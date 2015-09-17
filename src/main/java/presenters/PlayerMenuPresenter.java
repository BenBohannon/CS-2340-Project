package presenters;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

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
            context.showScreen("map_grid.fxml", 890, 490, false);
        }
    }

    public void setNumPlayersLeft(int numPlayersLeft) {
        this.numPlayersLeft = numPlayersLeft;
    }
}
