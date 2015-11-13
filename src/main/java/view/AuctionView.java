package view;

import com.google.inject.Inject;
import com.google.inject.name.Named;
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
 * View that manages the UI for the Auction screen, where players can buy and sell items
 */
public class AuctionView extends View<AuctionPresenter> {

    // put in buy/sell option for players, make food/energy rounds, timer
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

    private List<ImageView> playerImageList;
    private List<Text> resourceLists = new ArrayList<Text>();
    private List<Text> names = new ArrayList<Text>();
    private boolean canMove;

    @Inject @Named("TextInitialX")
    private int textInitialX;
    @Inject @Named("TextInitialY")
    private int textInitialY;
    @Inject @Named("MainFontSize")
    private int fontSize;
    @Inject @Named("StartBiddingDelay")
    private int startBiddingDelay;

    private static final double BOTTOM_LIMIT = 390;
    private static final double TOP_LIMIT = 150;
    // keep this here with the rest of the constants //
    private static final long DURATION = 10000L;
    private static final int PLAYER_IMAGE_SIZE = 50;

    private static final int MARGIN = 16;



    /**
     * initialises window.
     */
    public void initialize() {
        pane.getChildren().add(pane2);
        List<Player> playerList = new LinkedList<>(playerRepository.getAll());
        playerImageList = new ArrayList<>();
        for (int i = 0; i < playerRepository.size(); i++) {
            ImageView playerImage = MapView.createImageView(playerList.get(i).getRace().getImagePath(),
                    PLAYER_IMAGE_SIZE, PLAYER_IMAGE_SIZE);
            playerImageList.add(playerImage);
            double deltaX = playerImage.getImage().getWidth() / 2 - MARGIN;
            playerImage.setTranslateX(150 + 150 * i - deltaX);
            double deltaY = playerImage.getImage().getHeight() / 2 - MARGIN;
            playerImage.setTranslateY(390 - deltaY);
            Text playerName = new Text("Player " + (i + 1) + "\n\""
                    + playerList.get(i).getName() + "\"");
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
        Text startBidding = new Text(textInitialX, textInitialY, "Get Ready to Bid!");
        startBidding.setFont(new Font(40));
        pane2.getChildren().add(startBidding);
        startSmithoreBidding();

        pane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                handleKeyPressed(event);
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
        getPresenter().switchPresenter("map_grid_tile_select.fxml");
    }

    private void translateUp(int playerImageIndex) {
        if (playerImageList.size() > playerImageIndex
                && playerImageList.get(playerImageIndex).getTranslateY() > TOP_LIMIT) {

            playerImageList.get(playerImageIndex).setTranslateY(
                    playerImageList.get(playerImageIndex).getTranslateY() - 10);
        }
    }

    private void translateDown(int playerImageIndex) {
        if (playerImageList.size() > playerImageIndex
                && playerImageList.get(playerImageIndex).getTranslateY() < BOTTOM_LIMIT) {

                playerImageList.get(playerImageIndex).setTranslateY(
                        playerImageList.get(playerImageIndex).getTranslateY() + 10);
        }
    }

    private void handleKeyPressed(KeyEvent event) {
        if (canMove) {
            switch (event.getCode()) {
                case Q:
                    translateUp(0);
                    break;
                case Z:
                    translateDown(0);
                    break;
                case W:
                    translateUp(1);
                    break;
                case X:
                    translateDown(1);
                    break;
                case E:
                    translateUp(2);
                    break;
                case C:
                    translateDown(2);
                    break;
                case R:
                    translateUp(3);
                    break;
                case V:
                    translateDown(3);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * starts bidding of smithore.
     */
    public void startSmithoreBidding() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() { setAuctionSmithore(); }
                });
            }
        }, startBiddingDelay);
        timer2 = new Timer();
        timer2.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new TimerTask() {
                    @Override
                    public void run() {
                        AuctionView.this.startFoodBidding();
                    }
                });
            }
        }, DURATION + startBiddingDelay);
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

    private void setAuctionSmithore() {
        pane2.getChildren().clear();
        List<Player> playerList = new LinkedList<Player>(playerRepository.getAll());

        for (int i = 0; i < playerRepository.size(); i++) {

            Text smithore = new Text(playerList.get(i)
                    .getSmithore() + " Smithore");
            smithore.setTranslateX(150 + 150 * i);
            smithore.setTranslateY(435);
            Text auctionText = new Text(textInitialX, textInitialY, "Smithore Auction");
            auctionText.setFont(new Font(fontSize));
            pane2.getChildren().addAll(
                    playerImageList.get(i), smithore, auctionText);
            resetCharacters();
            canMove = true;
        }
    }

    /**
     * starts bidding of food.
     */
    public void startFoodBidding() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        pane2.getChildren().clear();
                        for (int i = 0; i < playerRepository.size(); i++) {
                            Text food = new Text(playerRepository.get(i).getFood() + " Food");
                            food.setTranslateX(150 + 150 * i);
                            food.setTranslateY(435);
                            Text auctionText = new Text(textInitialX, textInitialY, "Food Auction");
                            auctionText.setFont(new Font(fontSize));
                            pane2.getChildren().addAll(playerImageList.get(i), food, auctionText);
                            resetCharacters();
                            canMove = true;
                        }
                    }
                });
            }
        }, startBiddingDelay);
        timer2 = new Timer();
        timer2.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new TimerTask() {
                    @Override
                    public void run() {
                        AuctionView.this.startEnergyBidding();
                    }
                });
            }
        }, DURATION + startBiddingDelay);
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
//            double deltaY = (new Image(playerList.get(i).getRace().getImagePath())).getHeight()/2 - 16;
            playerImageList.get(i).setTranslateY(385);
        }
    }

    public void setTextInitialX(int pTextInitialX) {
        this.textInitialX = pTextInitialX;
    }

    public void setTextInitialY(int pTextInitialY) {
        this.textInitialY = pTextInitialY;
    }

    public void setFontSize(int pFontSize) {
        this.fontSize = pFontSize;
    }

    public void setStartBiddingDelay(int pStartBiddingDelay) {
        this.startBiddingDelay = pStartBiddingDelay;
    }
}

