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

    //TODO: Make resources and money update immediately, don't allow money or resources to drop below 0
    //TODO: When resources or money is too low, player is pushed back, introduce a store

    @Inject
    private DefaultTurnService turnService;
    @FXML
    private Pane pane;

    private Group pane2 = new Group();

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
    private long BIDDURATION = 15000L;
    private String resource;
    Text screenText = new Text(250, 120, "");
    private ArrayList<Boolean> isBuying = new ArrayList<Boolean>();
    private ArrayList<Text> buySell = new ArrayList<Text>();
    private ArrayList<Boolean> buyersInTrans = new ArrayList<Boolean>();
    private ArrayList<Boolean> sellersInTrans = new ArrayList<Boolean>();
    private volatile double clockTime;
    private volatile double greenLineTimer;
    private Line topBidLine = new Line(100, 0, RIGHTLIMIT, 0);
    private Line bottomBidLine = new Line(100, 0, RIGHTLIMIT, 0);
    private Rectangle greenLine = new Rectangle(650, 5, Color.GREEN);
    private double transVal = 0;

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
            Text resources = new Text(playerRepository.get(i).getSmithore() + " Smithore\n"
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
        bottomBidLine.setTranslateY(BOTTOMLIMIT);
        greenLine.setTranslateX(100);
        pane2.getChildren().add(screenText);
        for (long i = 0; i < 3; i++) {
            startResourceBidding(i);
        }

        pane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (canMove) {
                    switch (event.getCode()) {
                        case Q:
                            moveBidderUp(0);
                            break;
                        case Z:
                            moveBidderDown(0);
                            break;
                        case W:
                            moveBidderUp(1);
                            break;
                        case X:
                            moveBidderDown(1);
                            break;
                        case E:
                            moveBidderUp(2);
                            break;
                        case C:
                            moveBidderDown(2);
                            break;
                        case R:
                            moveBidderUp(3);
                            break;
                        case V:
                            moveBidderDown(3);
                            break;
                    }
                } else if (canFlip) {
                    switch (event.getCode()) {
                        case Q:
                            playerImageList.get(0).setTranslateY(TOPLIMIT);
                            buySell.get(0).setText("SELLING");
                            isBuying.set(0, false);
                            buySell.get(0).setTranslateY(TOPLIMIT);
                            break;
                        case Z:
                            playerImageList.get(0).setTranslateY(BOTTOMLIMIT);
                            buySell.get(0).setText("BUYING");
                            isBuying.set(0, true);
                            buySell.get(0).setTranslateY(BOTTOMLIMIT + 65);
                            break;
                        case W:
                            playerImageList.get(1).setTranslateY(TOPLIMIT);
                            buySell.get(1).setText("SELLING");
                            isBuying.set(1, false);
                            buySell.get(1).setTranslateY(TOPLIMIT);
                            break;
                        case X:
                            playerImageList.get(1).setTranslateY(BOTTOMLIMIT);
                            buySell.get(1).setText("BUYING");
                            isBuying.set(1, true);
                            buySell.get(1).setTranslateY(BOTTOMLIMIT + 65);
                            break;
                        case E:
                            playerImageList.get(2).setTranslateY(TOPLIMIT);
                            buySell.get(2).setText("SELLING");
                            isBuying.set(2, false);
                            buySell.get(2).setTranslateY(TOPLIMIT);
                            break;
                        case C:
                            playerImageList.get(2).setTranslateY(BOTTOMLIMIT);
                            buySell.get(2).setText("BUYING");
                            isBuying.set(2, true);
                            buySell.get(2).setTranslateY(BOTTOMLIMIT + 65);
                            break;
                        case R:
                            playerImageList.get(3).setTranslateY(TOPLIMIT);
                            buySell.get(3).setText("SELLING");
                            isBuying.set(3, false);
                            buySell.get(3).setTranslateY(TOPLIMIT);
                            break;
                        case V:
                            playerImageList.get(3).setTranslateY(BOTTOMLIMIT);
                            buySell.get(3).setText("BUYING");
                            isBuying.set(3, true);
                            buySell.get(3).setTranslateY(BOTTOMLIMIT + 65);
                            break;
                    }
                }
            }
        });

