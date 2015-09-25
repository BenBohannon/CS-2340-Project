package presenters;

import com.google.inject.Inject;
import data.MapInfoHolder;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import map.Map;
import map.Tile;

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

    @FXML
    private GridPane grid;
    @FXML
    private Pane pane;


    final private Group border = new Group();
    private Timer timer;
    private double mouseX;
    private double mouseY;


    /**
     * Constructor which sets up the default map.
     */
    @FXML
    public void initialize() {

        Rectangle top = new Rectangle(10, 100);
        Rectangle bottom = new Rectangle(10, 100);
        Rectangle right = new Rectangle(100, 10);
        Rectangle left = new Rectangle(100, 10);
        border.getChildren().addAll(top, bottom, right, left);
        border.setTranslateX(340);
        border.setTranslateY(235);

        pane.setOnMouseMoved(event -> {
            mouseX = event.getX();
            mouseY = event.getY();
        });

        pane.setOnMousePressed(event -> onClick());
        
        int mountainLimit = 6;
        int mountains = 0;

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

        //Start iterating through tiles to select
        iterateTiles();

    }

    private void iterateTiles() {
        if (timer == null) {
            long updateTime = 1000L;
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
        border.setTranslateX(border.getTranslateX());
        border.setTranslateY(border.getTranslateY());
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
}