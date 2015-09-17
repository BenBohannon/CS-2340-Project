package presenters;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * Created by brian on 9/10/15.
 */
public class StartPresenter extends Presenter {

    @FXML
    protected void handleStartEvent(ActionEvent event) {
        context.showScreen("config_screen.fxml");
        //this presenter's view will now be removed, and the config_screen's started//
    }
}