//        pane.getChildren().add(new Rectangle(100, 100));

        // Stream or for loop players
        // Key listener for


//        timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                presenter.switchPresenter("map_grid_tile_select.fxml");
//            }
//        }, 4000L);
//        topBidLine.setTranslateY(200);
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
                    greenLineTimer = 0;
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
                    clockTime = clockTime - 100L;
                    double clockHeight = whiteClock.getHeight();
                    redClock.setHeight(clockHeight * clockTime / BIDDURATION);
                    greenLineTimer ++;
//                    System.out.println(greenLineTimer);
                    if (transactionOccurring()) {
//                        System.out.println("transaction should occur");
                        if (greenLineTimer % 20 == 0) {
                            greenLine.setFill(Color.RED);
                            System.out.println("transaction occurred at t = " + greenLineTimer);
                            makeTransaction();
                        }
                    }
                    if ((greenLineTimer - 2) % 20 == 0) {
                        greenLine.setFill(Color.GREEN);
                    }
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
                    greenLineTimer = 0;
                    canMove = false;
                    topBidLine.setTranslateY(TOPLIMIT + 50);
                    bottomBidLine.setTranslateY(BOTTOMLIMIT);
                    if (pane2.getChildren().contains(greenLine)) {
                        pane2.getChildren().removeAll(greenLine);
                    }
                    System.out.println(5 + i * 5 + "\n");
                    // Reset everything and cancel timer3
