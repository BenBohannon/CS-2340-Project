package view;

import com.google.inject.Inject;
import data.Repository;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.entity.Player;
import model.service.DefaultTurnService;
import presenters.AuctionPresenter;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by kylemurray on 10/7/15.
 */
public class AuctionView extends View<AuctionPresenter> {

    @FXML
    private Pane pane;

    @Inject
    private Repository<Player> playerRepository;

    private Timer timer;
    private ImageView player1;
    private ImageView player2;
    private ImageView player3;
    private ImageView player4;
    private Text getReadyText;
    private Text foodText;
    private Text energyText;
    private Text smithoreText;

    public void initialize() {
        // Stream or for loop players
        player1 = new ImageView(new Image("/races/Character.png", 25, 25, true, false));
        player1.setTranslateX(150);
        player1.setTranslateY(700);
        getReadyText = new Text(200, 100, "Auction Round... Get Ready!");
        getReadyText.setFont(new Font(40));
        pane.getChildren().addAll(player1, getReadyText);
        getReadyText.toFront();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() ->
                {
                    pane.getChildren().remove(getReadyText);
                    startFoodBidding();
                });
            }
        }, 4000L);
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

    }

    public void startFoodBidding() {
        foodText = new Text(200, 50, "Food auction");
        foodText.setFont(new Font(40));
        pane.getChildren().add(foodText);
        getReadyText.toFront();
        timer.schedule(new TimerTask() {
                           @Override
                           public void run() {
                               Platform.runLater(() ->
                               {
                                   quantifyResources();
                                   // delay
                                   buyOrSell();
                                   // wait for keystrokes
                                   auctioning();
                               });
                           }
                       },
                300L, 10L);
    }

    public void startEnergyBidding() {

    }

    public void quantifyResources() {
        Text player1quant = new Text(playerRepository.get(0).getFood());
        // show how much resource each player has
    }

    public void buyOrSell() {
        // players choose whether they are buying or selling
    }

    public void auctioning() {
        // walk up and down to sell/buy
    }
}