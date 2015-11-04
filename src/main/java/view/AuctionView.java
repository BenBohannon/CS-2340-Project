package view;

import com.google.inject.Inject;
import data.Repository;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
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

    //TODO: fix holding down buttons, put in store, put in buy/sell option for players, make food/energy rounds, timer

    @Inject
    private DefaultTurnService turnService;
    @FXML
    private Pane pane;

    private Group pane2 = new Group();

    private Timer timer6;
    private ArrayList<ImageView> playerImageList;
    private ArrayList<Text> resourceLists = new ArrayList<Text>();
    private ArrayList<Text> names = new ArrayList<Text>();
    private Repository<Player> playerRepository;
    private boolean canMove;
    private boolean canFlip;
    private double BOTTOMLIMIT = 385;
    private double TOPLIMIT = 150;
    private double RIGHTLIMIT = 750;
    private long SHOWRESOURCEDURATION = 2000L;
    private long BUYORSELLDURATION = 4000L;
    private long BIDDURATION = 6000L;
    private String resource;
    Text screenText = new Text(250, 120, "");
    private ArrayList<Text> buySell = new ArrayList<Text>();
    private volatile double clockTime;
    private Line topBidLine = new Line(100, 0, RIGHTLIMIT, 0);
    private Line bottomBidLine = new Line(100, 0, RIGHTLIMIT, 0);

    public void initialize() {
        pane.getChildren().add(pane2);
        playerRepository = turnService.getAllPlayers();
        playerImageList = new ArrayList<>();
        for (int i = 0; i < playerRepository.size(); i++) {
            ImageView playerImage = MapView.createImageView(playerRepository.get(i).getRace().getImagePath(), 50, 50);
            playerImageList.add(playerImage);
                double deltaX = playerImage.getImage().getWidth()/2 - 16;
            playerImage.setTranslateX(150 + 150 * i - deltaX);
                double deltaY = playerImage.getImage().getHeight()/2 - 16;
            playerImage.setTranslateY(390 - deltaY);
            Text playerName = new Text("Player " + (i + 1) + "\n\"" + playerRepository.get(i).getName() + "\"");
            names.add(playerName);
            playerName.setTranslateX(150 + 150 * i);
            playerName.setTranslateY(370);
            Text resources = new Text(playerRepository.get(i).getCrystite() + " Crystite\n"
                    + playerRepository.get(i).getEnergy() + " Energy\n"
                    + playerRepository.get(i).getFood() + " Food");
            resourceLists.add(resources);
            resources.setTranslateX(150 + 150 * i);
            resources.setTranslateY(450);
            pane2.getChildren().addAll(playerImage, playerName, resources);
        }
        screenText.setText("Get Ready to Bid!");
        screenText.setFont(new Font(40));
        topBidLine.setTranslateY(TOPLIMIT + 50);
        bottomBidLine.setTranslateY(BOTTOMLIMIT - 10);
        pane2.getChildren().add(screenText);
        for (long i = 0; i < 3; i++) {
            startResourceBidding(i);
        }

        pane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (canMove) {
                    double topLine = topBidLine.getTranslateY();
                    double bottomLine = bottomBidLine.getTranslateY();
                    double asdf = bottomBidLine.getStartY();
                    double playerY = playerImageList.get(0).getTranslateY();
                    switch (event.getCode()) {
                        case Q:
                            if (playerImageList.size() > 0 && playerImageList.get(0).getTranslateY() > TOPLIMIT) {
                                playerImageList.get(0).setTranslateY(playerImageList.get(0).getTranslateY() - 10);
                            }
                            setLines();
                            break;
                        case Z:
                            if (playerImageList.size() > 0 && playerImageList.get(0).getTranslateY() < BOTTOMLIMIT) {
                                playerImageList.get(0).setTranslateY(playerImageList.get(0).getTranslateY() + 10);
                            }
                            setLines();
                            break;
                        case W:
                            if (playerImageList.size() > 1 && playerImageList.get(1).getTranslateY() > TOPLIMIT) {
                                playerImageList.get(1).setTranslateY(playerImageList.get(1).getTranslateY() - 10);
                            }
                            setLines();
                            break;
                        case X:
                            if (playerImageList.size() > 1 && playerImageList.get(1).getTranslateY() < BOTTOMLIMIT) {
                                playerImageList.get(1).setTranslateY(playerImageList.get(1).getTranslateY() + 10);
                            }
                            setLines();
                            break;
                        case E:
                            if (playerImageList.size() > 2 && playerImageList.get(2).getTranslateY() > TOPLIMIT) {
                                playerImageList.get(2).setTranslateY(playerImageList.get(2).getTranslateY() - 10);
                            }
                            setLines();
                            break;
                        case C:
                            if (playerImageList.size() > 2 && playerImageList.get(2).getTranslateY() < BOTTOMLIMIT) {
                                playerImageList.get(2).setTranslateY(playerImageList.get(2).getTranslateY() + 10);
                            }
                            setLines();
                            break;
                        case R:
                            if (playerImageList.size() > 3 && playerImageList.get(3).getTranslateY() > TOPLIMIT) {
                                playerImageList.get(3).setTranslateY(playerImageList.get(3).getTranslateY() - 10);
                            }
                            setLines();
                            break;
                        case V:
                            if (playerImageList.size() > 3 && playerImageList.get(3).getTranslateY() < BOTTOMLIMIT) {
                                playerImageList.get(3).setTranslateY(playerImageList.get(3).getTranslateY() + 10);
                            }
                            setLines();
                            break;
                    }
                } else if (canFlip) {
                    switch (event.getCode()) {
                        case Q:
                            playerImageList.get(0).setTranslateY(TOPLIMIT);
                            buySell.get(0).setText("SELLING");
                            buySell.get(0).setTranslateY(TOPLIMIT);
                            break;
                        case Z:
                            playerImageList.get(0).setTranslateY(BOTTOMLIMIT);
                            buySell.get(0).setText("BUYING");
                            buySell.get(0).setTranslateY(BOTTOMLIMIT + 20);
                            break;
                        case W:
                            playerImageList.get(1).setTranslateY(TOPLIMIT);
                            buySell.get(1).setText("SELLING");
                            buySell.get(1).setTranslateY(TOPLIMIT);
                            break;
                        case X:
                            playerImageList.get(1).setTranslateY(BOTTOMLIMIT);
                            buySell.get(1).setText("BUYING");
                            buySell.get(1).setTranslateY(BOTTOMLIMIT + 20);
                            break;
                        case E:
                            playerImageList.get(2).setTranslateY(TOPLIMIT);
                            buySell.get(2).setText("SELLING");
                            buySell.get(2).setTranslateY(TOPLIMIT);
                            break;
                        case C:
                            playerImageList.get(2).setTranslateY(BOTTOMLIMIT);
                            buySell.get(2).setText("BUYING");
                            buySell.get(2).setTranslateY(BOTTOMLIMIT + 20);
                            break;
                        case R:
                            playerImageList.get(3).setTranslateY(TOPLIMIT);
                            buySell.get(3).setText("SELLING");
                            buySell.get(3).setTranslateY(TOPLIMIT);
                            break;
                        case V:
                            playerImageList.get(3).setTranslateY(BOTTOMLIMIT);
                            buySell.get(3).setText("BUYING");
                            buySell.get(3).setTranslateY(BOTTOMLIMIT + 20);
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
        presenter.switchPresenter("map_grid_tile_select.fxml");
    }

    public void startResourceBidding(long i) {
        Timer timer;
        Timer timer2;
        Timer timer3;
        Timer timer4;
        Timer timer32;
        // First initialize all screen items that must be referenced multiple times
        Rectangle whiteClock = new Rectangle(10, BOTTOMLIMIT - TOPLIMIT, Color.WHITE);
        Rectangle redClock = new Rectangle(10, BOTTOMLIMIT - TOPLIMIT, Color.RED);
        Group clock = new Group(whiteClock, redClock);
        clock.setTranslateX(RIGHTLIMIT);
        clock.setTranslateY(TOPLIMIT);
        Long TOTALDELAY = SHOWRESOURCEDURATION + BUYORSELLDURATION + BIDDURATION;
        resource = new String();
        // First timer says when to reset the auction screen to next resource and show quantities. No movement
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() ->
                {
                    if (i == 0) {
                        resource = "Smithore";
                    } else if (i == 1) {
                        resource = "Food";
                    } else if (i == 2) {
                        resource = "Energy";
                    } else if (i == 3) {
                        resource = "Crystite";
                    }
                    resetPane();
                    pane2.getChildren().add(screenText);
                    screenText.setText("Get ready...");
                    System.out.println(1 + i * 5 + "\n");
                });
            }
        }, TOTALDELAY * i + 2005L);
        // Second timer says when to make players choose buy or sell
        timer2 = new Timer();
        timer2.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() ->
                {
//                    timer.cancel();
//                    resetPane();
                    canFlip = true;
                    screenText.setText("Buying or selling?");
                    System.out.println(2 + i * 5 + "\n");
//                    pane2.getChildren().add(new Text("Buying or selling?"));
                    // Handle all movement events and visual timer
                });
            }
        }, TOTALDELAY * i + SHOWRESOURCEDURATION + 2000L);
        // Third timer says when to start bidding. This is a one time event. Initializes bid screen items
        timer3 = new Timer();
        timer3.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() ->
                {
//                    timer2.cancel();
                    canFlip = false;
                    canMove = true;
                    screenText.setText("Start bidding");
                    clockTime = BIDDURATION;
                    pane2.getChildren().addAll(topBidLine, bottomBidLine, clock);
                    System.out.println(3 + i * 5 + "\n");
                });
            }
        }, TOTALDELAY * i + BUYORSELLDURATION + SHOWRESOURCEDURATION + 2000L);
        // Third.2 timer says when to start bidding. This timer is repeating and controls the timer and movements
        timer32 = new Timer();
        timer32.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() ->
                {
//                    if (i == 0) {
                        clockTime = clockTime - 100L;
                        double clockHeight = whiteClock.getHeight();
                        redClock.setHeight(clockHeight * clockTime / BIDDURATION);
//                    }
//                    System.out.println(4 + i * 5 + "\n");
                });
            }
        }, TOTALDELAY * i + BUYORSELLDURATION + SHOWRESOURCEDURATION + 2005L, 100L);
        // Fourth timer says when to end timer3 and reset everything back for the next round to begin
        timer4 = new Timer();
        timer4.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() ->
                {
//                    timer3.cancel();
                    timer32.cancel();
                    canMove = false;
                    System.out.println(5 + i * 5 + "\n");
                    // Reset everything and cancel timer3
//                    timer4.cancel();
                });
            }
        }, TOTALDELAY * i + BIDDURATION + BUYORSELLDURATION + SHOWRESOURCEDURATION + 2000L);
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
//            double deltaY = (new Image(playerRepository.get(i).getRace().getImagePath())).getHeight()/2 - 16;
            playerImageList.get(i).setTranslateY(385);
        }
    }

    public void resetPane() {
        pane2.getChildren().clear();
        Text auctionText = new Text(250, 80, resource + " Auction");
        auctionText.setFont(new Font(40));
        auctionText.setFill(Color.BLUE);
        Text money = new Text(80, 450, "MONEY:");
        buySell.clear();
        for (int j = 0; j < playerRepository.size(); j++) {
            Text buyText = new Text("BUYING");
            buyText.setFill(Color.GREEN);
            buySell.add(buyText);
            buyText.setTranslateX(150 + 150 * j);
            buyText.setTranslateY(450);
            Text moneyText = new Text("$" + playerRepository.get(j).getMoney());
            moneyText.setTranslateX(150 + 150 * j);
            moneyText.setTranslateY(470);
            Text quantText;
            if (resource.equals("Smithore")) {
                quantText = new Text(playerRepository.get(j).getSmithore() + " " + resource);
            } else if(resource.equals("Food")) {
                quantText = new Text(playerRepository.get(j).getFood() + " " + resource);
            } else if(resource.equals("Energy")){
                quantText = new Text(playerRepository.get(j).getEnergy() + " " + resource);
            } else {
                quantText = new Text("dawg wtf check line 355 of AuctionView");
            }
            quantText.setTranslateX(150 + 150 * j);
            quantText.setTranslateY(490);
            pane2.getChildren().addAll(playerImageList.get(j), quantText, buyText, moneyText);
            resetCharacters();
            // Reset position of bidLines
        }
        pane2.getChildren().addAll(money, auctionText);
    }

    public void setLines() {
        bottomBidLine.setTranslateY(highestBuyer());
        topBidLine.setTranslateY(lowestSeller());
        bottomBidLine.setTranslateY(bottomBidLine.getTranslateY() + 10);
    }

    private double highestBuyer() {
        double topPos = BOTTOMLIMIT;
        for (int j = 0; j < playerRepository.size(); j++) {
            double playerPos = playerImageList.get(j).getTranslateY();
            if (buySell.get(j).equals("BUYING") && playerPos < topPos) {
                topPos = playerPos;
            }
        }
        return topPos;
    }

    private double lowestSeller() {
        double bottomPos = TOPLIMIT;
        for (int j = 0; j < playerRepository.size(); j++) {
            double playerPos = playerImageList.get(j).getTranslateY();
            if (buySell.get(j).equals("SELLING") && playerPos > bottomPos) {
                bottomPos = playerPos;
            }
        }
        return bottomPos;
    }


//    public void resetPane() {
//        pane2.getChildren().clear();
//        Text auctionText = new Text(250, 80, resource + " Auction");
//        auctionText.setFont(new Font(40));
//        Text money = new Text(80, 450, "MONEY:");
//        for (int j = 0; j < playerRepository.size(); j++) {
//            // True means player is buying
//            Text buyText = new Text();
//            if(buyOrSell) {
//                buyText.setText("BUY");
//                buyText.setTranslateX(150 + 150 * j);
//                buyText.setTranslateY(450);
//            } else {
//                buyText.setText("SELL");
//                buyText.setTranslateY(TOPLIMIT - 15);
//            }
//            Text moneyText = new Text("$" + playerRepository.get(j).getMoney());
//            moneyText.setTranslateX(150 + 150 * j);
//            moneyText.setTranslateY(470);
//            Text quantText = new Text(playerRepository.get(j).getSmithore() + " " + resource);
//            quantText.setTranslateX(150 + 150 * j);
//            quantText.setTranslateY(490);
//            pane2.getChildren().addAll(playerImageList.get(j), quantText, buyText, moneyText);
//            resetCharacters();
//        }
//        pane2.getChildren().addAll(money, auctionText);
//    }
}