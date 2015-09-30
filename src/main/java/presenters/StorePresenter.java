package presenters;

import com.google.inject.Inject;
import data.TurnInfoHolder;

import javafx.event.ActionEvent;

/**
 * Created by connor on 9/29/15.
 */
public class StorePresenter extends Presenter {

    @Inject
    TurnInfoHolder turnInfoHolder;

    public void handleSmithoreBuyClick(ActionEvent event) {
        turnInfoHolder.getCurrentPlayer().addSmithore(1);
        turnInfoHolder.getCurrentPlayer().removeMoney(10);                  //temp price values
    }

    public void handleSmithoreSellClick(ActionEvent event) {
        turnInfoHolder.getCurrentPlayer().removeSmithore(1);
        turnInfoHolder.getCurrentPlayer().addMoney(10);
    }

    public void handleCrystiteBuyClick(ActionEvent event) {
        turnInfoHolder.getCurrentPlayer().addCrystite(1);
        turnInfoHolder.getCurrentPlayer().removeMoney(15);
    }

    public void handleCrystiteSellClick(ActionEvent event) {
        turnInfoHolder.getCurrentPlayer().removeCrystite(1);
        turnInfoHolder.getCurrentPlayer().addMoney(15);
    }

    public void handleFoodBuyClick(ActionEvent event) {
        turnInfoHolder.getCurrentPlayer().addFood(1);
        turnInfoHolder.getCurrentPlayer().removeMoney(5);
    }

    public void handleFoodSellClick(ActionEvent event) {
        turnInfoHolder.getCurrentPlayer().removeFood(1);
        turnInfoHolder.getCurrentPlayer().addMoney(5);
    }

    public void handleExitClick(ActionEvent event) {
        context.showScreen("town.fxml");
    }
}
