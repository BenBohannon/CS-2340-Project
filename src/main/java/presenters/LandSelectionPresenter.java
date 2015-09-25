package presenters;

import com.google.inject.Inject;
import data.MapInfoHolder;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import map.Map;
import map.Tile;

import java.awt.event.ActionEvent;

/**
 * Created by connor on 9/24/15.
 */
public class LandSelectionPresenter extends Presenter {
    private int numPlayersLeft;

    @Inject
    public Map map;
    @Inject
    MapInfoHolder mapInfo;
//    @Inject
//    Repository<Player> playerRepository;
    @FXML
    private GridPane grid;
    @FXML
    private Pane pane;
    int mountainLimit = 6;
    int mountains = 0;


    @FXML
    public void initialize() {


        //Create a map.
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 5; j++) {
                Tile tile = new Tile(mapInfo.getTileType(j, i));
                //Add tiles to the map.
                map.add(tile, i, j);

                //Add tile images to the gridPane
                grid.add(new ImageView(tile.getImage(100, 100)), i, j);
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
