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
    private TurnEndListener listener = this::nextTurn;


    @Override
    public void initialize() {
        if (turnService.isTurnInProgress()) {
            turnService.addTurnEndListener(listener);
        } else {
            if (turnService.isAllTurnsOver()) {
                beginRound();
            }
            beginTurn();
        }
    }

        /**
     * Loads the input .fxml file and gives up control to it.
     * @param str
     */
    private void switchPresenter(String str) {
        if (isListening) {
            turnService.removeTurnEndListener(listener);
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

    /**
     * Computes what should be done to the input tile, based on model information.
     * @param tileCoord Coordinate of the tile to affect
     */
    public void onClick(Point tileCoord) {

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

    public void beginRound() {
        turnService.beginRound();
    }

    private void beginTurn() {
        turnService.beginTurn();
        turnService.addTurnEndListener(listener);
        isListening = true;
        view.showTurnStartText();
    }

    private void nextTurn(Player p) {
        isListening = false;
        Platform.runLater(() -> {
            view.stopMovement();
            if (!turnService.isAllTurnsOver()) {
                beginTurn();
            } else {
                //TODO: Switch to stat screen here!
                switchPresenter("map_grid_tile_select.fxml");
            }
        });
    }


}