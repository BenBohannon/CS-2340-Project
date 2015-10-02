package presenters;

import com.google.inject.Inject;
import model.service.DefaultTurnService;
import javafx.event.ActionEvent;
import model.entity.Mule;
import model.entity.MuleType;

/**
 * Created by brian on 9/17/15.
 */
public class TownPresenter extends Presenter {

    @Inject
    DefaultTurnService turnService;

    public void handleEnergyClick(ActionEvent event) {
        turnService.getCurrentPlayer().addMule(new Mule(MuleType.Energy));
        returnToMap();
    }

    public void handleSmithoreClick(ActionEvent event) {
        turnService.getCurrentPlayer().addMule(new Mule(MuleType.Smithore));
        returnToMap();
    }

    public void handleFoodClick(ActionEvent event) {
        turnService.getCurrentPlayer().addMule(new Mule(MuleType.Food));
        returnToMap();
    }

    public void handleCrystiteClick(ActionEvent event) {
        turnService.getCurrentPlayer().addMule(new Mule(MuleType.Crysite));
        returnToMap();
    }

    public void handleStoreClick(ActionEvent event) {
        context.showScreen("store.fxml");
    }

    public void handleMapClick(ActionEvent event) {
        returnToMap();
    }

    /**
     * helper method for logging the unimplemented behavior of returning to the model.map
     * UPDATE: now returns to model.map
     */
    private void returnToMap() {
        context.showScreen("map_grid.fxml");
    }

    public void handlePubClick(ActionEvent event) {
        int amountToAdd;
        if (turnService.getRoundNumber() < 3) {
            amountToAdd = 50;
        } else if (turnService.getRoundNumber() > 6) {
            amountToAdd = 100;
        } else {
            amountToAdd = 150;
        }

        turnService.getCurrentPlayer().addMoney(amountToAdd + (int) (Math.random() * turnService.getTimeLeftInTurn()));
        turnService.endTurn();
        // if (allplayershavegone)
        context.showScreen("map_grid_tile_select.fxml");
        // else
        // context.showScreen("map_grid.fxml");
        // iterate player id and reset to zero at appropriate times

    }
}