//                    timer4.cancel();
                    if (i >= 2) {
                        presenter.switchPresenter("map_grid_tile_select.fxml");
                    }
                });
            }
        }, TOTALDELAY * i + BIDDURATION + BUYORSELLDURATION + SHOWRESOURCEDURATION + 2000L);
    }

    public void startEnergyBidding() {

    }

    public void quantifyResources() {
//        Text player1quant = new Text(playerRepository.get(0).getFood());
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
            isBuying.add(new Boolean(true));
            buyersInTrans.add(new Boolean(false));
            sellersInTrans.add(new Boolean(false));
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
        // Occurs every timer a player is moved during the bidding round
        bottomBidLine.setTranslateY(highestBuyer());
        topBidLine.setTranslateY(lowestSeller());
        if(bottomBidLine.getTranslateY() - topBidLine.getTranslateY() < 10) {
            greenLine.setTranslateY(topBidLine.getTranslateY());
            transVal = 400 - greenLine.getTranslateY(); // Make sure the 400 - # matches up with the player bid values
            System.out.println(transVal);
            determineBuyersAndSellers();
            if (!pane2.getChildren().contains(greenLine)) {
                pane2.getChildren().addAll(greenLine);
            }
        } else if (bottomBidLine.getTranslateY() - topBidLine.getTranslateY() >= 10
                && pane2.getChildren().contains(greenLine)) {
            pane2.getChildren().removeAll(greenLine);
        }
    }

    private void determineBuyersAndSellers() {
        // If green line is present, compares players' bid value to transVal ("transaction value") that was determined
        // when the green line appeared, or when two players first met up to bid
        if (pane2.getChildren().contains(greenLine)) {
            for (int j = 0; j < playerRepository.size(); j++) {
                if (buySell.get(j).getText().equals("$" + transVal) && isBuying.get(j)) {
//                    System.out.println("Determined player " + (j + 1) + " is a buyer");
                    buyersInTrans.set(j, new Boolean(true));
                } else {
                    buyersInTrans.set(j, new Boolean(false));
                }
                System.out.println("Gets to determining sellers");
                System.out.println("text is right val: " + buySell.get(j).getText().equals("$" + transVal));
                System.out.println("isBuying: " + !isBuying.get(j));
                if (buySell.get(j).getText().equals("$" + transVal) && !isBuying.get(j)) {
//                    System.out.println("Determined player " + (j + 1) + " is a seller");
                    sellersInTrans.set(j, new Boolean(true));
                } else {
                    sellersInTrans.set(j, new Boolean(false));
                }
            }
        }
    }

    private double highestBuyer() {
        double topPos = BOTTOMLIMIT;
        for (int j = 0; j < playerRepository.size(); j++) {
            double playerPos = playerImageList.get(j).getTranslateY();
            if (isBuying.get(j) && playerPos < topPos) {
                topPos = playerPos;
            }
        }
        return topPos;
    }

    private double lowestSeller() {
        double bottomPos = TOPLIMIT + 50;
        for (int j = 0; j < playerRepository.size(); j++) {
            double playerPos = playerImageList.get(j).getTranslateY() + 50;
            if (!isBuying.get(j) && playerPos > bottomPos) {
                bottomPos = playerPos;
            }
        }
        return bottomPos;
    }

    private void moveBidderUp(int j) {
        double moveTo = playerImageList.get(j).getTranslateY() - 10;
        if (playerImageList.size() > j
                && moveTo > TOPLIMIT - 15
                && (moveTo > topBidLine.getTranslateY()
                || !isBuying.get(j))) {
            playerImageList.get(j).setTranslateY(moveTo);
        }
        double val = playerImageList.get(j).getTranslateY();
        val = isBuying.get(j)? val - 5 : val + 50;
        val = 400 - val; // Make sure the 400 - # matches up with transVal and moveBidderDown
        buySell.get(j).setText("$" + val);
        setLines();
    }

    private void moveBidderDown(int j) {
        double moveTo = playerImageList.get(j).getTranslateY() + 10;
        if (playerImageList.size() > j
                && moveTo < BOTTOMLIMIT + 15
                && (moveTo < bottomBidLine.getTranslateY() - 50
                || isBuying.get(j))) {
            playerImageList.get(j).setTranslateY(moveTo);
        }
        double val = playerImageList.get(j).getTranslateY();
        val = isBuying.get(j)? val - 5 : val + 50;
        val = 400 - val; // Make sure the 400 - # matches up with transVal and moveBidderUp
        buySell.get(j).setText("$" + val);
        setLines();
    }

    private boolean transactionOccurring() {
//        System.out.println(buyersInTrans + "\n");
//        System.out.println(sellersInTrans + "\n");
        return pane2.getChildren().contains(greenLine)
                && buyersInTrans.contains(true)
                && sellersInTrans.contains(true);
    }

    private void makeTransaction() {
        ArrayList<Integer> buyerInds = new ArrayList<Integer>();
        ArrayList<Integer> sellerInds = new ArrayList<Integer>();
        for (int j = 0; j < playerRepository.size(); j++) {
            if(isBuying.get(j)) {
                buyerInds.add(j);
            }
            if(!isBuying.get(j)) {
                sellerInds.add(j);
            }
        }
        //select random array val
        int buyer = buyerInds.get((int) Math.floor(Math.random() * buyerInds.size())); // returns random buyer
        int seller = sellerInds.get((int) Math.floor(Math.random() * sellerInds.size())); // returns random seller
        playerRepository.get(seller).offsetMoney((int) transVal);
        playerRepository.get(buyer).offsetMoney(- (int) transVal);
        if(resource.equals("Smithore")) {
            playerRepository.get(buyer).offsetSmithore(1);
            playerRepository.get(seller).offsetSmithore(-1);
        } else if(resource.equals("Food")) {
            playerRepository.get(buyer).offsetFood(1);
            playerRepository.get(seller).offsetFood(-1);
        } else if(resource.equals("Energy")) {
            playerRepository.get(buyer).offsetEnergy(1);
            playerRepository.get(seller).offsetEnergy(-1);
        }
    }
}