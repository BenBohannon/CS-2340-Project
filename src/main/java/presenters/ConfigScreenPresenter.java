package presenters;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import presenters.PlayerMenuPresenter;

import java.io.IOException;

/**
 * Created by brian on 9/10/15.
 */
public class ConfigScreenPresenter {

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presenters/player_config.fxml"));
            root = loader.load();
            ((PlayerMenuPresenter)loader.getController()).setNumPlayersLeft(--numPlayersLeft);
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
