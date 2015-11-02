package presenters;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;


/**
 * Created by brian on 9/10/15.
 */
public class StartPresenter extends Presenter {

    @FXML
    protected void handleStartEvent(ActionEvent event) {
        context.showScreen("config_screen.fxml");

    }

    @FXML
    protected void handleLoadEvent(ActionEvent event) {
        context.showScreen("map_grid_tile_select.fxml");
    }


}
