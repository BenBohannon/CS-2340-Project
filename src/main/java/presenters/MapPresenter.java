package presenters;

import com.google.inject.Inject;
import data.MapInfoHolder;
import data.Repository;

import java.awt.Point;

import data.TurnEndListener;
import javafx.application.Platform;
import model.entity.Player;
import model.map.*;
import model.service.DefaultTurnService;
import view.MapView;


/**
 * Created by Ben 9/14/2015
 */
public class MapPresenter extends Presenter<MapView> {

    @Inject
    private Map map;
    @Inject
    private MapInfoHolder mapInfo;

    @Inject
    private Repository<Player> playerRepository;

    @Inject
    private DefaultTurnService turnService;

    private boolean isListening = false;
    private TurnEndListener listener = (Player p) -> nextTurn(p);

    /**
     * Loads the input .fxml file and gives up control to it.
     * @param str
     */
    public void switchPresenter(String str) {
        if (isListening) {
            turnService.removeTurnEndListener(listener);
            isListening = false;
        }
        context.showScreen(str);
    }

    /**
     * Computes what should be done to the input tile, based on model information.
     * @param tileCoord Coordinate of the tile to affect
     */
    public void onClick(Point tileCoord) {

    }

    public boolean checkTurnState() {
        if (turnService.isTurnInProgress()) {
            turnService.addTurnEndListener(listener);
            isListening = true;
            return false;
        } else {
            if (turnService.isAllTurnsOver()) {
                //TODO: Switch to stat screen here!
                switchPresenter("map_grid_tile_select.fxml");
            } else {
                beginTurn();
            }
            return true;
        }
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

    public void beginRound() {
        turnService.beginRound();
    }

    public void beginTurn() {
        turnService.beginTurn();
        turnService.addTurnEndListener(listener);
        isListening = true;
    }

    public void nextTurn(Player p) {
        isListening = false;
        Platform.runLater(() -> {
            view.stopMovement();
            if (!turnService.isAllTurnsOver()) {
                beginTurn();
                view.startTurn();
            } else {
                //TODO: Switch to stat screen here!
                switchPresenter("map_grid_tile_select.fxml");
            }
        });
    }


}