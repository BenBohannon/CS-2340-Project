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
public class StartController {

    @FXML
    GridPane grid;

    @FXML
    protected void handleStartEvent(ActionEvent event) {
        Stage stage = (Stage) grid.getScene().getWindow();
        Parent gameConfig = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/config_screen.fxml"));
            gameConfig = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setScene(new Scene(gameConfig, 800, 800));
        stage.show();

    }
}
