package presenters;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import java.io.IOException;
import java.util.Random;

import map.*;


/**
 * Created by Ben 9/14/2015
 */
public class MapPresenter extends Presenter {

    private Map map;

    @FXML
    private GridPane grid;

    /**
     * Constructor which sets up the default map.
     */
    @FXML
    public void initialize() {

        //If there's already an instance of map, don't overwrite it.
        if (map != null) {
            return;
        }

        map = new Map();
        int mountainLimit = 6;
        int mountains = 0;

        //Create a map.
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 5; j++) {
                map.Tile temp;
                Random rand = new Random();

                if (i == 4) {
                    if (j == 2) {
                        //Make a town
                        temp = new Tile(TileType.TOWN);

                    } else {
                        //Make a river
                        temp = new Tile(TileType.RIVER);
                    }
                } else {
                    if (mountains < mountainLimit && rand.nextInt(6) == 0) {
                        temp = new Tile(TileType.MOUNTAIN);
                        mountains++;
                    } else {
                        temp = new Tile(TileType.PLAIN);
                    }
                }
                //Add tiles to the map.
                map.add(temp, i, j);

                //Add tile images to the gridPane
                grid.add(new ImageView(temp.getImage(100, 100)), i, j);
            }
        }

    }

}