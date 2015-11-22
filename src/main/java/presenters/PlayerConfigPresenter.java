package presenters;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import data.abstractsources.Repository;
import javafx.scene.paint.Color;
import model.entity.Player;
import model.entity.PlayerRace;
import view.PlayerConfigView;

import java.util.function.Predicate;

/**
 * Created by brian on 9/10/15.
 */
public class PlayerConfigPresenter extends Presenter<PlayerConfigView> {

    @Inject
    private Repository<Player> playerRepository;

    @Inject
    @Named("InitialPlayerMoney")
    private int initialPlayerMoney;

    private int numPlayersLeft;


    /**
     * now we don't have to worry about the views.
     * We can just have them pass us the events we care about.
     * But notice, there are things going on in the domain
     * that the View doesn't know about, so we'll have to
     * inform it.
     * @param playerColor player's color
     * @param playerName player's name, totally valid thank to the View
     * @param playerRaceStr player's race
     */
    public void finish(Color playerColor, String playerName, String playerRaceStr) {

        PlayerRace playerRace = PlayerRace.valueOf(playerRaceStr);

        boolean allUnique = true;

        //check if color has already been used//
        if (getPlayerRepository().getAll().stream().anyMatch(new Predicate<Player>() {
            @Override
            public boolean test(Player player) {
                return player.getColor().equals(playerColor);
            }
        })) {
            //show validation labels in view//
            allUnique = false;
            //NOTICE: view is already of correct type
            getView().showColorAlreadyChosen();
        }

        //check if name has already been used//
        if (getPlayerRepository().getAll().stream().anyMatch(new Predicate<Player>() {
            @Override
            public boolean test(Player player) {
                return player.getName().equals(playerName);
            }
        })) {
            //show validation labels in view//
            allUnique = false;
            getView().showNameAlreadyChosen();
        }

        //check if race has already been used//
        if (getPlayerRepository().getAll().stream().anyMatch(new Predicate<Player>() {
            @Override
            public boolean test(Player player) {
                return player.getRace().equals(playerRace);
            }
        })) {
            //show validation labels in view//
            allUnique = false;
            //NOTICE: view is already of correct type
            getView().showRaceAlreadyChosen();
        }


        if (allUnique) {
            //add player and change presenter//
            Player p = new Player();
            p.setColor(playerColor);
            p.setName(playerName);
            for (PlayerRace race : PlayerRace.values()) {
                if (race.toString().equalsIgnoreCase(playerRaceStr.trim().toLowerCase())) {
                    p.setRace(race);
                }
            }
            p.setMoney(getInitialPlayerMoney());
            p.setId(-1);
            getPlayerRepository().save(p);

            if (numPlayersLeft > 0) {
                PlayerConfigPresenter nextPresenter = (PlayerConfigPresenter) getContext().showScreen("player_config.fxml");
                nextPresenter.setNumPlayersLeft(--numPlayersLeft);
            } else {
                getContext().showScreen("map_grid_tile_select.fxml");
            }
        }
    }

    /**
     * sets number of players left
     * @param pNumPlayersLeft num of players
     */
    public void setNumPlayersLeft(int pNumPlayersLeft) {
        this.numPlayersLeft = pNumPlayersLeft;
    }

    public Repository<Player> getPlayerRepository() {
        return playerRepository;
    }

    public void setPlayerRepository(Repository<Player> pPlayerRepository) {
        this.playerRepository = pPlayerRepository;
    }

    public int getInitialPlayerMoney() {
        return initialPlayerMoney;
    }

    public void setInitialPlayerMoney(int pInitialPlayerMoney) {
        this.initialPlayerMoney = pInitialPlayerMoney;
    }
}
