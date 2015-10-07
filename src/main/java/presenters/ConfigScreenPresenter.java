package presenters;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

/**
 * Created by brian on 9/10/15.
 */
public class ConfigScreenPresenter extends Presenter {
    @FXML
    ToggleGroup group;

    @FXML
    protected void handleFinishButtonAction(ActionEvent event) {
        int numPlayersLeft = Integer.parseInt(((RadioButton) group.getSelectedToggle()).getText());

        //returns presenter of view passed, after stage has been set//
        PlayerConfigPresenter nextPresenter = (PlayerConfigPresenter) context.showScreen("player_config.fxml");

        //can pass info to next presenter//
        nextPresenter.setNumPlayersLeft(--numPlayersLeft);
    }
}
