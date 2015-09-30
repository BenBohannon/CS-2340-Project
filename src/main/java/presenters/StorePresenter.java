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
        turnInfoHolder.getCurrentPlayer().buySmithore(1, 10);               //temp price values
    }

    public void handleSmithoreSellClick(ActionEvent event) {
        turnInfoHolder.getCurrentPlayer().sellSmithore(1, 10);
    }

    public void handleCrystiteBuyClick(ActionEvent event) {
        turnInfoHolder.getCurrentPlayer().buyCrystite(1, 20);
    }

    public void handleCrystiteSellClick(ActionEvent event) {
        turnInfoHolder.getCurrentPlayer().sellCrystite(1,20);
    }

    public void handleFoodBuyClick(ActionEvent event) {
        turnInfoHolder.getCurrentPlayer().buyFood(1, 5);
    }

    public void handleFoodSellClick(ActionEvent event) {
        turnInfoHolder.getCurrentPlayer().sellFood(1, 5);
    }

    public void handleExitClick(ActionEvent event) {
        context.showScreen("town.fxml");
    }
}
