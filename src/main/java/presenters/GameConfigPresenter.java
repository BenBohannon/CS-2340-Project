package presenters;

import view.GameConfigView;

/**
 * Presenter that displays the configuration screen for setting up the game options.
 */
public class GameConfigPresenter extends Presenter<GameConfigView> {

    public final void finishConfigure(String difficulty, String map, int numPlayers) {
        //returns presenter of view passed, after stage has been set//
        PlayerConfigPresenter nextPresenter = (PlayerConfigPresenter) getContext().showScreen("player_config.fxml");

        //can pass info to next presenter//
        nextPresenter.setNumPlayersLeft(numPlayers - 1);
    }
}
