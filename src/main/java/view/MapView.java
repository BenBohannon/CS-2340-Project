package view;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
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
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;
import java.util.function.Function;


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
    private Rectangle timerRed;

    private List<ImageView> installedMuleImageViews;

    @Inject @Named("PlayerImageDimensions")
    private int playerImageDimensions;
    @Inject @Named("PlayerStartingX")
    private int playerStartingX;
    @Inject @Named("PlayerStartingY")
    private int playerStartingY;
    @Inject @Named("MapRows")
    private int rows;
    @Inject @Named("MapCols")
    private int cols;
    @Inject @Named("TimeRemainingBarWidth")
    private int timeRemainingBarWidth;
    @Inject @Named("TimeRemainingBarHeight")
    private int timeRemainingBarHeight;
    @Inject @Named("MuleDimensions")
    private int muleDimensions;
    @Inject @Named("PlayerImageAnimationSpeed")
    private int playerImageAnimationSpeed;
    @Inject @Named("AnimationFrameTime")
    private int animationFrameTime;
    @Inject @Named("MouseAnimationDelay")
    private int mouseAnimationDelay;
    @Inject @Named("TextInitialX")
    private int textInitialX;
    @Inject @Named("TextInitialY")
    private int textInitialY;
    @Inject @Named("MainFontSize")
    private int mainFontSize;
    @Inject @Named("MovementStartDelay")
    private int movementStartDelay;
    @Inject @Named("TurnStartDelay")
    private int turnStartDelay;
    @Inject @Named("OwnershipRectWidth")
    private int ownershipRectangleThickness;



    private static final int ANIMATION_CONST_1 = 20;
    private static final int ANIMATION_CONST_2 = 5;
    // can't do static injection with setter //
    @Inject @Named("TileDimensions")
    static int tileDimensions;




    /**
     * Constructor which sets up the default model.map.
     */
    @FXML
    public void initialize() {
        installedMuleImageViews = new LinkedList<>();

        character = createImageView("/races/Human.png", playerImageDimensions, playerImageDimensions);
        addImageToPane(character, playerStartingX, playerStartingY);


        if (getPresenter().isTurnInProgress()) {
            setCharacterImage(getPresenter().getCurrentPlayer().getRace().getImagePath());
        }

        pane.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseX = event.getX();
                mouseY = event.getY();
            }
        });

        pane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                MapView.this.getPresenter()
                        .onClick(getImageCoordinates(character));
            }
        });

        Map map = getPresenter().getMap();
        //Create a model.map.
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                Tile tile = map.getOccupants(i, j, Tile.class)[0];

                //Add tile images to the gridPane
                grid.add(new ImageView(tile.getImage(getTileDimensions(), getTileDimensions())), i, j);

                Mule[] mules = map.getOccupants(i, j, Mule.class);
                if (mules.length != 0) {
                    placeMuleGraphic(j, i, mules[0].getType());
                }
            }
        }

        for (Player player : getPresenter().getPlayerRepository().getAll()) {
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

        Rectangle timerWhite = new Rectangle(timeRemainingBarWidth, timeRemainingBarHeight, Color.WHITE);
        timerRed = new Rectangle(timeRemainingBarWidth, timeRemainingBarHeight, Color.RED);
        ColorAdjust monochrome = new ColorAdjust();
        monochrome.setSaturation(-1.0);
        pane.getChildren().addAll(timerWhite, timerRed);

        if (getPresenter().isTurnInProgress()) {
            startMovement();
        }
    }

    /**
     * displays mule in game.
     */
    public void displayMule() {
        if (mule == null) {
            mule = new ImageView(new Image(
                    "/mule/shrek_donkey.png", muleDimensions, muleDimensions, true, false));
        }
        addImageToPane(mule, (int) (character.getX() + playerImageDimensions / 2),
                (int) (character.getY() + playerImageDimensions / 2));
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
        moveCharacter(playerImageAnimationSpeed);
        updateTimer();

        //If the player is on the town tile, enter the town.
        Point temp = getImageCoordinates(character);
        if (temp.getX() == (getCols() / 2) && temp.getY() == (getRows() / 2)) { //&& !isLandSelectPhase) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    MapView.this.getPresenter().enterCity();
                }
            });
        }
    }

    /**
     * Updates the red timer on screen
     */
    private void updateTimer() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                double timerPerc = MapView.this.getPresenter().getFractionRemaining();
                timerRed.setWidth(timerPerc * timeRemainingBarWidth);
            }
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

            final double newDeltaX = deltaX / (ANIMATION_CONST_2 * mag) + deltaX / ANIMATION_CONST_1;
            final double newDeltaY = deltaY / (ANIMATION_CONST_2 * mag) + deltaY / ANIMATION_CONST_1;

            final double newPixelsPerSecond = pixelsPerSecond * 0.016;

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    character.setX(character.getX()
                            + newDeltaX * newPixelsPerSecond);
                    character.setY(character.getY()
                            + newDeltaY * newPixelsPerSecond);

                    //Move mule with character sprite//
                    if (mule != null) {
                        mule.setX(character.getX() + playerImageDimensions / 2);
                        mule.setY(character.getY() + playerImageDimensions / 2);
                    }
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

        long updateTime = animationFrameTime;

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                update();
            }
        }, mouseAnimationDelay, updateTime);
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
        text = new Text(textInitialX, textInitialY, getPresenter()
                .getCurrentPlayer().getName() + "'s Turn! Get Ready!");
        text.setFont(new Font(mainFontSize));
        pane.getChildren().add(text);
        text.toFront();
        character.setX(playerStartingX);
        character.setY(playerStartingY);

        Timer turnStartTextTimer = new Timer();
        turnStartTextTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        pane.getChildren().remove(text);
                        startMovement();
                    }
                });
            }
        }, movementStartDelay);
    }

    /**
     * shows the random event that happened to the user
     * @param eventText text to be shown to user
     */
    public void showRandomEventText(String eventText) {
        text = new Text(textInitialX, textInitialY, eventText);
        text.setFont(new Font(mainFontSize));
        pane.getChildren().add(text);
        text.toFront();
        character.setX(playerStartingX);
        character.setY(playerStartingY);

        Timer randomEventTextTimer = new Timer();
        randomEventTextTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        pane.getChildren().remove(text);
                        startMovement();
                    }
                });
            }
        }, turnStartDelay);
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
            throw new IllegalArgumentException("Input image path was null!");
        }

        character.setImage(new Image(imagePath, playerImageDimensions,
                playerImageDimensions, true, false));
    }

    /**
     * places the mule graphic on a tile
     * @param row row of tile
     * @param col col of tile
     * @param type tyle of mule
     */
    public void placeMuleGraphic(int row, int col, MuleType type) {
        Point p = getPixelOffset(row, col);
        ImageView img = createImageView(type.getImagePath(), tileDimensions, tileDimensions);
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
                + imageView.getImage().getWidth() / 2) / tileDimensions),
                (int) ((imageView.getY()
                        + imageView.getImage().getHeight() / 2) / tileDimensions));
    }

    /**
     * returns pixel coordinates of the top left corner
     * of the grid block designated by the grid coordinates
     * @param row row number
     * @param col col number
     * @return pixel coordinates
     */
    public static Point getPixelOffset(int row, int col) {
        return new Point(col * tileDimensions, row * tileDimensions);
    }

    /**
     * creates border of user tile
     * @param color color of border
     * @return border to be added
     */
    private Group createBorder(Color color) {
        Group border = new Group();
        javafx.scene.shape.Rectangle top =
                new javafx.scene.shape.Rectangle(ownershipRectangleThickness, tileDimensions);
        javafx.scene.shape.Rectangle bottom =
                new javafx.scene.shape.Rectangle(ownershipRectangleThickness, tileDimensions);
        javafx.scene.shape.Rectangle right =
                new javafx.scene.shape.Rectangle(tileDimensions, ownershipRectangleThickness);
        javafx.scene.shape.Rectangle left =
                new javafx.scene.shape.Rectangle(tileDimensions, ownershipRectangleThickness);
        bottom.setTranslateX(tileDimensions - ownershipRectangleThickness);
        right.setTranslateY(tileDimensions - ownershipRectangleThickness);
        border.getChildren().addAll(top, bottom, right, left);
        border.getChildren().stream()
                .map(new Function<Node, Shape>() {
                    @Override
                    public Shape apply(Node node) {
                        return ((Shape) node);
                    }
                })
                .forEach(new Consumer<Shape>() {
                    @Override
                    public void accept(Shape shape) {
                        shape.setFill(color);
                    }
                });
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

    public int getCols() {
        return cols;
    }

    public void setCols(int pCols) {
        this.cols = pCols;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int pRows) {
        this.rows = pRows;
    }

    public void setPlayerStartingY(int pPlayerStartingY) {
        this.playerStartingY = pPlayerStartingY;
    }

    public void setPlayerStartingX(int pPlayerStartingX) {
        this.playerStartingX = pPlayerStartingX;
    }

    public void setPlayerImageDimensions(int pPlayerImageDimensions) {
        this.playerImageDimensions = pPlayerImageDimensions;
    }

    public int getTileDimensions() {
        return tileDimensions;
    }

    public void setTileDimensions(int pTileDimensions) {
        this.tileDimensions = pTileDimensions;
    }

    public void setTimeRemainingBarWidth(int pTimeRemainingBarWidth) {
        this.timeRemainingBarWidth = pTimeRemainingBarWidth;
    }

    public void setTimeRemainingBarHeight(int pTimeRemainingBarHeight) {
        this.timeRemainingBarHeight = pTimeRemainingBarHeight;
    }

    public void setMuleDimensions(int pMuleDimensions) {
        this.muleDimensions = pMuleDimensions;
    }

    public int getPlayerImageAnimationSpeed() {
        return playerImageAnimationSpeed;
    }

    public void setPlayerImageAnimationSpeed(int pPlayerImageAnimationSpeed) {
        this.playerImageAnimationSpeed = pPlayerImageAnimationSpeed;
    }

    public int getAnimationFrameTime() {
        return animationFrameTime;
    }

    public void setAnimationFrameTime(int pAnimationFrameTime) {
        this.animationFrameTime = pAnimationFrameTime;
    }

    public void setMouseAnimationDelay(int pMouseAnimationDelay) {
        this.mouseAnimationDelay = pMouseAnimationDelay;
    }

    public void setTextInitialX(int pTextInitialX) {
        this.textInitialX = pTextInitialX;
    }

    public void setTextInitialY(int pTextInitialY) {
        this.textInitialY = pTextInitialY;
    }

    public void setMainFontSize(int pMainFontSize) {
        this.mainFontSize = pMainFontSize;
    }

    public void setMovementStartDelay(int pMovementStartDelay) {
        this.movementStartDelay = pMovementStartDelay;
    }

    public void setTurnStartDelay(int pTurnStartDelay) {
        this.turnStartDelay = pTurnStartDelay;
    }

    public void setOwnershipRectangleThickness(int pOwnershipRectangleThickness) {
        this.ownershipRectangleThickness = pOwnershipRectangleThickness;
    }
}
