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
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.entity.Player;
import presenters.AuctionPresenter;

import java.util.*;

/**
 * View that manages the UI for the Auction screen, where players can buy and sell items
 */
public class AuctionView extends View<AuctionPresenter> {

    //TODO: Make resources and money update immediately, don't allow money or resources to drop below 0
    //TODO: When resources or money is too low, player is pushed back, introduce a store
    @Inject
    private Repository<Player> playerRepository;
    @FXML
    private Pane pane;

    private Group pane2 = new Group();

    private ArrayList<ImageView> playerImageList;
    private ArrayList<Text> resourceLists = new ArrayList<Text>();
    private ArrayList<Text> names = new ArrayList<Text>();
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
    private ArrayList<Text> resourceTexts = new ArrayList<Text>();
    private ArrayList<Text> moneyTexts = new ArrayList<Text>();
    private ArrayList<Boolean> buyersInTrans = new ArrayList<Boolean>();
    private ArrayList<Boolean> sellersInTrans = new ArrayList<Boolean>();
    private volatile double clockTime;
    private volatile double greenLineTimer;
    private Line topBidLine = new Line(100, 0, RIGHTLIMIT, 0);
    private Line bottomBidLine = new Line(100, 0, RIGHTLIMIT, 0);
    private Rectangle greenLine = new Rectangle(650, 5, Color.GREEN);
    private double transVal = 0;

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

    private static final int X_OFFSET = 150;


