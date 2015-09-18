package presenters;

import com.google.inject.Inject;
import data.Repository;
import data.TurnManager;
import javafx.event.ActionEvent;
import model.Mule;
import model.MuleType;
import model.Player;

/**
 * Created by brian on 9/17/15.
 */
public class TownPresenter extends Presenter {

    @Inject
    TurnManager turnManager;

    public void handleEnergyClick(ActionEvent event) {
        turnManager.getCurrentPlayer().addMule(new Mule(MuleType.Energy));
        returnToMap();
    }

    public void handleSmithoreClick(ActionEvent event) {
        turnManager.getCurrentPlayer().addMule(new Mule(MuleType.Smithore));
        returnToMap();
    }

    public void handleFoodClick(ActionEvent event) {
        turnManager.getCurrentPlayer().addMule(new Mule(MuleType.Food));
        returnToMap();
    }

    public void handleCrystiteClick(ActionEvent event) {
        turnManager.getCurrentPlayer().addMule(new Mule(MuleType.Crysite));
        returnToMap();
    }

    public void handleMapClick(ActionEvent event) {
        returnToMap();
    }

    /**
     * helper method for logging the unimplemented behavior of returning to the map
     */
    private void returnToMap() {
        System.out.println("Would return to map; not yet implemented.");
        //context.showScreen("map.fxml");
    }

    public void handlePubClick(ActionEvent event) {
        int amountToAdd;
        if (turnManager.getRoundNumber() < 3) {
            amountToAdd = 50;
        } else if (turnManager.getRoundNumber() > 6) {
            amountToAdd = 100;
        } else {
            amountToAdd = 150;
        }
        turnManager.getCurrentPlayer().addMoney(amountToAdd + (int) (Math.random() * turnManager.getTimeLeftInTurn()));
    }
}
