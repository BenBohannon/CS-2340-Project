package presenters;

import com.google.inject.Inject;
import data.TurnInfoHolder;
import javafx.event.ActionEvent;
import model.Mule;
import model.MuleType;

/**
 * Created by brian on 9/17/15.
 */
public class TownPresenter extends Presenter {

    @Inject
    TurnInfoHolder turnInfoHolder;

    public void handleEnergyClick(ActionEvent event) {
        turnInfoHolder.getCurrentPlayer().addMule(new Mule(MuleType.Energy));
        returnToMap();
    }

    public void handleSmithoreClick(ActionEvent event) {
        turnInfoHolder.getCurrentPlayer().addMule(new Mule(MuleType.Smithore));
        returnToMap();
    }

    public void handleFoodClick(ActionEvent event) {
        turnInfoHolder.getCurrentPlayer().addMule(new Mule(MuleType.Food));
        returnToMap();
    }

    public void handleCrystiteClick(ActionEvent event) {
        turnInfoHolder.getCurrentPlayer().addMule(new Mule(MuleType.Crysite));
        returnToMap();
    }

    public void handleStoreClick(ActionEvent event) {
        context.showScreen("store.fxml");
    }

    public void handleMapClick(ActionEvent event) {
        returnToMap();
    }

    /**
     * helper method for logging the unimplemented behavior of returning to the map
     * UPDATE: now returns to map
     */
    private void returnToMap() {
        context.showScreen("map_grid.fxml");
    }

    public void handlePubClick(ActionEvent event) {
        int amountToAdd;
        if (turnInfoHolder.getRoundNumber() < 3) {
            amountToAdd = 50;
        } else if (turnInfoHolder.getRoundNumber() > 6) {
            amountToAdd = 100;
        } else {
            amountToAdd = 150;
        }
        turnInfoHolder.getCurrentPlayer().addMoney(amountToAdd + (int) (Math.random() * turnInfoHolder.getTimeLeftInTurn()));
        // if (allplayershavegone)
        context.showScreen("map_grid_tile_select.fxml");
        // else
        // context.showScreen("map_grid.fxml");
        // iterate player id and reset to zero at appropriate times
    }
}
