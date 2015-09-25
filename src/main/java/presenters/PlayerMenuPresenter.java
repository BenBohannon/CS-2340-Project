package presenters;

import com.google.inject.Inject;
import data.Repository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import model.Player;

/**
 * Created by brian on 9/10/15.
 */
public class PlayerMenuPresenter extends Presenter {

    @FXML
    ColorPicker colorPicker;

    @Inject
    Repository<Player> playerRepository;

    private int numPlayersLeft;

    @FXML
    protected void handleFinishButtonAction(ActionEvent event) {
        Player p = new Player();
        p.setColor(colorPicker.getValue());
        playerRepository.save(p);

        if (numPlayersLeft > 0) {
            PlayerMenuPresenter nextPresenter = (PlayerMenuPresenter) context.showScreen("player_config.fxml");
            nextPresenter.setNumPlayersLeft(--numPlayersLeft);
        } else {
            context.showScreen("map_grid_tile_select.fxml");
        }
    }

    public void setNumPlayersLeft(int numPlayersLeft) {
        this.numPlayersLeft = numPlayersLeft;
    }
}
