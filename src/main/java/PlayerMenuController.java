import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by brian on 9/10/15.
 */
public class PlayerMenuController {
    @FXML
    GridPane grid;
    private int numPlayersLeft;

    @FXML
    protected void handleFinishButtonAction(ActionEvent event) {
        Stage stage = (Stage) grid.getScene().getWindow();

        if (numPlayersLeft < 0) {
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
    }

    public void setNumPlayersLeft(int numPlayersLeft) {
        this.numPlayersLeft = numPlayersLeft;
    }
}
