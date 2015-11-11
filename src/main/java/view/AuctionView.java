package view;

import com.google.inject.Inject;
import data.Repository;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.entity.Player;
import presenters.AuctionPresenter;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by kylemurray on 10/7/15.
 */
public final class AuctionView extends View<AuctionPresenter> {

    // put in buy/sell option for players, make food/energy rounds, timer
    @Inject
    private Repository<Player> playerRepository;
    @FXML
    private Pane pane;

    private Group pane2 = new Group();
    private Timer timer;
    private Timer timer2;
    private Timer timer3;
    private Timer timer4;
    private Timer timer5;
    private Timer timer6;
    private ArrayList<ImageView> playerImageList;
    private ArrayList<Text> resourceLists = new ArrayList<Text>();
    private ArrayList<Text> names = new ArrayList<Text>();
    private boolean canMove;
    private final double bottomlimit = 390;
    private final double toplimit = 150;
    private final long duration = 10000L;

    /**
     * initialises window.
     */
    public void initialize() {
        pane.getChildren().add(pane2);
        playerImageList = new ArrayList<>();
        for (int i = 0; i < playerRepository.size(); i++) {
            ImageView playerImage = MapView.
                    createImageView(playerRepository.get(i).
                            getRace().getImagePath(), 50, 50);
            playerImageList.add(playerImage);
            double deltaX = playerImage.getImage().getWidth() / 2 - 16;
            playerImage.setTranslateX(150 + 150 * i - deltaX);
            double deltaY = playerImage.getImage().getHeight() / 2 - 16;
            playerImage.setTranslateY(390 - deltaY);
            Text playerName = new Text("Player " + (i + 1) + "\n\""
                    + playerRepository.get(i).getName() + "\"");
            names.add(playerName);
            playerName.setTranslateX(150 + 150 * i);
            playerName.setTranslateY(370);
            Text resources = new Text(playerRepository.
                    get(i).getCrystite() + " Crystite\n"
                    + playerRepository.get(i).getEnergy() + " Energy\n"
                    + playerRepository.get(i).getFood() + " Food");
            resourceLists.add(resources);
            resources.setTranslateX(150 + 150 * i);
            resources.setTranslateY(435);
            pane2.getChildren().addAll(playerImage, playerName, resources);
        }
        Text startBidding = new Text(250, 120, "Get Ready to Bid!");
        startBidding.setFont(new Font(40));
        pane2.getChildren().add(startBidding);
        startSmithoreBidding();

        pane.setOnKeyPressed(event -> {
            if (canMove) {
                switch (event.getCode()) {
                    case Q:
                        if (playerImageList.size() > 0 && playerImageList.
                                get(0).getTranslateY() > toplimit) {
                            playerImageList.get(0).setTranslateY(
                                    playerImageList.get(0).getTranslateY()
                                            - 10);
                        }
                        break;
                    case Z:
                        if (playerImageList.size() > 0 && playerImageList.
                                get(0).getTranslateY() < bottomlimit) {
                            playerImageList.get(0).setTranslateY(
                                    playerImageList.get(0).getTranslateY()
                                            + 10);
                        }
                        break;
                    case W:
                        if (playerImageList.size() > 1 && playerImageList.
                                get(1).getTranslateY() > toplimit) {
                            playerImageList.get(1).setTranslateY(
                                    playerImageList.get(1).getTranslateY()
                                            - 10);
                        }
                        break;
                    case X:
                        if (playerImageList.size() > 1 && playerImageList.
                                get(1).getTranslateY() < bottomlimit) {
                            playerImageList.get(1).setTranslateY(
                                    playerImageList.get(1).getTranslateY()
                                            + 10);
                        }
                        break;
                    case E:
                        if (playerImageList.size() > 2 && playerImageList.
                                get(2).getTranslateY() > toplimit) {
                            playerImageList.get(2).setTranslateY(
                                    playerImageList.get(2).getTranslateY()
                                            - 10);
                        }
                        break;
                    case C:
                        if (playerImageList.size() > 2 && playerImageList.
                                get(2).getTranslateY() < bottomlimit) {
                            playerImageList.get(2).setTranslateY(
                                    playerImageList.get(2).getTranslateY()
                                            + 10);
                        }
                        break;
                    case R:
                        if (playerImageList.size() > 3 && playerImageList.
                                get(3).getTranslateY() > toplimit) {
                            playerImageList.get(3).setTranslateY(
                                    playerImageList.get(3).getTranslateY()
                                            - 10);
                        }
                        break;
                    case V:
                        if (playerImageList.size() > 3 && playerImageList.
                                get(3).getTranslateY() < bottomlimit) {
                            playerImageList.get(3).setTranslateY(
                                    playerImageList.get(3).getTranslateY()
                                            + 10);
                        }
                        break;
                    default:
                        break;
                }
            }
        });

//        pane.getChildren().add(new Rectangle(100, 100));

        // Stream or for loop players
//        player1 = new ImageView(new Image(
//                      "/races/Human.png", 25, 25, true, false));
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

    /**
     * switches presenter to map.
     */
    public void handleContinueButtonAction() {
        presenter.switchPresenter("map_grid_tile_select.fxml");
    }

    /**
     * starts bidding of smithore.
     */
    public void startSmithoreBidding() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() ->
                {
                    pane2.getChildren().clear();
                    for (int i = 0; i < playerRepository.size(); i++) {
                        Text smithore = new Text(playerRepository.
                                get(i).getSmithore() + " Smithore");
                        smithore.setTranslateX(150 + 150 * i);
                        smithore.setTranslateY(435);
                        Text auctionText = new Text(
                                250, 120, "Smithore Auction");
                        auctionText.setFont(new Font(40));
                        pane2.getChildren().addAll(
                                playerImageList.get(i), smithore, auctionText);
                        resetCharacters();
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
        }, duration + 4000L);
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

    /**
     * starts bidding of food.
     */
    public void startFoodBidding() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() ->
                {
                    pane2.getChildren().clear();
                    for (int i = 0; i < playerRepository.size(); i++) {
                        Text food = new Text(playerRepository.
                                get(i).getFood() + " Food");
                        food.setTranslateX(150 + 150 * i);
                        food.setTranslateY(435);
                        Text auctionText = new Text(
                                250, 120, "Food Auction");
                        auctionText.setFont(new Font(40));
                        pane2.getChildren().addAll(
                                playerImageList.get(i), food, auctionText);
                        resetCharacters();
                        canMove = true;
                    }
                });
            }
        }, 4000L);
        timer2 = new Timer();
        timer2.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(AuctionView.this::startEnergyBidding);
            }
        }, duration + 4000L);
    }

    /**
     * start bidding of energy.
     */
    public void startEnergyBidding() {
    }

    /**
     * show how much resource each player has.
     */
    public void quantifyResources() {
    }

    /**
     * players choose whether they are buying or selling.
     */
    public void buyOrSell() {
    }

    /**
     * walk up and down to sell/buy.
     */
    public void auctioning() {
    }

    /**
     * reset characters to default.
     */
    public void resetCharacters() {
        for (int i = 0; i < playerRepository.size(); i++) {
//            double deltaY = (new Image(playerRepository.
//                                  get(i).getRace().getImagePath())).
//                                      getHeight()/2 - 16;
            playerImageList.get(i).setTranslateY(385);
        }
    }
}

