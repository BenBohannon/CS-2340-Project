package presenters;

import com.google.inject.Inject;
import data.Repository;
import javafx.scene.control.Toggle;
import javafx.scene.control.RadioButton;
import javafx.scene.paint.Color;
import model.entity.Player;
import model.entity.PlayerRace;
import view.PlayerConfigView;

/**
 * Created by brian on 9/10/15.
 */
public class PlayerConfigPresenter extends Presenter<PlayerConfigView> {

    @Inject
    Repository<Player> playerRepository;

    private int numPlayersLeft;


    /**
     * now we don't have to worry about the views. We can just have them pass us the events we care about.
     * But notice, there are things going on in the domain that the View doesn't know about, so we'll have to
     * inform it.
     * @param playerColor player's color
     * @param playerName player's name, totally valid thank to the View
     */
    public void finish(Color playerColor, String playerName, Toggle playerRace) {

        boolean allUnique = true;

        //check if color has already been used//
        if (playerRepository.getAll().stream().anyMatch(player -> player.getColor().equals(playerColor))) {
            //show validation labels in view//
            allUnique = false;
            //NOTICE: view is already of correct type
            view.showColorAlreadyChosen();
        }

        //check if name has already been used//
        if (playerRepository.getAll().stream().anyMatch(player -> player.getName().equals(playerName))) {
            //show validation labels in view//
            allUnique = false;
            view.showNameAlreadyChosen();
        }

        //check if race has already been used//
        if (playerRepository.getAll().stream().anyMatch(player -> player.getRace().equals(playerRace))) {
            //show validation labels in view//
            allUnique = false;
            //NOTICE: view is already of correct type
            view.showRaceAlreadyChosen();
        }


        if (allUnique) {
            //add player and change presenter//
            Player p = new Player();
            p.setColor(playerColor);
            p.setName(playerName);
            for (PlayerRace race : PlayerRace.values()) {
                if (race.toString().equals(playerRace.selectedProperty().toString())) {
                    p.setRace(race);
                }
            }
            p.setId(-1);
            playerRepository.save(p);

            if (numPlayersLeft > 0) {
                PlayerConfigPresenter nextPresenter = (PlayerConfigPresenter) context.showScreen("player_config.fxml");
                nextPresenter.setNumPlayersLeft(--numPlayersLeft);
            } else {
                context.showScreen("map_grid_tile_select.fxml");
            }
        }
    }

    public void setNumPlayersLeft(int numPlayersLeft) {
        this.numPlayersLeft = numPlayersLeft;
    }
}
