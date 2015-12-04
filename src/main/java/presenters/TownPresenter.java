package presenters;

import com.google.inject.Inject;
import com.google.inject.name.Named;
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
    private DefaultTurnService turnService;

    @Inject
    private StoreService storeService;

    @Inject @Named("FirstPubRoundThreshold")
    private int firstPubRoundThreshold;
    @Inject @Named("SecondPubRoundThreshold")
    private int secondPubRoundThreshold;
    @Inject @Named("FirstPubWinningLimit")
    private int firstPubWinningLimit;
    @Inject @Named("SecondPubWinningLimit")
    private int secondPubWinningLimit;
    @Inject @Named("ThirdPubWinningLimit")
    private int thirdPubWinningLimit;


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
        getStoreService().decrementMuleCount();
        getTurnService().getCurrentPlayer().addMule(mule);
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
        if (getTurnService().isTurnInProgress()) {
            getTurnService().removeTurnEndListener(this);
        }
        return (MapPresenter) getContext().showScreenUninitialized("map_grid.fxml");
    }

    public void handlePubClick(ActionEvent event) {
        int amountToAdd;
        if (getTurnService().getRoundNumber() < firstPubRoundThreshold) {
            amountToAdd = firstPubWinningLimit;
        } else if (getTurnService().getRoundNumber() > secondPubRoundThreshold) {
            amountToAdd = secondPubWinningLimit;
        } else {
            amountToAdd = thirdPubWinningLimit;
        }

        if (turnService.isTurnInProgress()) {
            turnService.getCurrentPlayer().offsetMoney(amountToAdd + (int) (Math.random() * turnService.getTimeLeftInTurn()));

            // must remove so we have the chance to open auction screen //
            turnService.removeTurnEndListener(this);

            turnService.endTurn();
        }

        if (turnService.isAllTurnsOver()) {
            getContext().showScreen("auction.fxml");
        } else {
            getContext().showScreen("map_grid.fxml");
            //Turn seems to end on its own idk how
        }

    }

    @Override
    public void onTurnEnd(Player player) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                TownPresenter.this.getContext().showScreen("map_grid.fxml");
            }
        });
    }

    public DefaultTurnService getTurnService() {
        return turnService;
    }

    public void setTurnService(DefaultTurnService pTurnService) {
        this.turnService = pTurnService;
    }

    public StoreService getStoreService() {
        return storeService;
    }

    public void setStoreService(StoreService pStoreService) {
        this.storeService = pStoreService;
    }

    public void setSecondPubRoundThreshold(int pSecondPubRoundThreshold) {
        this.secondPubRoundThreshold = pSecondPubRoundThreshold;
    }

    public void setFirstPubRoundThreshold(int pFirstPubRoundThreshold) {
        this.firstPubRoundThreshold = pFirstPubRoundThreshold;
    }
}
