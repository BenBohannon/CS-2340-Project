package presenters;

import com.google.inject.Inject;

import javafx.event.ActionEvent;
import model.service.DefaultTurnService;

/**
 * Created by connor on 9/29/15.
 */
public class StorePresenter extends Presenter {

    @Inject
    DefaultTurnService turnService;

    public void handleSmithoreBuyClick(ActionEvent event) {
        turnService.getCurrentPlayer().buySmithore(10);               //temp price values
    }

    public void handleSmithoreSellClick(ActionEvent event) {
        turnService.getCurrentPlayer().sellSmithore(10);
    }

    public void handleCrystiteBuyClick(ActionEvent event) {
        turnService.getCurrentPlayer().buyCrystite(20);
    }

    public void handleCrystiteSellClick(ActionEvent event) {
        turnService.getCurrentPlayer().sellCrystite(20);
    }

    public void handleFoodBuyClick(ActionEvent event) {
        turnService.getCurrentPlayer().buyFood(5);
    }

    public void handleFoodSellClick(ActionEvent event) {
        turnService.getCurrentPlayer().sellFood(5);
    }

    public void handleEnergyBuyClick(ActionEvent event) {
        turnService.getCurrentPlayer().buyEnergy(10);
    }

    public void handleEnergySellClick(ActionEvent event) {
        turnService.getCurrentPlayer().sellEnergy(10);
    }

    public void handleExitClick(ActionEvent event) {
        context.showScreen("town.fxml");
    }
}