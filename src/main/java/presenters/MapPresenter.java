package presenters;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;


/**
 * Created by Ben 9/14/2015
 */
public class MapPresenter {

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

        //Create a map.
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 5; j++) {
                Tile temp;

                if (i == 4) {
                    if (j == 2) {
                        //Make a town
                        temp = new Tile(TileType.TOWN);

                    } else {
                        //Make a river
                        temp = new Tile(TileType.RIVER);
                    }
                } else {
                    //TODO: Make plains or mountains, randomly.
                    temp = new Tile(TileType.PLAIN);
                }
                //Add tiles to the map.
                map.add(temp, i, j);

                //Add tile images to the gridPane
                grid.add(new ImageView(temp.getImage(100, 100)), i, j);
            }
        }

    }

}