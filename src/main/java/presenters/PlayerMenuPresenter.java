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
public class PlayerMenuPresenter extends Presenter {
    private int numPlayersLeft;

    @FXML
    protected void handleFinishButtonAction(ActionEvent event) {
        if (numPlayersLeft > 0) {
            PlayerMenuPresenter nextPresenter = (PlayerMenuPresenter) context.showScreen("player_config.fxml");
            nextPresenter.setNumPlayersLeft(--numPlayersLeft);
        } else {
            context.showScreen("temp.fxml");
        }
    }

    public void setNumPlayersLeft(int numPlayersLeft) {
        this.numPlayersLeft = numPlayersLeft;
    }
}