    /**
     * initialises window.
     */
    @FXML
    public void initialize() {
        pane.getChildren().add(pane2);
        List<Player> playerList = new LinkedList<>(playerRepository.getAll());
        playerImageList = new ArrayList<>();

        for (int i = 0; i < playerRepository.size(); i++) {

            ImageView playerImage = MapView.createImageView(playerList.get(i).getRace().getImagePath(),
                    PLAYER_IMAGE_SIZE, PLAYER_IMAGE_SIZE);
            playerImageList.add(playerImage);

            double deltaX = playerImage.getImage().getWidth() / 2 - MARGIN;
            playerImage.setTranslateX(X_OFFSET + X_OFFSET * i - deltaX);
            double deltaY = playerImage.getImage().getHeight() / 2 - MARGIN;
            playerImage.setTranslateY(390 - deltaY);

            Text playerName = new Text("Player " + (i + 1) + "\n\""
                    + playerList.get(i).getName() + "\"");
            names.add(playerName);

            playerName.setTranslateX(X_OFFSET + X_OFFSET * i);
            playerName.setTranslateY(370);
            Text resources = new Text(playerList.get(i).getSmithore() + " Smithore\n"
                    + playerList.get(i).getEnergy() + " Energy\n"
                    + playerList.get(i).getFood() + " Food");
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
//                    System.out.println(1 + i * 5 + "\n");
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
                    if(i == 0) {
                        popUpMessage("QWER to sell | ZXCV to buy", 2000L);
                    }
//                    System.out.println(2 + i * 5 + "\n");
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
                    setLines();
                    pane2.getChildren().addAll(topBidLine, bottomBidLine, clock);
                    if(i == 0) {
                        popUpMessage("QWER to bid up | ZXCV to bid down", 2000L);
                    }
//                    System.out.println(3 + i * 5 + "\n");
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
//                            System.out.println("transaction occurred at t = " + greenLineTimer);
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
//                    System.out.println(5 + i * 5 + "\n");
                    // Reset everything and cancel timer3
//                    timer4.cancel();
                    if (i >= 2) {
                        getPresenter().switchPresenter("map_grid_tile_select.fxml");
                    }
                });
            }
        }, TOTALDELAY * i + BIDDURATION + BUYORSELLDURATION + SHOWRESOURCEDURATION + 2000L);
    }

    /**
     * start bidding of energy.
     */
    public void startEnergyBidding() {
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

    public void resetPane() {
        pane2.getChildren().clear();
        Text auctionText = new Text(250, 80, resource + " Auction");
        auctionText.setFont(new Font(40));
        auctionText.setFill(Color.BLUE);
        Text money = new Text(80, 450, "MONEY:");
        isBuying.clear();
        buySell.clear();
        buyersInTrans.clear();
        sellersInTrans.clear();
        updateResourcesAndMoney();
        for (int j = 0; j < playerRepository.size(); j++) {
            Text buyText = new Text("BUYING");
            buyText.setFill(Color.GREEN);
            buySell.add(buyText);
            isBuying.add(true);
            buyersInTrans.add(false);
            sellersInTrans.add(false);
            buyText.setTranslateX(150 + 150 * j);
            buyText.setTranslateY(450);
            resetCharacters();
            pane2.getChildren().addAll(playerImageList.get(j), buyText);
            // Reset position of bidLines
        }
        pane2.getChildren().addAll(money, auctionText);
    }

    public void setLines() {
        // Occurs every timer a player is moved during the bidding round, and before every failed transaction
        determineBuyersAndSellers();
        bottomBidLine.setTranslateY(highestBuyer());
        if (highestBuyer() > BOTTOMLIMIT - 10) {
            bottomBidLine.setTranslateY(BOTTOMLIMIT - 10);
        }
        topBidLine.setTranslateY(lowestSeller());
        if (lowestSeller() < TOPLIMIT + 60) {
            topBidLine.setTranslateY(TOPLIMIT + 60);
        }
        for (int j = 0; j < playerRepository.size(); j++) {
            System.out.println("Player " + j + " isBuying: " + isBuying.get(j));
        }
        System.out.println("Highest Buyer: " + highestBuyer());
        System.out.println("Lowest Seller: " + lowestSeller() + "\n");

        if(bottomBidLine.getTranslateY() - topBidLine.getTranslateY() < 10) {
            greenLine.setTranslateY(topBidLine.getTranslateY());
            transVal = 400 - greenLine.getTranslateY(); // Make sure the 400 - # matches up with the player bid values
//            System.out.println(transVal);
            if (!pane2.getChildren().contains(greenLine)) {
                pane2.getChildren().addAll(greenLine);
            }
        } else if (bottomBidLine.getTranslateY() - topBidLine.getTranslateY() >= 10
                && pane2.getChildren().contains(greenLine)) {
            pane2.getChildren().removeAll(greenLine);
        }
        determineBuyersAndSellers();
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
        double offset = isBuying.get(j)? 60 : 0;
        if (playerImageList.size() > j
                && moveTo > TOPLIMIT - 10 + offset
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
        double offset = !isBuying.get(j)? 60 : 0;
        if (playerImageList.size() > j
                && moveTo < BOTTOMLIMIT + 10 - offset
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
        ArrayList<Integer> buyerInds = new ArrayList<>();
        ArrayList<Integer> sellerInds = new ArrayList<>();
        setLines();
        List<Player> playerList = new ArrayList<>(playerRepository.getAll());
        for (int j = 0; j < playerRepository.size(); j++) {
//            System.out.println("Gets to determining sellers");
//            System.out.println("Player " + j + " has the right bid: " + buySell.get(j).getText().equals("$" + transVal));
//            System.out.println("Player " + j + " isBuying: " + isBuying.get(j));
            if(isBuying.get(j) && buySell.get(j).getText().equals("$" + transVal)) {
                if (playerList.get(j).getMoney() < transVal) {
                    playerImageList.get(j).setTranslateY(BOTTOMLIMIT - 10);
                    moveBidderDown(j);
                    popUpMessage("Player " + (j + 1) + " has insufficient funds", 1000);
                } else {
                    buyerInds.add(j);
                }
            }
            if(!isBuying.get(j) && buySell.get(j).getText().equals("$" + transVal)) {
                if ((resource.equals("Smithore") && playerList.get(j).getSmithore() <= 0)
                        || (resource.equals("Food") && playerList.get(j).getFood() <= 0)
                        || (resource.equals("Energy") && playerList.get(j).getEnergy() <= 0)) {
                    playerImageList.get(j).setTranslateY(TOPLIMIT + 10);
                    moveBidderUp(j);
                    popUpMessage("Player " + (j + 1) + " has no " + resource + " to sell", 1000);
                } else {
                    sellerInds.add(j);
                }
            }
        }
        //select random array val
        if (buyerInds.size() < 1 || sellerInds.size() < 1) {
//            System.out.println("seller or buyer inds is messed up");
            return;
        }
        int buyer = buyerInds.get((int) Math.floor(Math.random() * buyerInds.size())); // returns random buyer
        int seller = sellerInds.get((int) Math.floor(Math.random() * sellerInds.size())); // returns random seller
        playerList.get(seller).offsetMoney((int) transVal);
        playerList.get(buyer).offsetMoney(- (int) transVal);
        if(resource.equals("Smithore")) {
            playerList.get(buyer).offsetSmithore(1);
            playerList.get(seller).offsetSmithore(-1);
        } else if(resource.equals("Food")) {
            playerList.get(buyer).offsetFood(1);
            playerList.get(seller).offsetFood(-1);
        } else if(resource.equals("Energy")) {
            playerList.get(buyer).offsetEnergy(1);
            playerList.get(seller).offsetEnergy(-1);
        }
        playerRepository.save(playerList.get(buyer));
        playerRepository.save(playerList.get(seller));
//        System.out.println("Transaction Occured!!!");
        updateResourcesAndMoney();
    }

    public void updateResourcesAndMoney() {
        // First remove the money and resource Texts from the pane before you clear them from their arrayLists
        if (!pane2.getChildren().isEmpty()) {
            for (int j = 0; j < playerRepository.size(); j++) {
                if (pane2.getChildren().contains(moneyTexts.get(j))) {
                    pane2.getChildren().remove(moneyTexts.get(j));
                }
                if (pane2.getChildren().contains(resourceTexts.get(j))) {
                    pane2.getChildren().remove(resourceTexts.get(j));
                }
            }
        }
        resourceTexts.clear();
        moneyTexts.clear();
        List<Player> playerList = new ArrayList<>(playerRepository.getAll());
        for (int j = 0; j < playerRepository.size(); j++) {
            Text moneyText = new Text("$" + playerList.get(j).getMoney());
            moneyText.setTranslateX(150 + 150 * j);
            moneyText.setTranslateY(470);
            Text quantText;
            if (resource.equals("Smithore")) {
                quantText = new Text(playerList.get(j).getSmithore() + " " + resource);
            } else if(resource.equals("Food")) {
                quantText = new Text(playerList.get(j).getFood() + " " + resource);
            } else if(resource.equals("Energy")){
                quantText = new Text(playerList.get(j).getEnergy() + " " + resource);
            } else {
                quantText = new Text("dawg wtf check line 355 of AuctionView");
            }
            quantText.setTranslateX(150 + 150 * j);
            quantText.setTranslateY(490);
            resourceTexts.add(quantText);
            moneyTexts.add(moneyText);
            pane2.getChildren().addAll(moneyText, quantText);
        }
    }

    private void popUpMessage(String msg, long duration) {
        Text message = new Text(300, 300, msg);
        message.setFill(Color.RED);
        double strLen = msg.getBytes().length;
        Rectangle box = new Rectangle(280, 280, strLen * 6.5 + 40, 30);
        box.setFill(Color.BLACK);
        Group messageBox = new Group(box, message);
        pane2.getChildren().add(messageBox);
        Timer endTimer = new Timer();
        endTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() ->
                {
                    if(pane2.getChildren().contains(messageBox)) {
                        pane2.getChildren().remove(messageBox);
                    }
                });
            }
        }, duration);
    }
}