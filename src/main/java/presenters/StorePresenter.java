package presenters;

import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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

    @FXML
    private Label crystiteLabel;
    @FXML
    private Label foodLabel;
    @FXML
    private Label smithoreLabel;
    @FXML
    private Label energyLabel;

    @FXML
    void initialize() {
        updateEnergyLabel();
        updateFoodLabel();
        updateCrysLabel();
        updateSmithoreLabel();
    }

    public void handleSmithoreBuyClick(ActionEvent event) {
        store.sellSmithore(turnService.getCurrentPlayer());
        updateSmithoreLabel();
    }

    public void handleSmithoreSellClick(ActionEvent event) {
        store.buySmithore(turnService.getCurrentPlayer(), 10);      //temp price
        updateSmithoreLabel();
    }

    public void handleCrystiteBuyClick(ActionEvent event) {
        store.sellCrystite(turnService.getCurrentPlayer());
        updateCrysLabel();
    }

    public void handleCrystiteSellClick(ActionEvent event) {
        store.buyCrystite(turnService.getCurrentPlayer(), 20);
        updateCrysLabel();
    }

    public void handleFoodBuyClick(ActionEvent event) {
        store.sellFood(turnService.getCurrentPlayer());
        updateFoodLabel();
    }

    public void handleFoodSellClick(ActionEvent event) {
        store.buyFood(turnService.getCurrentPlayer(), 5);
        updateFoodLabel();
    }

    public void handleEnergyBuyClick(ActionEvent event) {
        store.sellEnergy(turnService.getCurrentPlayer());
        updateEnergyLabel();
    }

    public void handleEnergySellClick(ActionEvent event) {
        store.buyEnergy(turnService.getCurrentPlayer(), 10);
        updateEnergyLabel();
    }

    public void handleExitClick(ActionEvent event) {
        context.showScreen("town.fxml");
    }

    private void updateCrysLabel() {
        crystiteLabel.setText("Crystite: " + turnService.getCurrentPlayer().getCrystite());
    }

    private void updateFoodLabel() {
        foodLabel.setText("Food: " + turnService.getCurrentPlayer().getFood());
    }

    private void updateEnergyLabel() {
        energyLabel.setText("Energy: " + turnService.getCurrentPlayer().getEnergy());
    }

    private void updateSmithoreLabel() {
        smithoreLabel.setText("Smithore: " + turnService.getCurrentPlayer().getSmithore());
    }
}