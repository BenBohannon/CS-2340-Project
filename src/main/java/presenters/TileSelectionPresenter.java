package presenters;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import data.MapInfoHolder;
import data.abstractsources.Repository;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import model.entity.Mule;
import model.entity.MuleType;
import model.entity.Player;
import model.map.Map;
import model.map.Tile;
import view.MapView;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;


/**
 * Created by Ben 9/14/2015
 */
public class TileSelectionPresenter extends Presenter {

    @Inject
    private Map map;
    @Inject
    private MapInfoHolder mapInfo;
    @Inject
    private Repository<Player> playerRepository;

    @FXML
    private GridPane grid;
    @FXML
    private Pane pane;

    private final Group border = createBorder(0, 0, Color.WHITE);
    private Timer timer;
    // private double mouseX;
    // private double mouseY;
    private volatile int tileID;
    private boolean[] playerHasChosen;

    private int selectionRound = 0;


    @Inject @Named("MaxPlayer")
    private int maxPlayersAllowed;
    @Inject @Named("MapRows")
    private int rows;
    @Inject @Named("MapCols")
    private int cols;
    @Inject @Named("TileDimensions")
    private int tileDimensions;
    @Inject @Named("OwnershipRectWidth")
    private int ownershipRectThickness;
    @Inject @Named("TileIterationInterval")
    private int tileIterationInterval;
    @Inject @Named("TileIterationStartDelay")
    private int tileIterationStartDelay;
    @Inject @Named("MulePrice")
    private int mulePrice;





    /**
     * Constructor which sets up the default pMap.
     */
    @FXML
    public void initialize() {
        playerHasChosen = new boolean[maxPlayersAllowed];

        tileID = 0;

//        pane.setOnMouseMoved(event -> {
//            mouseX = event.getX();
//            mouseY = event.getY();
//        });

        for (Player player : getPlayerRepository().getAll()) {
            for (Tile tile : player.getOwnedProperties()) {
                Point location = getPixelOffset(tile.getLocation().getCol(), tile.getLocation().getRow());
                Group newBorder = createBorder(location.getX(), location.getY(), player.getColor());
                pane.getChildren().add(newBorder);
            }
        }

        pane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                handleKeyEvent(event);
            }
        });

        pane.getChildren().add(border);

        pane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                TileSelectionPresenter.this.onClick();
            }
        });

        //Create tiles and draw pMap or just draw pMap if tiles already in place //
        if (getMap().getOccupants(0, 0, Tile.class).length == 0) {
            for (int i = 0; i < cols; i++) {
                for (int j = 0; j < rows; j++) {
                    Tile tile = new Tile(getMapInfo().getTileType(j, i));
                    //Add tiles to the pMap.
                    getMap().add(tile, i, j);

                    //Add tile images to the gridPane
                    grid.add(new ImageView(tile.getImage(tileDimensions, tileDimensions)), i, j);

                    Mule[] mules = getMap().getOccupants(i, j, Mule.class);
                    if (mules.length != 0) {
                        placeMuleGraphic(i, j, mules[0].getType());
                    }
                }
            }
        } else {
            for (int i = 0; i < cols; i++) {
                for (int j = 0; j < rows; j++) {
                    Tile tile = getMap().getOccupants(i, j, Tile.class)[0];
                    grid.add(new ImageView(tile.getImage(tileDimensions, tileDimensions)), i, j);

                    Mule[] mules = getMap().getOccupants(i, j, Mule.class);
                    if (mules.length > 0) {
                        placeMuleGraphic(i, j, mules[0].getType());
                    }
                }
            }
        }

        //Start iterating through tiles to select
        iterateTiles();
    }

    private void iterateTiles() {
        if (timer == null) {
            timer = new Timer(true);

            timer.schedule(new TimerTask() {
                               @Override
                               public void run() {
                                   update();
                               }
                           },
                    tileIterationStartDelay, tileIterationInterval);
        }
    }

    private void handleKeyEvent(KeyEvent event) {
        Point location = TileSelectionPresenter.this.getCoords(border.getTranslateX(), border.getTranslateY());
        getMap().refreshFromDatasource();
        Tile tile = (Tile) getMap().getOccupants(location.y, location.x)[0];

        List<Player> players = new ArrayList<>(getPlayerRepository().getAll().size());
        players.addAll(getPlayerRepository().getAll());

        boolean tileIsOwned = players.stream()
                .anyMatch(new Predicate<Player>() {
                    @Override
                    public boolean test(Player p) {
                        return p.getOwnedProperties().contains(tile);
                    }
                });
        if (!tileIsOwned && tileID != (rows * cols) / 2) {
            switch (event.getCode()) {
                case A:
                    selectTile(0, players, tile);
                    break;
                case S:
                    selectTile(1, players, tile);
                    break;
                case D:
                    selectTile(2, players, tile);
                    break;
                case F:
                    selectTile(players.size() - 1, players, tile);
                    break;
            }
        }
    }

    private void selectTile(int playerIndex, List<Player> players, Tile tile) {
        Player player = getPlayerRepository().get(players.get(playerIndex).getId());
        if (!playerHasChosen[playerIndex]) { // && if tile is free && if player exists
            player.buyProperty(tile, selectionRound > 1 ? mulePrice : 0);
            getPlayerRepository().save(player);
            Group border1 = TileSelectionPresenter.this.createBorder(border.getTranslateX(), border.getTranslateY(), player.getColor());
            pane.getChildren().add(border1);
            playerHasChosen[playerIndex] = true;
            // assign tile to player
        }
    }


    /**
     * Called every time the player clicks on the pMap screen.
     */
    private void onClick() {

    }

    private void update() {
        // jump to the next grid tile
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                tileID++;
                if (tileID != 1 && tileID % cols == 0) {
                    border.setTranslateX(border.getTranslateX() - (cols * tileDimensions));
                    border.setTranslateY(border.getTranslateY() + tileDimensions);
                }
                if (tileID % (rows * cols) == 0) {
                    border.setTranslateY(border.getTranslateY() - (rows * tileDimensions));
                    TileSelectionPresenter.this.stopMovement();
                    TileSelectionPresenter.this.getContext().showScreen("map_grid.fxml");
                }
                border.setTranslateX(border.getTranslateX() + tileDimensions);
                if (TileSelectionPresenter.this.doneSelecting()) {
                    TileSelectionPresenter.this.stopMovement();
                    TileSelectionPresenter.this.getContext().showScreen("map_grid.fxml");
                }
            }
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
        Rectangle top = new Rectangle(ownershipRectThickness, tileDimensions);
        Rectangle bottom = new Rectangle(ownershipRectThickness, tileDimensions);
        Rectangle right = new Rectangle(tileDimensions, ownershipRectThickness);
        Rectangle left = new Rectangle(tileDimensions, ownershipRectThickness);
        top.setTranslateX(x);
        top.setTranslateY(y);
        bottom.setTranslateX(tileDimensions - ownershipRectThickness + x);
        bottom.setTranslateY(y);
        right.setTranslateX(x);
        right.setTranslateY(tileDimensions - ownershipRectThickness + y);
        left.setTranslateX(x);
        left.setTranslateY(y);
        Group tempBorder = new Group();
        tempBorder.getChildren().addAll(top, bottom, right, left);
        tempBorder.getChildren().stream()
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
        return tempBorder;
    }

    private boolean doneSelecting() {
        for (int i = 0; i < getPlayerRepository().getAll().size(); i++) {
            if (!playerHasChosen[i]) { return false; }
        }
        return true;
    }

