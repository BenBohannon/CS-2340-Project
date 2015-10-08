package presenters;

import com.google.inject.Inject;
import javafx.event.ActionEvent;
import model.entity.Store;
import model.service.DefaultTurnService;

/**
 * Created by connor on 9/29/15.
 */
public class StorePresenter extends Presenter {

    @Inject
    DefaultTurnService turnService;

    @Inject
    Store store;


    public void handleSmithoreBuyClick(ActionEvent event) {
        store.sellSmithore(turnService.getCurrentPlayer());
    }

    public void handleSmithoreSellClick(ActionEvent event) {
        store.buySmithore(turnService.getCurrentPlayer(), 10);      //temp price
    }

    public void handleCrystiteBuyClick(ActionEvent event) {
        store.sellCrystite(turnService.getCurrentPlayer());
    }

    public void handleCrystiteSellClick(ActionEvent event) {
        store.buyCrystite(turnService.getCurrentPlayer(), 20);
    }

    public void handleFoodBuyClick(ActionEvent event) {
        store.sellFood(turnService.getCurrentPlayer());
    }

    public void handleFoodSellClick(ActionEvent event) {
        store.buyFood(turnService.getCurrentPlayer(), 5);
    }

    public void handleEnergyBuyClick(ActionEvent event) {
        store.sellEnergy(turnService.getCurrentPlayer());
    }

    public void handleEnergySellClick(ActionEvent event) {
        store.buyEnergy(turnService.getCurrentPlayer(), 10);
    }

    public void handleExitClick(ActionEvent event) {
        context.showScreen("town.fxml");
    }
}