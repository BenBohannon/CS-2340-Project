package view;

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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.entity.Mule;
import model.entity.MuleType;
import model.entity.Player;
import model.map.Map;
import model.map.Tile;
import presenters.MapPresenter;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Map View takes in user input and interacts with its Map Presenter.
 * Created by Benjamin on 10/1/2015.
 */
public class MapView extends View<MapPresenter> {

    @FXML
    private GridPane grid;
    @FXML
    private Pane pane;

    private Text text;

    private ImageView character;
    private ImageView mule;
    private Timer timer;
    private double mouseX;
    private double mouseY;
    private Rectangle timerWhite;
    private Rectangle timerRed;

    private LinkedList<ImageView> installedMuleImageViews;

    /**
     * Constructor which sets up the default model.map.
     */
    @FXML
    public void initialize() {
        installedMuleImageViews = new LinkedList<>();

        character = createImageView("/races/Human.png", 25, 25);
        addImageToPane(character, 340, 235);

        if (presenter.isTurnInProgress()) {
            setCharacterImage(presenter.
                    getCurrentPlayer().getRace().getImagePath());
        }

        pane.setOnMouseMoved(event -> {
            mouseX = event.getX();
            mouseY = event.getY();
        });

        pane.setOnMousePressed(event ->
                presenter.onClick(getImageCoordinates(character)));

        Map map = presenter.getMap();
        //Create a model.map.
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 5; j++) {
                Tile tile = map.getOccupants(i, j, Tile.class)[0];

                //Add tile images to the gridPane
                grid.add(new ImageView(tile.getImage(100, 100)), i, j);

                Mule[] mules = map.getOccupants(i, j, Mule.class);
                if (mules.length != 0) {
                    placeMuleGraphic(j, i, mules[0].getType());
                }
            }
        }

        for (Player player : presenter.getPlayerRepository().getAll()) {
            for (Tile tile : player.getOwnedProperties()) {
                Group border = createBorder(player.getColor());
                pane.getChildren().add(border);
                Point location = getPixelOffset(
                        tile.getLocation().getCol(),
                        tile.getLocation().getRow());
                border.setLayoutX(location.getX());
                border.setLayoutY(location.getY());
            }
        }

        timerWhite = new Rectangle(200, 20, Color.WHITE);
        timerRed = new Rectangle(200, 20, Color.RED);
        ColorAdjust monochrome = new ColorAdjust();
        monochrome.setSaturation(-1.0);
        Blend blush = new Blend(BlendMode.MULTIPLY, monochrome,
                new ColorInput(0, 0, character.getImage().getWidth(),
                        character.getImage().getHeight(), Color.RED));

        pane.getChildren().addAll(timerWhite, timerRed);

        if (presenter.isTurnInProgress()) {
            startMovement();
        }
    }

    /**
     * displays mule in game.
     */
    public void displayMule() {
        if (mule == null) {
            mule = new ImageView(new Image(
                    "/mule/shrek_donkey.png", 40, 40, true, false));
        }
        addImageToPane(mule, (int) (character.getX() + 30),
                (int) (character.getY() + 30));
    }

    /**
     * stops displaying mule in game.
     */
    public void stopDisplayingMule() {
        if (mule != null) {
            pane.getChildren().remove(mule);
        }
        mule = null;
    }

    /**
     * Update Function called 60 times per second for player input.
     * Moves the player's character towards the mouse.
     */
    private void update() {
        moveCharacter(80);
        updateTimer();

        //If the player is on the town tile, enter the town.
        Point temp = getImageCoordinates(character);
        if (temp.getX() == 4 && temp.getY() == 2) { //&& !isLandSelectPhase) {
            Platform.runLater(presenter::enterCity);
        }
    }

    /**
     * Updates the red timer on screen
     */
    private void updateTimer() {
        Platform.runLater(() -> {
            double timerPerc = presenter.getTimeRemaining();
            timerRed.setWidth(timerPerc * 200);
        });
    }

    /**
     * Moves the character towards the cursor at a
     * speed of the input number of pixels per second.
     * @param pixelsPerSecond Speed at which the character moves.
     */
    private void moveCharacter(double pixelsPerSecond) {
        if (character != null) {
            double deltaX = mouseX - (character.getX()
                    + character.getImage().getWidth() / 2);
            double deltaY = mouseY - (character.getY()
                    + character.getImage().getHeight() / 2);

            //If our values aren't ready yet, or If we're
            // already close to the cursor, don't move the character.
            if (Math.abs(deltaX) < 1 && Math.abs(deltaY) < 1) {
                return;
            }

            double mag = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

            final double newDeltaX = deltaX / (5 * mag) + deltaX / 20;
            final double newDeltaY = deltaY / (5 * mag) + deltaY / 20;

            final double newPixelsPerSecond = pixelsPerSecond * 0.016;

            Platform.runLater(() -> {
                character.setX(character.getX()
                        + newDeltaX * newPixelsPerSecond);
                character.setY(character.getY()
                        + newDeltaY * newPixelsPerSecond);

                //Move mule with character sprite//
                if (mule != null) {
                    mule.setX(character.getX() + 30);
                    mule.setY(character.getY() + 30);
                }
            });
        }
    }

    /**
     * Starts the scheduled task of moving the current player towards the mouse.
     */
    public void startMovement() {
        if (timer == null) {
            timer = new Timer();
        }

        long updateTime = 16L;

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                update();
            }
        }, 300L, updateTime);
    }

    /**
     * Stops the scheduled task of moving the current player towards the mouse.
     */
    public void stopMovement() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /**
     * Starts the turn with an intermission text, then allows movement.
     */
    public void showTurnStartText() {
        text = new Text(250, 120, presenter.getCurrentPlayer().getName()
                + "'s Turn! Get Ready!");
        text.setFont(new Font(40));
        pane.getChildren().add(text);
        text.toFront();
        character.setX(340);
        character.setY(235);

        Timer turnStartTextTimer = new Timer();
        turnStartTextTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() ->
                {
                    pane.getChildren().remove(text);
                    startMovement();
                });
            }
        }, 2000L);
    }

    /**
     * shows the random event that happened to the user
     * @param eventText text to be shown to user
     */
    public void showRandomEventText(String eventText) {
        text = new Text(150, 120, eventText);
        text.setFont(new Font(40));
        pane.getChildren().add(text);
        text.toFront();
        character.setX(340);
        character.setY(235);

        Timer randomEventTextTimer = new Timer();
        randomEventTextTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() ->
                {
                    pane.getChildren().remove(text);
                    startMovement();
                });
            }
        }, 5000L);
    }

