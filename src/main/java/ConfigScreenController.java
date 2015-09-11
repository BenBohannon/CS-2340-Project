import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by brian on 9/10/15.
 */
public class ConfigScreenController {

    @FXML
    GridPane grid;
    @FXML
    ToggleGroup group;

    @FXML
    protected void handleFinishButtonAction(ActionEvent event) {
        Stage stage = (Stage) grid.getScene().getWindow();
        int numPlayersLeft = Integer.parseInt(((RadioButton) group.getSelectedToggle()).getText());
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/player_config.fxml"));
            root = loader.load();
            ((PlayerMenuController)loader.getController()).setNumPlayersLeft(--numPlayersLeft);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene playerConfigScene = new Scene(root, 800, 800);

        stage.setScene(playerConfigScene);
        stage.show();
    }

    @FXML
    protected void start(ActionEvent event) {

    }
}
