package presenters;

import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.service.StoreService;
import model.service.DefaultTurnService;

/**
 * Created by connor on 9/29/15.
 */
public class StorePresenter extends Presenter {

    @Inject
    DefaultTurnService turnService;

    @Inject
    StoreService storeService;

    @FXML
    private Label crystiteLabel;
    @FXML
    private Label foodLabel;
    @FXML
    private Label smithoreLabel;
    @FXML
    private Label energyLabel;

    @FXML
    public void initialize() {
        updateEnergyLabel();
        updateFoodLabel();
        updateCrysLabel();
        updateSmithoreLabel();
    }

    public void handleSmithoreBuyClick(ActionEvent event) {
        storeService.sellSmithore(turnService.getCurrentPlayer());
        updateSmithoreLabel();
    }

    public void handleSmithoreSellClick(ActionEvent event) {
        storeService.buySmithore(turnService.getCurrentPlayer(), 10);      //temp price
        updateSmithoreLabel();
    }

    public void handleCrystiteBuyClick(ActionEvent event) {
        storeService.sellCrystite(turnService.getCurrentPlayer());
        updateCrysLabel();
    }

    public void handleCrystiteSellClick(ActionEvent event) {
        storeService.buyCrystite(turnService.getCurrentPlayer(), 20);
        updateCrysLabel();
    }

    public void handleFoodBuyClick(ActionEvent event) {
        storeService.sellFood(turnService.getCurrentPlayer());
        updateFoodLabel();
    }

    public void handleFoodSellClick(ActionEvent event) {
        storeService.buyFood(turnService.getCurrentPlayer(), 5);
        updateFoodLabel();
    }

    public void handleEnergyBuyClick(ActionEvent event) {
        storeService.sellEnergy(turnService.getCurrentPlayer());
        updateEnergyLabel();
    }

    public void handleEnergySellClick(ActionEvent event) {
        storeService.buyEnergy(turnService.getCurrentPlayer(), 10);
        updateEnergyLabel();
    }

    public void handleExitClick(ActionEvent event) {
        context.showScreen("town.fxml");
    }

    private void updateCrysLabel() {
        crystiteLabel.setText("Crystite: " + storeService.getCrystite());
    }

    private void updateFoodLabel() {
        foodLabel.setText("Food: " + storeService.getFood());
    }

    private void updateEnergyLabel() {
        energyLabel.setText("Energy: " + storeService.getEnergy());
    }

    private void updateSmithoreLabel() {
        smithoreLabel.setText("Smithore: " + storeService.getSmithore());
    }
}