//    public void startTurnTurn() {
//        character = createImageView(presenter.getCurrentPlayer()
//                         .getRace().getImagePath(), 50, 50);
//        character.setX(340);
//        character.setY(235);
//    }

    /**
     * Sets the character's image on the map to be
     * the input image. (For switching races)
     * @param imagePath path of image to set
     */
    public void setCharacterImage(String imagePath) {
        if (imagePath == null) {
            throw new NullPointerException("Input image path was null!");
        }

        character.setImage(new Image(imagePath, 25, 25, true, false));
    }

    /**
     * places the mule graphic on a tile
     * @param row row of tile
     * @param col col of tile
     * @param type tyle of mule
     */
    public void placeMuleGraphic(int row, int col, MuleType type) {
        Point p = getPixelOffset(row, col);
        ImageView img = createImageView(type.getImagePath(), 100, 100);
        addImageToPane(img, (int) p.getX(), (int) p.getY());
        installedMuleImageViews.add(img);
    }

    /**
     * @param imageView imageview sprite
     * @return Point coordinates of the Tile which the
     *          imageview is currently over.
     */
    public static Point getImageCoordinates(ImageView imageView) {
        return new Point((int) ((imageView.getX()
                + imageView.getImage().getWidth() / 2) / 100),
                (int) ((imageView.getY()
                        + imageView.getImage().getHeight() / 2) / 100));
    }

    /**
     * returns pixel coordinates of the top left corner
     * of the grid block designated by the grid coordinates
     * @param row row number
     * @param col col number
     * @return pixel coordinates
     */
    public static Point getPixelOffset(int row, int col) {
        return new Point(col * 100, row * 100);
    }

    /**
     * creates border of user tile
     * @param color color of border
     * @return border to be added
     */
    private Group createBorder(Color color) {
        Group border = new Group();
        javafx.scene.shape.Rectangle top =
                new javafx.scene.shape.Rectangle(10, 100);
        javafx.scene.shape.Rectangle bottom =
                new javafx.scene.shape.Rectangle(10, 100);
        javafx.scene.shape.Rectangle right =
                new javafx.scene.shape.Rectangle(100, 10);
        javafx.scene.shape.Rectangle left =
                new javafx.scene.shape.Rectangle(100, 10);
        bottom.setTranslateX(90);
        right.setTranslateY(90);
        border.getChildren().addAll(top, bottom, right, left);
        border.getChildren().stream()
                .map(node -> ((javafx.scene.shape.Shape) node))
                .forEach(shape -> shape.setFill(color));
        return border;
    }

    /**
     * creates an Image
     * @param imagePath location of image
     * @param width width of image
     * @param height height of image
     * @return created image object
     */
    public static ImageView createImageView(String imagePath,
                                            int width, int height) {
        return new ImageView(new Image(imagePath, width, height, true, false));
    }

    /**
     * adds image to pan
     * @param view Image to be added
     * @param x x coordinate of where it should be added
     * @param y y coordinate of where it should be added
     */
    private void addImageToPane(ImageView view, int x, int y) {
        view.setX(x);
        view.setY(y);
        pane.getChildren().add(view);
    }
}
