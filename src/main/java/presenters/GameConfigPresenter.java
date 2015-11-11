package presenters;

import view.GameConfigView;

/**
 * Created by brian on 9/10/15.
 */
public class GameConfigPresenter extends Presenter<GameConfigView> {

    /**
     * switches presenter to player config screen
     * @param difficulty selected difficulty
     * @param map selected map
     * @param numPlayers selected num of players
     */
    public void finishConfigure(String difficulty, String map, int numPlayers) {
        //returns presenter of view passed, after stage has been set//
        PlayerConfigPresenter nextPresenter = (PlayerConfigPresenter)
                context.showScreen("player_config.fxml");

        //can pass info to next presenter//
        nextPresenter.setNumPlayersLeft(--numPlayers);
    }
}
