package presenters;

import com.google.inject.Inject;
import data.MapInfoHolder;
import data.Repository;

import java.awt.Point;

import data.TurnEndListener;
import javafx.application.Platform;
import model.entity.Mule;
import model.entity.Player;
import model.map.*;
import model.service.DefaultTurnService;
import view.MapView;


/**
 * Created by Ben 9/14/2015
 */
public class MapPresenter extends Presenter<MapView> implements TurnEndListener {

    @Inject
    private Map map;
    @Inject
    private MapInfoHolder mapInfo;

    @Inject
    private Repository<Player> playerRepository;

    @Inject
    private DefaultTurnService turnService;

    private boolean isListening;
    private boolean isPlacingMule;
    private Mule mulePlacing;


    @Override
    public void initialize() {
        if (turnService.isTurnInProgress()) {
            turnService.addTurnEndListener(this);
        } else {
            if (turnService.isAllTurnsOver()) {
                beginRound();
            }
            beginTurn();
        }
        
        if (isPlacingMule) {
            view.displayMule();
        }
    }

    /**
     * Loads the input .fxml file and gives up control to it.
     * @param str
     */
    private void switchPresenter(String str) {
        if (isListening) {
            turnService.removeTurnEndListener(this);
            isListening = false;
        }
        context.showScreen(str);
    }

    /**
     * Turns control over to the TownPresenter, and stops character movement.
     */
    public void enterCity() {
        view.stopMovement();

        switchPresenter("town.fxml");
    }

    @Override
    public void onTurnEnd(Player player) {
        isListening = false;
        Platform.runLater(() -> {
            view.stopMovement();

            //Mule is lost if not placed//
            if (isPlacingMule) {
                player.mules.remove(mulePlacing);
                view.stopDisplayingMule();
                isPlacingMule = false;
            }

            if (turnService.isAllTurnsOver()) {
                //TODO: Switch to stat screen here!
                switchPresenter("map_grid_tile_select.fxml");
            } else {
                beginTurn();
            }
        });
    }

    /**
     * Computes what should be done to the input tile, based on model information.
     * @param tileCoord Coordinate of the tile to affect
     */
    public void onClick(Point tileCoord) {
        if (!isPlacingMule) {
            return;
        }

        //check if player owns tile//
        boolean owned = playerRepository.getAll().stream()
                .flatMap(p -> p.getOwnedProperties().stream())
                .anyMatch(t -> t.getLocation().getCol() == tileCoord.y && t.getLocation().getRow() == tileCoord.x);

        //check for another mule//
        boolean occupied = map.getOccupants(tileCoord.x, tileCoord.y, Mule.class).length > 0;

        System.out.println(tileCoord.x + ", " + tileCoord.y);
        System.out.println("owned: "+ owned + " occupied: " + occupied);
        if (owned && !occupied) {
            map.add(mulePlacing, tileCoord.x, tileCoord.y);
            view.placeMuleGraphic(tileCoord.y, tileCoord.x, mulePlacing.getType());
        } else {
            System.out.println("Mule Lost");
        }

        mulePlacing = null;
        isPlacingMule = false;
        view.stopDisplayingMule();
    }

    public boolean isTurnInProgress() {
        return turnService.isTurnInProgress();
    }

    public Map getMap() {
        return map;
    }

    public Repository<Player> getPlayerRepository() {
        return playerRepository;
    }

    public String getCurrentPlayerName() {
        return turnService.getCurrentPlayer().getName();
    }

    public boolean isPlacingMule() {
        return isPlacingMule;
    }

    /**
     * should be called by townPresenter
     * @param isPlacingMule
     */
    public void setIsPlacingMule(boolean isPlacingMule, Mule mule) {
        this.mulePlacing = mule;
        this.isPlacingMule = isPlacingMule;
    }

    public void beginRound() {
        turnService.beginRound();
    }

    private void beginTurn() {
        System.out.println("beginTurn()");
        turnService.beginTurn();
        turnService.addTurnEndListener(this);
        isListening = true;
        view.showTurnStartText();
    }
}