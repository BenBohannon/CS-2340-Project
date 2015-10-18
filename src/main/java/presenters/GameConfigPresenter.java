package presenters;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import view.GameConfigView;

/**
 * Created by brian on 9/10/15.
 */
public class GameConfigPresenter extends Presenter<GameConfigView> {

    public void finishConfigure(String difficulty, String map, int numPlayers) {
        //returns presenter of view passed, after stage has been set//
        PlayerConfigPresenter nextPresenter = (PlayerConfigPresenter) context.showScreen("player_config.fxml");

        //can pass info to next presenter//
        nextPresenter.setNumPlayersLeft(--numPlayers);
    }
}
