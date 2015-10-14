package view;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import presenters.AuctionPresenter;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by kylemurray on 10/7/15.
 */
public class AuctionView extends View<AuctionPresenter> {

    private Timer timer;
    private ImageView player1;
    private ImageView player2;
    private ImageView player3;
    private ImageView player4;

    public void initialize() {
        // Stream or for loop players
        player1 = new ImageView(new Image("/races/ACharacter.png", 25, 25, true, false));
        player1.setTranslateX(150);
        player1.setTranslateY(700);
        // Key listener for


//        timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                presenter.switchPresenter("map_grid_tile_select.fxml");
//            }
//        }, 4000L);
    }

    public void handleContinueButtonAction() {
        presenter.switchPresenter("map_grid_tile_select.fxml");
    }

    public void startSmithoreBidding() {
        timer.schedule(new TimerTask() {
                           @Override
                           public void run() {
                               Platform.runLater(() ->
                               {
                                   quantifyResources();
                                   buyOrSell();
                                   auctioning();
                               });
                           }
                       },
                300L, 10L);
    }

    public void startFoodBidding() {

    }

    public void startEnergyBidding() {

    }

    public void quantifyResources() {
        // show how much resource each player has
    }

    public void buyOrSell() {
        // players choose whether they are buying or selling
    }

    public void auctioning() {
        // walk up and down to sell/buy
    }
}