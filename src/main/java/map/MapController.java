package map;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;


/**
 * Created by Ben 9/14/2015
 */
public class MapController {

    Map map;

    @FXML
    GridPane pane;

    /**
     * Constructor which sets up the default map.
     */
    public MapController() {
        map = new Map();

        //TODO: Create the Tiles for the map manually, and add them to the gridPane.
        if (pane == null) {
            System.out.println("No pane!");
        }
    }

}