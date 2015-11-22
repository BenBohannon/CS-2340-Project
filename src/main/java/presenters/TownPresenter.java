package presenters;

import com.google.inject.Inject;
import model.service.StoreService;
import model.service.TurnEndListener;
import javafx.application.Platform;
import javafx.scene.control.Button;
import model.entity.Player;
import model.service.DefaultTurnService;
import javafx.event.ActionEvent;
import model.entity.Mule;
import model.entity.MuleType;

/**
 * Created by brian on 9/17/15.
 */
public class TownPresenter extends Presenter implements TurnEndListener {

    @Inject
    DefaultTurnService turnService;

    @Inject
    StoreService storeService;

    @Override
    public void initialize() {
        turnService.addTurnEndListener(this);
    }

    public void handleMuleClick(ActionEvent event) {
        String buttonText = ((Button) event.getSource()).getText().trim();
        Mule mule = null;
        if (buttonText.equals("ENERGY MULES")) {
            mule = new Mule(MuleType.Energy);
        } else if (buttonText.equals("FOOD MULES")) {
            mule = new Mule(MuleType.Food);
        } else if (buttonText.equals("SMITHORE MULES")) {
            mule = new Mule(MuleType.Smithore);
        } else {
            mule = new Mule(MuleType.Crysite);
        }
        storeService.decrementMuleCount();
        turnService.getCurrentPlayer().addMule(mule);
        MapPresenter presenter = returnToMapUninitialized();
        presenter.setIsPlacingMule(true, mule);
        presenter.initialize();
    }

    public void handleMapClick(ActionEvent event) {
        returnToMapUninitialized().initialize();
    }

    public void handleStoreClick(ActionEvent event) {
        getContext().showScreen("store.fxml");
    }

    /**
     * helper method for logging the unimplemented behavior of returning to the model.map
     * UPDATE: now returns to model.map
     */
    private MapPresenter returnToMapUninitialized() {
        if (turnService.isTurnInProgress()) {
            turnService.removeTurnEndListener(this);
        }
        return (MapPresenter) getContext().showScreenUninitialized("map_grid.fxml");
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

        if (turnService.isTurnInProgress()) {
            turnService.getCurrentPlayer().offsetMoney(amountToAdd + (int) (Math.random() * turnService.getTimeLeftInTurn()));
            turnService.endTurn();
        }

        if (turnService.isAllTurnsOver()) {
            System.out.println("show auction screen");
            getContext().showScreen("auction.fxml");
        } else {
            getContext().showScreen("map_grid.fxml");
            //Turn seems to end on its own idk how
        }

    }

    @Override
    public void onTurnEnd(Player player) {
        Platform.runLater(() -> getContext().showScreen("map_grid.fxml"));
    }
}
