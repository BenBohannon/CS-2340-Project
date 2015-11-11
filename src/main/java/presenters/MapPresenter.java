package presenters;

import com.google.inject.Inject;
import data.MapInfoHolder;
import data.Repository;
import model.service.TurnEndListener;
import javafx.application.Platform;
import model.entity.Mule;
import model.entity.MuleType;
import model.entity.Player;
import model.map.Map;
import model.map.Tile;
import model.map.TileType;
import model.service.DefaultTurnService;
import view.MapView;


import java.awt.Point;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Ben 9/14/2015.
 */
public class MapPresenter extends Presenter<MapView>
        implements TurnEndListener {

    @Inject
    private Map map;
    @Inject
    private MapInfoHolder mapInfo;

    @Inject
    private Repository<Player> playerRepository;
    @Inject
    private DefaultTurnService turnService;

    private boolean isListening;
    private boolean isPlacingMule;
    private Mule mulePlacing;


    @Override
    public void initialize() {
        if (turnService.isTurnInProgress()) {
            turnService.addTurnEndListener(this);
            // Make sure this only happens ONCE
            isListening = true;
        } else {
            if (turnService.isAllTurnsOver()) {
                turnService.beginRound();

                //RANDOM EVENT CODE

                //Get random money to add/subtract from a player.
                Random rand = new Random();
                int deltaMoney = rand.nextInt(400) - 200;

                //Get a winning player.
                Player eventPlayer = null;
                List<Player> players = playerRepository.getAll();
                for (Player p : players) {
                    if (p.getRank() >= players.size() / 2) {
                        if (eventPlayer == null) {
                            eventPlayer = p;
                        } else if (rand.nextBoolean()) {
                            eventPlayer = p;
                        }
                    }
                }

                //If we failed to pick a player,
                // just start the turn without random events.
                if (eventPlayer == null) {
                    beginTurn();
                    return;
                }

                //Print the random event to the screen.
                if (deltaMoney < 0) {
                    view.showRandomEventText("Random event! "
                            + eventPlayer.getName() + " loses "
                            + deltaMoney + " money!");
                } else {
                    view.showRandomEventText("Random event! "
                            + eventPlayer.getName() + " gains "
                            + deltaMoney + " money!");
                }

                //Start the turn after the text has disappeared.
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(MapPresenter.this::beginTurn);
                    }
                }, 5010L);

            } else {
                beginTurn();
            }
        }
        if (isPlacingMule) {
            view.displayMule();
        }
    }

    /**
     * Loads the input.fxml file and gives up control to it.
     * @param str name of fxml file
     */
    private void switchPresenter(String str) {
        if (isListening) {
            turnService.removeTurnEndListener(this);
            isListening = false;
        }
//        turnService.stopTimers();
        context.showScreen(str);
    }

    /**
     * Turns control over to the TownPresenter, and stops character movement.
     */
    public void enterCity() {
        view.stopMovement();

        switchPresenter("town.fxml");
    }

    @Override
    public void onTurnEnd(Player player) {
        isListening = false;
        Platform.runLater(() -> {
            view.stopMovement();

            //Mule is lost if not placed//
            if (isPlacingMule) {
                player.getMules().remove(mulePlacing);
                view.stopDisplayingMule();
                isPlacingMule = false;
            }

            if (turnService.isAllTurnsOver()) {
                calcProduction();
                switchPresenter("auction.fxml");
            } else {
                beginTurn();
            }
        });
    }

    /**
     * Computes what should be done to the input tile,
     * based on model information.
     * @param tileCoord Coordinate of the tile to affect
     */
    public void onClick(Point tileCoord) {
        if (!isPlacingMule) {
            return;
        }

        /*
        //check if player owns tile//
        boolean owned = playerRepository.getAll().stream()
                .flatMap(p -> p.getOwnedProperties().stream())
                .anyMatch(t -> t.getLocation().getCol()
                == tileCoord.y && t.getLocation().getRow() == tileCoord.x);
        */
        Tile[] tile = map.getOccupants(tileCoord.x, tileCoord.y, Tile.class);
        boolean owned = (getCurrentPlayer() == tile[0].ownedBy());

        //check for another mule//
        boolean occupied = map.getOccupants(tileCoord.x,
                tileCoord.y, Mule.class).length > 0;

        System.out.println(tileCoord.x + ", " + tileCoord.y);
        System.out.println("owned: " + owned + " occupied: " + occupied);
        if (owned && !occupied) {
            map.add(mulePlacing, tileCoord.x, tileCoord.y);
            view.placeMuleGraphic(tileCoord.y,
                    tileCoord.x, mulePlacing.getType());
        } else {
            turnService.getCurrentPlayer().getMules().remove(mulePlacing);
            System.out.println("Mule Lost");
        }

        mulePlacing = null;
        isPlacingMule = false;
        view.stopDisplayingMule();
    }

    /**
     * check if turn is in progress
     * @return if in progress
     */
    public boolean isTurnInProgress() {
        return turnService.isTurnInProgress();
    }

    /**
     * gets map being used by game
     * @return map
     */
    public Map getMap() {
        return map;
    }

    /**
     * gets all players currently in game
     * @return all players
     */
    public Repository<Player> getPlayerRepository() {
        return playerRepository;
    }

    /**
     * if current player is placing mule
     * @return if placing mule
     */
    public boolean isPlacingMule() {
        return isPlacingMule;
    }

    /**
     * gets current player
     * @return current player
     */
    public Player getCurrentPlayer() {
        return turnService.getCurrentPlayer();
    }

    /**
     * gets time remaining in turn
     * @return time remaining
     */
    public double getTimeRemaining() {
        return turnService.getTimeRemaining();
    }

    /**
     * should be called by townPresenter.
     * @param isPlacingMule if placing mule
     * @param mule mule that is being placed
     */
    public void setIsPlacingMule(boolean isPlacingMule, Mule mule) {
        this.mulePlacing = mule;
        this.isPlacingMule = isPlacingMule;
    }

    /**
     * begins turn.
     */
    private void beginTurn() {
        turnService.beginTurn();
        turnService.addTurnEndListener(this);
        isListening = true;
        view.setCharacterImage(turnService.
                getCurrentPlayer().getRace().getImagePath());
        view.showTurnStartText();
    }

    /**
     * calcuates production of resources from tiles and mules.
     */
    private void calcProduction() {
        for (int i = 0; i < map.getRows(); i++) {
            for (int j = 0; j < map.getCols(); j++) {

                Mule[] mule = map.getOccupants(i, j, Mule.class);
                if (mule.length < 1) {
                    continue;
                }

                Tile[] tile = map.getOccupants(i, j, Tile.class);

                int amount = 1;

                if (mule[0].getType() == MuleType.Crysite) {
                    tile[0].ownedBy().offsetCrystite(amount);

                } else if (mule[0].getType() == MuleType.Energy) {

                    if (tile[0].getTileType() == TileType.PLAIN) {
                        amount = 3;
                    } else if (tile[0].getTileType() == TileType.RIVER) {
                        amount = 2;
                    }

                    tile[0].ownedBy().offsetEnergy(amount);

                } else if (mule[0].getType() == MuleType.Food) {
                    if (tile[0].getTileType() == TileType.RIVER) {
                        amount = 4;
                    } else if (tile[0].getTileType() == TileType.PLAIN) {
                        amount = 2;
                    }

                    tile[0].ownedBy().offsetFood(amount);

                } else if (mule[0].getType() == MuleType.Smithore) {
                    if (tile[0].getTileType() == TileType.MOUNTAIN_3) {
                        amount = 4;
                    } else if (tile[0].getTileType() == TileType.MOUNTAIN_2) {
                        amount = 3;
                    } else if (tile[0].getTileType() == TileType.MOUNTAIN_1) {
                        amount = 2;
                    } else if (tile[0].getTileType() == TileType.RIVER) {
                        amount = 0;
                    }

                    tile[0].ownedBy().offsetSmithore(amount);
                }

            }
        }
    }
}
