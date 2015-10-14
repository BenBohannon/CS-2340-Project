package view;

import com.google.inject.Inject;
import data.Repository;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import model.entity.Player;
import model.service.DefaultTurnService;
import presenters.AuctionPresenter;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by kylemurray on 10/7/15.
 */
public class AuctionView extends View<AuctionPresenter> {

    @Inject
    private DefaultTurnService turnService;
    @FXML
    private Pane pane;

    private Timer timer;
    private ArrayList<ImageView> playerImageList;
    private Repository<Player> playerRepository;

    public void initialize() {
        playerRepository = turnService.getAllPlayers();
        playerImageList = new ArrayList<>();
        for (int i = 0; i < playerRepository.size(); i++) {
            ImageView playerImage = new ImageView(playerRepository.get(i).getRaceImage().getImage());
            playerImageList.add(playerImage);
                double deltaX = playerRepository.get(i).getRaceImage().getImage().getWidth()/2 - 16;
            playerImage.setTranslateX(150 + 150 * i - deltaX);
                double deltaY = playerRepository.get(i).getRaceImage().getImage().getHeight()/2 - 16;
            playerImage.setTranslateY(390 - deltaY);
            Text playerName = new Text("Player " + (i + 1) + "\n\"" + playerRepository.get(i).getName() + "\"");
            playerName.setTranslateX(150 + 150 * i);
            playerName.setTranslateY(370);
            Text resourceScore = new Text(playerRepository.get(i).getCrystite() + " Crystite\n"
                    + playerRepository.get(i).getEnergy() + " Energy\n"
                    + playerRepository.get(i).getFood() + " Food");
            resourceScore.setTranslateX(150 + 150 * i);
            resourceScore.setTranslateY(435);
            pane.getChildren().addAll(playerImage, playerName, resourceScore);
        }
//        pane.getChildren().add(new Rectangle(100, 100));

        // Stream or for loop players
//        player1 = new ImageView(new Image("/races/ACharacter.png", 25, 25, true, false));
//        player1.setTranslateX(150);
//        player1.setTranslateY(700);
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