package presenters;

import com.google.inject.Inject;
import data.MapInfoHolder;
import data.Repository;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import map.Map;
import map.Tile;
import model.Player;
import view.MapView;

import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Ben 9/14/2015
 */
public class MapPresenter extends Presenter<MapView> {

    @Inject
    public Map map;
    @Inject
    MapInfoHolder mapInfo;

    @FXML
    private GridPane grid;
    @FXML
    private Pane pane;


    private ImageView character;
    private Timer timer;
    private double mouseX;
    private double mouseY;

    private static boolean isLandSelectPhase = true;
    @Inject
    private Repository<Player> playerRepository;
    private int currentPlayer = 0;
    private List<Player> players;

    /**
     * Constructor which sets up the default map.
     */
    @FXML
    public void initialize() {

        character = new ImageView(new Image("/races/Character.png", 25, 25, true, false));
        character.setX(340);
        character.setY(235);
        ColorAdjust monochrome = new ColorAdjust();
        monochrome.setSaturation(-1.0);
        Blend blush = new Blend(BlendMode.MULTIPLY, monochrome,
                new ColorInput(0, 0, character.getImage().getWidth(), character.getImage().getHeight(), Color.RED));

        pane.setOnMouseMoved(event -> {
            mouseX = event.getX();
            mouseY = event.getY();
        });

//        pane.setOnMousePressed(event -> onClick());

        startMovement();

        int mountainLimit = 6;
        int mountains = 0;

        //Create a map.
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 5; j++) {
                Tile tile = map.getOccupants(i, j, Tile.class)[0];
                //Add tiles to the map.
                //map.add(tile, i, j);

                //Add tile images to the gridPane
                grid.add(new ImageView(tile.getImage(100, 100)), i, j);
            }
        }

        for (Player player : playerRepository.getAll()) {
            for (Tile tile : player.getOwnedProperties()) {
                Group border = createBorder(player.getColor());
                pane.getChildren().add(border);
                Point location = getPixelOffset(tile.getLocation().getCol(), tile.getLocation().getRow());
                border.setLayoutX(location.getX());
                border.setLayoutY(location.getY());
            }
        }

        pane.getChildren().add(character);
        Rectangle totTime = new Rectangle(200, 20);
        Rectangle remTime = new Rectangle(200, 20, Color.RED);
        pane.getChildren().addAll(totTime, remTime);

    }

    /**
     * Update Function called 60 times per second for player input.
     * Moves the player's character towards the mouse.
     */
    private void update() {
        moveCharacter(80);

        //If the player is on the town tile, enter the town.
        Point temp = getCharacterTile();
        if (temp.getX() == 4 && temp.getY() == 2) { //&& !isLandSelectPhase) {
            Platform.runLater(() -> enterCity());
        }
    }

    /**
     * Called every time the player clicks on the map screen.
     */
    private void onClick() {
//        if (isLandSelectPhase) {
//            //Check to see if this tile isn't already owned, give it to this player, and move to the next.
//            Point temp = getCharacterTile();
//            Tile tile = map.getOccupants(temp.x, temp.y, Tile.class)[0];
//
//            boolean isOwnedTile = false;
//            for (Player p : players) {
//                if (p.ownsProperty(tile)) {
//                    isOwnedTile = true;
//                    break;
//                }
//            }
//            //If the player is on an owned Tile, do nothing. If on the City, pass their turn.
//            if (isOwnedTile) {
//                return;
//            } else if (tile.getTileType() != TileType.TOWN) {
//                Player player = players.get(currentPlayer);
//
//                if (player.getOwnedProperties().size() < 3) {
//                    //Give the player this property.
//                    player.addProperty(tile);
//                    //TODO: Change the Tile's color to have the Player's color.
//                } else if (player.getMoney() >= 300) {
//                    player.addProperty(tile);
//                    player.addMoney(-300);
//                    //TODO: Change the Tile's color to have the Player's color.
//                } else {
//                    //Else, the player can't afford the property, so do nothing.
//                    return;
//                }
//            }
//
//            //Let the next player select his land, if anyone left.
//            currentPlayer++;
//            if (currentPlayer >= players.size()) {
//                isLandSelectPhase = false;
//            } else {
//                //Setup the player (who goes first) for his turn.
//            }
//            character.setX(340);
//            character.setY(235);
//
//            //Create the pause between turns.
//            stopMovement();
//            startMovement();
//        }
    }

    /**
     * Moves the character towards the cursor at a speed of the input number of pixels per second.
     * @param pixelsPerSecond Speed at which the character moves.
     */
    private void moveCharacter(double pixelsPerSecond) {
        if (character != null) {
            double deltaX = mouseX - (character.getX() + character.getImage().getWidth()/2);
            double deltaY = mouseY - (character.getY() + character.getImage().getHeight()/2);

            //If our values aren't ready yet, or If we're already close to the cursor, don't move the character.
            if (Math.abs(deltaX) < 1 && Math.abs(deltaY) < 1) {
                return;
            }

            double mag = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

            final double newDeltaX = deltaX / (5 * mag) + deltaX / 20;
            final double newDeltaY = deltaY / (5 * mag) + deltaY / 20;

            final double newPixelsPerSecond = pixelsPerSecond * 0.016;

            Platform.runLater(() -> {
                character.setX(character.getX() + newDeltaX * newPixelsPerSecond);
                character.setY(character.getY() + newDeltaY * newPixelsPerSecond);
            });
        }
    }

    /**
     * Starts the scheduled task of moving the current player towards the mouse.
     */
    private void startMovement() {
        if (timer == null) {
            long updateTime = 16L;
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
     * Stops the scheduled task of moving the current player towards the mouse.
     */
    private void stopMovement() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /**
     * Returns the tile which the character is currently over.
     */
    private Point getCharacterTile() {
        int x = (int)(character.getX() + character.getImage().getWidth()/2);
        int y = (int)(character.getY() + character.getImage().getWidth()/2);
        return new Point(((int) (character.getX() + character.getImage().getWidth()/2) )/100,
                ((int) (character.getY() + character.getImage().getHeight()/2))/100);
    }

    /**
     * returns pixel coordinates of the top left corner of the grid block designated by the grid coordinates
     */
    private Point getPixelOffset(int row, int col) {
        return new Point(col * 100, row * 100);
    }

    /**
     * Sets the character's image on the map to be the input image. (For switching races)
     * @param img Image to set.
     */
    public void setCharacterImage(Image img) {
        if (img == null) {
            throw new NullPointerException("Input image was null!");
        }

        character.setImage(img);
    }

    /**
     * Turns control over to the TownPresenter, and stops character movement.
     */
    public void enterCity() {
        stopMovement();

        context.showScreen("town.fxml");
    }

    /**
     * Prepares the Map to start a land selection phase the next time it's shown.
     */
    public static void readyLandSelectPhase() {
        isLandSelectPhase = true;
    }

    private Group createBorder(Color color) {
        Group border = new Group();
        javafx.scene.shape.Rectangle right = new javafx.scene.shape.Rectangle(10, 100);
        javafx.scene.shape.Rectangle left = new javafx.scene.shape.Rectangle(10, 100);
        javafx.scene.shape.Rectangle bottom = new javafx.scene.shape.Rectangle(100, 10);
        javafx.scene.shape.Rectangle top = new javafx.scene.shape.Rectangle(100, 10);
        right.setTranslateX(90);
        bottom.setTranslateY(90);
        border.getChildren().addAll(top, bottom, right, left);
        border.getChildren().stream()
                .map(node -> ((javafx.scene.shape.Shape) node))
                .forEach(shape -> shape.setFill(color));
        return border;
    }

    public boolean isRoundOver() {
        if (currentPlayer >= players.size()) {
            return true;
        }
        return false;
    }

}