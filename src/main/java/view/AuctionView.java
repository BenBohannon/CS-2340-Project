package view;

import com.google.inject.Inject;
import data.abstractsources.Repository;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.entity.Player;
import presenters.AuctionPresenter;

import java.util.*;

/**
 * Created by kylemurray on 10/7/15.
 */
public class AuctionView extends View<AuctionPresenter> {

    //TODO: fix holding down buttons, put in store, put in buy/sell option for players, make FOOD/ENERGY rounds, timer
    @Inject
    private Repository<Player> playerRepository;
    @FXML
    private Pane pane;

    private Group pane2 = new Group();
    private Timer timer;
    private Timer timer2;
    // will be used in future versions //
    private Timer timer3;
    private Timer timer4;
    private Timer timer5;
    private Timer timer6;
    private ArrayList<ImageView> playerImageList;
    private ArrayList<Text> resourceLists = new ArrayList<Text>();
    private ArrayList<Text> names = new ArrayList<Text>();
    private boolean canMove;
    private double BOTTOMLIMIT = 390;
    private double TOPLIMIT = 150;
    private long DURATION = 10000L;

    public void initialize() {
        pane.getChildren().add(pane2);
        List<Player> playerList = new LinkedList<>(playerRepository.getAll());
        playerImageList = new ArrayList<>();
        for (int i = 0; i < playerRepository.size(); i++) {
            ImageView playerImage = MapView.createImageView(playerList.get(i).getRace().getImagePath(), 50, 50);
            playerImageList.add(playerImage);
                double deltaX = playerImage.getImage().getWidth()/2 - 16;
            playerImage.setTranslateX(150 + 150 * i - deltaX);
                double deltaY = playerImage.getImage().getHeight()/2 - 16;
            playerImage.setTranslateY(390 - deltaY);
            Text playerName = new Text("Player " + (i + 1) + "\n\"" + playerList.get(i).getName() + "\"");
            names.add(playerName);
            playerName.setTranslateX(150 + 150 * i);
            playerName.setTranslateY(370);
            Text resources = new Text(playerList.get(i).getCrystite() + " Crystite\n"
                    + playerList.get(i).getEnergy() + " ENERGY\n"
                    + playerList.get(i).getFood() + " Food");
            resourceLists.add(resources);
            resources.setTranslateX(150 + 150 * i);
            resources.setTranslateY(435);
            pane2.getChildren().addAll(playerImage, playerName, resources);
        }
        Text startBidding = new Text(250, 120, "Get Ready to Bid!");
        startBidding.setFont(new Font(40));
        pane2.getChildren().add(startBidding);
        startSmithoreBidding();

        pane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (canMove) {
                    switch (event.getCode()) {
                        case Q:
                            if (playerImageList.size() > 0 && playerImageList.get(0).getTranslateY() > TOPLIMIT) {
                                playerImageList.get(0).setTranslateY(playerImageList.get(0).getTranslateY() - 10);
                            }
                            break;
                        case Z:
                            if (playerImageList.size() > 0 && playerImageList.get(0).getTranslateY() < BOTTOMLIMIT) {
                                playerImageList.get(0).setTranslateY(playerImageList.get(0).getTranslateY() + 10);
                            }
                            break;
                        case W:
                            if (playerImageList.size() > 1 && playerImageList.get(1).getTranslateY() > TOPLIMIT) {
                                playerImageList.get(1).setTranslateY(playerImageList.get(1).getTranslateY() - 10);
                            }
                            break;
                        case X:
                            if (playerImageList.size() > 1 && playerImageList.get(1).getTranslateY() < BOTTOMLIMIT) {
                                playerImageList.get(1).setTranslateY(playerImageList.get(1).getTranslateY() + 10);
                            }
                            break;
                        case E:
                            if (playerImageList.size() > 2 && playerImageList.get(2).getTranslateY() > TOPLIMIT) {
                                playerImageList.get(2).setTranslateY(playerImageList.get(2).getTranslateY() - 10);
                            }
                            break;
                        case C:
                            if (playerImageList.size() > 2 && playerImageList.get(2).getTranslateY() < BOTTOMLIMIT) {
                                playerImageList.get(2).setTranslateY(playerImageList.get(2).getTranslateY() + 10);
                            }
                            break;
                        case R:
                            if (playerImageList.size() > 3 && playerImageList.get(3).getTranslateY() > TOPLIMIT) {
                                playerImageList.get(3).setTranslateY(playerImageList.get(3).getTranslateY() - 10);
                            }
                            break;
                        case V:
                            if (playerImageList.size() > 3 && playerImageList.get(3).getTranslateY() < BOTTOMLIMIT) {
                                playerImageList.get(3).setTranslateY(playerImageList.get(3).getTranslateY() + 10);
                            }
                            break;
                    }
                }
            }
        });

//        pane.getChildren().add(new Rectangle(100, 100));

        // Stream or for loop players
//        player1 = new ImageView(new Image("/races/Human.png", 25, 25, true, false));
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
        getPresenter().switchPresenter("map_grid_tile_select.fxml");
    }

    public void startSmithoreBidding() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() ->
                {
                    pane2.getChildren().clear();
                    List<Player> playerList = new LinkedList<Player>(playerRepository.getAll());

                    for (int i = 0; i < playerRepository.size(); i++) {
                        Text smithore = new Text(playerList.get(i).getSmithore() + " Smithore");
                        smithore.setTranslateX(150 + 150 * i);
                        smithore.setTranslateY(435);
                        Text auctionText = new Text(250, 120, "Smithore Auction");
                        auctionText.setFont(new Font(40));
                        pane2.getChildren().addAll(playerImageList.get(i), smithore, auctionText);
                        resetCharacters(); //TODO: add this method
                        canMove = true;
                    }
                });
            }
        }, 4000L);
        timer2 = new Timer();
        timer2.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(AuctionView.this::startFoodBidding);
            }
        }, DURATION + 4000L);
//        timer.schedule(new TimerTask() {
//                           @Override
//                           public void run() {
//                               Platform.runLater(() ->
//                               {
//                                   quantifyResources();
//                                   buyOrSell();
//                                   auctioning();
//                               });
//                           }
//                       },
//                300L, 10L);
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

    public void resetCharacters() {
        for (int i = 0; i < playerRepository.size(); i++) {
//            double deltaY = (new Image(playerList.get(i).getRace().getImagePath())).getHeight()/2 - 16;
            playerImageList.get(i).setTranslateY(385);
        }
    }
}