package presenters;

import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import map.Map;
import map.Tile;
import map.TileType;

import java.awt.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Ben 9/14/2015
 */
public class MapPresenter extends Presenter {

    @Inject
    public Map map;

    @FXML
    private GridPane grid;
    @FXML
    private Pane pane;


    private ImageView character;
    private Timer timer;
    private double mouseX;
    private double mouseY;

    private int numPlayersLeft;

    /**
     * Constructor which sets up the default map.
     */
    @FXML
    public void initialize() {

        character = new ImageView(new Image("/races/Character.png", 25, 25, true, false));
        pane.getChildren().add(character);
        character.setX(340);
        character.setY(235);

        pane.setOnMouseMoved(event -> {
            mouseX = event.getX();
            mouseY = event.getY();
        });

        pane.setOnMousePressed(event -> onClick());

        startMovement();


        int mountainLimit = 6;
        int mountains = 0;

        //Create a map.
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 5; j++) {
                map.Tile temp;
                Random rand = new Random();

                if (i == 4) {
                    if (j == 2) {
                        //Make a town
                        temp = new Tile(TileType.TOWN);
                    } else {
                        //Make a river
                        temp = new Tile(TileType.RIVER);
                    }
                } else {
                    if (mountains < mountainLimit && rand.nextInt(6) == 0) {
                        temp = new Tile(TileType.MOUNTAIN);
                        mountains++;
                    } else {
                        temp = new Tile(TileType.PLAIN);
                    }
                }
                //Add tiles to the map.
                map.add(temp, i, j);
                ImageView imageView = new ImageView(temp.getImage(100, 100));

                //Add tile images to the gridPane
                grid.add(imageView, i, j);
            }
        }
    }

    /**
     * Update Function called 60 times per second for player input.
     * Moves the player's character towards the mouse.
     */
    private void update() {
        moveCharacter(80);

        //If the player is on the town tile, enter the town.
        Point temp = getCharacterTile();
        if (temp.getX() == 4 && temp.getY() == 2) {
            Platform.runLater(() -> enterCity());
        }
    }

    /**
     * Called every time the player clicks on the map screen.
     */
    private void onClick() {

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

            final double newDeltaX = deltaX / mag;
            final double newDeltaY = deltaY / mag;

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
        return new Point(((int) (character.getX() + character.getImage().getWidth()/2) )/100,
                ((int) (character.getY() + character.getImage().getHeight()/2))/100);
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
     * Places the character outside of the city, and restarts movement.
     */
    public void exitCity() {
        character.setX(340);
        character.setY(235);

        startMovement();
    }

}