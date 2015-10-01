package presenters;

import com.google.inject.Inject;
import data.MapInfoHolder;
import data.Repository;

import java.awt.Point;

import model.entity.Player;
import model.map.*;


/**
 * Created by Ben 9/14/2015
 */
public class MapPresenter extends Presenter {

    @Inject
    private Map map;
    @Inject
    private MapInfoHolder mapInfo;

    @Inject
    private Repository<Player> playerRepository;

    /**
     * Loads the input .fxml file and gives up control to it.
     * @param str
     */
    public void switchPresenter(String str) {
        context.showScreen(str);
    }

    /**
     * Computes what should be done to the input tile, based on model information.
     * @param tileCoord Coordinate of the tile to affect
     */
    public void onClick(Point tileCoord) {

    }

    public Map getMap() {
        return map;
    }

    public Repository<Player> getPlayerRepository() {
        return playerRepository;
    }



}