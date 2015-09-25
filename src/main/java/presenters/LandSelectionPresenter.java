package presenters;

import com.google.inject.Inject;
import data.MapInfoHolder;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
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
    @Inject
    MapInfoHolder mapInfo;
    @FXML
    private GridPane grid;
    @FXML
    private Pane pane;
    int mountainLimit = 6;
    int mountains = 0;


    @FXML
    public void initialize() {
        pane.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case A:
                        playerRepository.get(0).buyProperty(//tile);
                        break;
                    case F:
                        goSouth = true;
                        break;
                    case J:
                        goWest = true;
                        break;
                    case SEMICOLON:
                        goEast = true;
                        break;
                }
            }
        });


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
