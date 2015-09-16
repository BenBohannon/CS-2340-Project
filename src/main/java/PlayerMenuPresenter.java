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
public class PlayerMenuPresenter {
    @FXML
    GridPane grid;
    private int numPlayersLeft;

    @FXML
    protected void handleFinishButtonAction(ActionEvent event) {
        Stage stage = (Stage) grid.getScene().getWindow();

        if (numPlayersLeft > 0) {
                Parent root = null;
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/player_config.fxml"));
                    root = loader.load();
                    ((PlayerMenuPresenter)loader.getController()).setNumPlayersLeft(--numPlayersLeft);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Scene playerConfigScene = new Scene(root, 800, 800);

                stage.setScene(playerConfigScene);
                stage.show();
        }
        else {
            Parent root = null;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/temp.fxml"));
                root = loader.load();
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
