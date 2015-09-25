package presenters;

import com.google.inject.Inject;
import data.MapInfoHolder;
import data.Repository;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import map.Map;
import map.Tile;
import model.Player;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Ben 9/14/2015
 */
public class TileSelectionPresenter extends Presenter {

    @Inject
    public Map map;
    @Inject
    MapInfoHolder mapInfo;
    @Inject
    Repository<Player> playerRepository;

    @FXML
    private GridPane grid;
    @FXML
    private Pane pane;

    final private Group border = createBorder(0, 0, Color.WHITE);
    private Timer timer;
    private double mouseX;
    private double mouseY;
    private volatile int tileID;
    private boolean[] playerHasChosen = new boolean[4];

    private int selectionRound = 0;


    /**
     * Constructor which sets up the default map.
     */
    @FXML
    public void initialize() {

        pane.getChildren().add(border);
        pane.setOnMouseMoved(event -> {
            mouseX = event.getX();
            mouseY = event.getY();
        });

        pane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                Player player = null;
                Point location = getCoords(border.getTranslateX(), border.getTranslateY());
                Tile tile = (Tile) map.getOccupants(location.y, location.x)[0];
                boolean tileIsOwned = playerRepository.getAll().stream()
                        .anyMatch(p -> p.getOwnedProperties().contains(tile));
                if (!tileIsOwned) {
                    switch (event.getCode()) {
                        case A:
                            player = playerRepository.get(0);
                            if (!playerHasChosen[0]) { // && if tile is free && if player exists
                                player = playerRepository.get(0);
                                player.buyProperty(tile, selectionRound > 1 ? -300 : 0);
                                if (selectionRound > 1) {
                                    System.out.println("money -= 300 player " + player.getId());
                                }
                                Group border1 = createBorder(border.getTranslateX(), border.getTranslateY(), player.getColor());
                                pane.getChildren().add(border1);
                                playerHasChosen[0] = true;
                                // assign tile to player
                            }
                            break;
                        case S:
                            if (!playerHasChosen[1]) {
                                player = playerRepository.get(1);
                                player.buyProperty(tile, selectionRound > 1 ? -300 : 0);
                                if (selectionRound > 1) {
                                    System.out.println("money -= 300 player " + player.getId());
                                }
                                Group border2 = createBorder(border.getTranslateX(), border.getTranslateY(), player.getColor());
                                pane.getChildren().add(border2);
                                playerHasChosen[1] = true;
                            }
                            break;
                        case D:
                            if (!playerHasChosen[2]) {
                                player = playerRepository.get(2);
                                player.buyProperty(tile, selectionRound > 1 ? -300 : 0);
                                if (selectionRound > 1) {
                                    System.out.println("money -= 300 player " + player.getId());
                                }
                                Group border3 = createBorder(border.getTranslateX(), border.getTranslateY(), player.getColor());
                                pane.getChildren().add(border3);
                                playerHasChosen[2] = true;
                            }
                            break;
                        case F:
                            if (!playerHasChosen[3]) {
                                player = playerRepository.get(3);
                                player.buyProperty(tile, selectionRound > 1 ? -300 : 0);
                                if (selectionRound > 1) {
                                    System.out.println("money -= 300 player " + player.getId());
                                }
                                Group border4 = createBorder(border.getTranslateX(), border.getTranslateY(), player.getColor());
                                pane.getChildren().add(border4);
                                playerHasChosen[3] = true;
                            }
                            break;
                    }
                }
            }
        });

        pane.setOnMousePressed(event -> onClick());

        //Start iterating through tiles to select
        iterateTiles();

        //Create a map.
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 5; j++) {
                Tile tile = new Tile(mapInfo.getTileType(j, i));
                //Add tiles to the map.
                map.add(tile, i, j);

                //Add tile images to the gridPane
                grid.add(new ImageView(tile.getImage(100, 100)), i, j);
            }
        }
    }

    private void iterateTiles() {
        if (timer == null) {
            long updateTime = 200L;
            timer = new Timer(true);

            timer.schedule(new TimerTask() {
                               @Override
                               public void run() {
                                   update();
                               }
                           },
                    1000L, updateTime);
        }
    }


    /**
     * Called every time the player clicks on the map screen.
     */
    private void onClick() {

    }

    private void update() {
        // jump to the next grid tile
        Platform.runLater(() -> {
            if (doneSelecting()) {
                stopMovement();
                context.showScreen("map_grid.fxml");
            }
            tileID ++;
            if (tileID != 1 && tileID % 9 == 0) {
                border.setTranslateX(border.getTranslateX() - 900);
                border.setTranslateY(border.getTranslateY() + 100);
            }
            if (tileID % 45 == 0) {
                border.setTranslateY(border.getTranslateY() - 500);
                if (noneSelected()) {
                    stopMovement();
                    context.showScreen("map_grid.fxml");
                }
                for (int i = 0; i < playerRepository.getAll().size(); i++) {
                    playerHasChosen[i] = false;
                }
                selectionRound++;
            }
            border.setTranslateX(border.getTranslateX() + 100);
        });
    }

    /**
     * Stops the scheduled task of moving the current player towards the mouse.
     */
    private void stopMovement() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public Group createBorder(double x, double y, Color color) {
        Rectangle top = new Rectangle(10, 100);
        Rectangle bottom = new Rectangle(10, 100);
        Rectangle right = new Rectangle(100, 10);
        Rectangle left = new Rectangle(100, 10);
        top.setTranslateX(x);
        top.setTranslateY(y);
        bottom.setTranslateX(90 + x);
        bottom.setTranslateY(y);
        right.setTranslateX(x);
        right.setTranslateY(90 + y);
        left.setTranslateX(x);
        left.setTranslateY(y);
        Group tempBorder = new Group();
        tempBorder.getChildren().addAll(top, bottom, right, left);
        tempBorder.getChildren().stream()
                .map(node -> ((Shape) node))
                .forEach(shape -> shape.setFill(color));
        return tempBorder;
    }

    private boolean allSelected() {
        for (int i = 0; i < playerRepository.getAll().size(); i++) {
            if (!playerHasChosen[i]) { return false; }
        }
        return true;
    }

    private boolean noneSelected() {
        for (int i = 0; i < playerRepository.getAll().size(); i++) {
            if (playerHasChosen[i]) {
                return false;
            }
        }
        return true;
    }

    private Point getCoords(double x, double y) {
        return new Point((int)(y/100),(int) (x/100));
    }

}