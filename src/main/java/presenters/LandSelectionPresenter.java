package presenters;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import map.Map;
import map.Tile;
import map.TileType;

import java.awt.event.ActionEvent;
import java.util.Random;

/**
 * Created by connor on 9/24/15.
 */
public class LandSelectionPresenter extends Presenter {
    private int numPlayersLeft;

    @Inject
    public Map map;
    @FXML
    private GridPane grid;
    @FXML
    private Pane pane;
    int mountainLimit = 6;
    int mountains = 0;


    @FXML
    public void initialize() {
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
                //grid.add(new ImageView(temp.getImage(100, 100)), i, j);
            }
        }
    }

    @FXML
    public void handleColorPress(ActionEvent ev) {
        if (numPlayersLeft > 0) {
            Button b = (Button) ev.getSource();
            b.setStyle("-fx-background-color: darkblue");
            numPlayersLeft--;
        } else {
            context.showScreen("map_grid.fxml");
        }
    }

    public void setNumPlayersLeft(int numPlayersLeft) {
        this.numPlayersLeft = numPlayersLeft;
    }
}