//    private boolean noneSelected() {
//        for (int i = 0; i < pPlayerRepository.getAll().size(); i++) {
//            if (playerHasChosen[i]) {
//                return false;
//            }
//        }
//        return true;
//    }

    private Point getCoords(double x, double y) {
        return new Point((int)(y/tileDimensions),(int) (x/tileDimensions));
    }

    private Point getPixelOffset(int row, int col) {
        return new Point(col * tileDimensions, row * tileDimensions);
    }

    public void placeMuleGraphic(int col, int row, MuleType type) {
        Point p = getPixelOffset(row, col);
        ImageView img = MapView.createImageView(type.getImagePath(), tileDimensions, tileDimensions);
        addImageToPane(img, (int) p.getX(), (int) p.getY());
    }
    
    private void addImageToPane(ImageView view, int x, int y) {
        view.setX(x);
        view.setY(y);
        pane.getChildren().add(view);
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map pMap) {
        this.map = pMap;
    }

    public MapInfoHolder getMapInfo() {
        return mapInfo;
    }

    public void setMapInfo(MapInfoHolder pMapInfo) {
        this.mapInfo = pMapInfo;
    }

    public Repository<Player> getPlayerRepository() {
        return playerRepository;
    }

    public void setPlayerRepository(Repository<Player> pPlayerRepository) {
        this.playerRepository = pPlayerRepository;
    }

    public void setMaxPlayersAllowed(int pMaxPlayersAllowed) {
        this.maxPlayersAllowed = pMaxPlayersAllowed;
    }

    public void setRows(int pRows) {
        this.rows = pRows;
    }

    public void setCols(int pCols) {
        this.cols = pCols;
    }

    public void setTileDimensions(int pTileDimensions) {
        this.tileDimensions = pTileDimensions;
    }

    public void setTileIterationInterval(int pTileIterationInterval) {
        this.tileIterationInterval = pTileIterationInterval;
    }

    public void setTileIterationStartDelay(int pTileIterationStartDelay) {
        this.tileIterationStartDelay = pTileIterationStartDelay;
    }

    public void setMulePrice(int pMulePrice) {
        this.mulePrice = pMulePrice;
    }

    public void setOwnershipRectThickness(int pOwnershipRectThickness) {
        this.ownershipRectThickness = pOwnershipRectThickness;
    }
}