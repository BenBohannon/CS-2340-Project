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
    private DefaultTurnService turnService;

    @Inject
    private StoreService storeService;

    @FXML
    private Label crystiteLabel;
    @FXML
    private Label foodLabel;
    @FXML
    private Label smithoreLabel;
    @FXML
    private Label energyLabel;

    public StorePresenter(DefaultTurnService pTurnService, StoreService pStoreService) {
        turnService = pTurnService;
        storeService = pStoreService;
    }


    @FXML
    @Override
    public final void initialize() {
        updateEnergyLabel();
        updateFoodLabel();
        updateCrysLabel();
        updateSmithoreLabel();
    }

    public final void handleSmithoreBuyClick(ActionEvent event) {
        storeService.sellSmithore(turnService.getCurrentPlayer());
        updateSmithoreLabel();
    }

    public final void handleSmithoreSellClick(ActionEvent event) {
        storeService.buySmithore(turnService.getCurrentPlayer(), storeService.getSmithorePrice());      //temp price
        updateSmithoreLabel();
    }

    public final void handleCrystiteBuyClick(ActionEvent event) {
        storeService.sellCrystite(turnService.getCurrentPlayer());
        updateCrysLabel();
    }

    public final void handleCrystiteSellClick(ActionEvent event) {
        storeService.buyCrystite(turnService.getCurrentPlayer(), storeService.getCrystitePrice());
        updateCrysLabel();
    }

    public final void handleFoodBuyClick(ActionEvent event) {
        storeService.sellFood(turnService.getCurrentPlayer());
        updateFoodLabel();
    }

    public final void handleFoodSellClick(ActionEvent event) {
        storeService.buyFood(turnService.getCurrentPlayer(), storeService.getFoodPrice());
        updateFoodLabel();
    }

    public final void handleEnergyBuyClick(ActionEvent event) {
        storeService.sellEnergy(turnService.getCurrentPlayer());
        updateEnergyLabel();
    }

    public final void handleEnergySellClick(ActionEvent event) {
        storeService.buyEnergy(turnService.getCurrentPlayer(), storeService.getEnergyPrice());
        updateEnergyLabel();
    }

    public final void handleExitClick(ActionEvent event) {
        getContext().showScreen("town.fxml");
    }

    private void updateCrysLabel() {
        crystiteLabel.setText("Crystite: " + storeService.getCrystite());
    }

    private void updateFoodLabel() {
        foodLabel.setText("Food: " + storeService.getFood());
    }

    private void updateEnergyLabel() {
        energyLabel.setText("ENERGY: " + storeService.getEnergy());
    }

    private void updateSmithoreLabel() {
        smithoreLabel.setText("Smithore: " + storeService.getSmithore());
    }
}