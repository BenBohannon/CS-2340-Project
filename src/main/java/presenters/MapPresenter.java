package presenters;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import data.MapInfoHolder;
import data.abstractsources.Repository;
import model.service.TurnEndListener;
import javafx.application.Platform;
import model.entity.Mule;
import model.entity.MuleType;
import model.entity.Player;
import model.map.Map;
import model.map.Tile;
import model.service.DefaultTurnService;
import view.MapView;

import java.awt.Point;
import java.util.*;


/**
 * Presenter that displays the screen that allows the player to move about
 * the map.
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

    @Inject
    @Named("MaxRandomEventMoney")
    private int maxRandomEventMoney;
    @Inject
    @Named("MinRandomEventMoney")
    private int minRandomEventMoney;
    @Inject
    @Named("TurnStartDelay")
    private int turnStartDelay;

    private boolean isListening;
    private boolean isPlacingMule;
    private Mule mulePlacing;


    @Override
    public void initialize() {
        if (turnService.isTurnInProgress()) {
            if (!turnService.isListening(this)) {
                turnService.addTurnEndListener(this); // Make sure this only happens ONCE
            }
        } else {
            boolean temp = turnService.isAllTurnsOver();
            if (temp) {
                turnService.beginRound();

                //RANDOM EVENT CODE

                //Get random money to add/subtract from a player.
                Random rand = new Random();
                int deltaMoney = rand.nextInt(getMaxRandomEventMoney() - getMinRandomEventMoney())
                                         + getMinRandomEventMoney();

                //Get a winning player.
                Player eventPlayer = getWinningPlayer();

                //If we failed to pick a player,
                // just start the turn without random events.
                if (eventPlayer == null) {
                    beginTurn();

                } else {
                    //Print the random event to the screen.

                    if (deltaMoney < 0) {
                        getView().showRandomEventText("Random event! "
                                                              + eventPlayer.getName() + " loses " + deltaMoney + " money!");
                    } else {
                        getView().showRandomEventText("Random event! "
                                                              + eventPlayer.getName() + " gains " + deltaMoney + " money!");
                    }

                    //Start the turn after the text has disappeared.
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Platform.runLater(new TimerTask() {
                                @Override
                                public void run() {
                                    beginTurn();
                                }
                            });
                        }
                    }, getTurnStartDelay());
                }
            } else {
                beginTurn();
            }
            if (isPlacingMule) {
                getView().displayMule();
            }
        }
    }

    private Player getWinningPlayer() {
        Random rand = new Random();
        Player eventPlayer = null;
        Set<Player> players = playerRepository.getAll();
        for (Player p : players) {
            if (p.getRank() >= players.size() / 2) {
                if (eventPlayer == null) {
                    eventPlayer = p;
                } else if (rand.nextBoolean()) {
                    eventPlayer = p;
                }
            }
        }
        return eventPlayer;
    }

    /**
     * Loads the input .fxml file and gives up control to it.
     * @param str the path of the fxml file relative to the presenters directory
     */
    private void switchPresenter(String str) {
        if (turnService.isTurnInProgress() && turnService.isListening(this)) {
            turnService.removeTurnEndListener(this);
        }
//        turnService.stopTimers();
        getContext().showScreen(str);
    }

    /**
     * Turns control over to the TownPresenter, and stops character movement.
     */
    public void enterCity() {
        getView().stopMovement();

        switchPresenter("town.fxml");
    }

    @Override
    public void onTurnEnd(Player player) {

        Platform.runLater(() -> {
            getView().stopMovement();

            //Mule is lost if not placed//
            if (isPlacingMule) {
                player.getMules().remove(mulePlacing);
                getView().stopDisplayingMule();
                isPlacingMule = false;
            }

            if (turnService.isAllTurnsOver()) {
                MapPresenter.this.calcProduction();
                MapPresenter.this.switchPresenter("auction.fxml");
            } else {
                MapPresenter.this.beginTurn();
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
        boolean owned = (getCurrentPlayer().equals(tile[0].ownedBy()));

        //check for another mule//
        boolean occupied = map.getOccupants(tileCoord.x,
                tileCoord.y, Mule.class).length > 0;

        if (owned && !occupied) {
            map.add(mulePlacing, tileCoord.x, tileCoord.y);
            getView().placeMuleGraphic(tileCoord.y,
                    tileCoord.x, mulePlacing.getType());
        } else {
            turnService.getCurrentPlayer().getMules().remove(mulePlacing);
        }

        mulePlacing = null;
        isPlacingMule = false;
        getView().stopDisplayingMule();
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
    public double getFractionRemaining() {
        return turnService.getFractionRemaining();
    }

    /**
     * should be called by townPresenter
     * @param pIsPlacingMule the boolean of whether this mule is being placed or not.
     */
    public void setIsPlacingMule(boolean pIsPlacingMule, Mule mule) {
        this.mulePlacing = mule;
        this.isPlacingMule = pIsPlacingMule;
    }

    /**
     * begins turn.
     */
    private void beginTurn() {
        turnService.beginTurn();
        turnService.addTurnEndListener(this);

        getView().setCharacterImage(turnService.getCurrentPlayer().getRace().getImagePath());
        getView().showTurnStartText();
    }

    /**
     * calcuates production of resources from tiles and mules.
     */
    private void calcProduction() {
        for (int i = 0; i < map.getRows(); i++) {
            for (int j = 0; j < map.getCols(); j++) {

                Mule[] mules = map.getOccupants(i, j, Mule.class);
                if (mules.length < 1) {
                    continue;
                }
                Mule mule = mules[0];

                Tile[] tiles = map.getOccupants(i, j, Tile.class);
                Tile tile = tiles[0];


                if (mule.getType() == MuleType.Crysite) {
                    // crystite production always 1 //
                    tile.ownedBy().offsetCrystite(1);

                } else if (mule.getType() == MuleType.Energy) {
                    tile.ownedBy().offsetEnergy(tile.getTileType().getEnergyPC());

                } else if (mule.getType() == MuleType.Food) {
                    tile.ownedBy().offsetFood(tile.getTileType().getFoodPC());

                } else if (mule.getType() == MuleType.Smithore) {
                    tile.ownedBy().offsetSmithore(tile.getTileType().getSmithorePC());
                }

            }
        }
    }

    public int getMaxRandomEventMoney() {
        return maxRandomEventMoney;
    }

    public void setMaxRandomEventMoney(int pMaxRandomEventMoney) {
        this.maxRandomEventMoney = pMaxRandomEventMoney;
    }

    public int getMinRandomEventMoney() {
        return minRandomEventMoney;
    }

    public void setMinRandomEventMoney(int pMinRandomEventMoney) {
        this.minRandomEventMoney = pMinRandomEventMoney;
    }

    public int getTurnStartDelay() {
        return turnStartDelay;
    }

    public void setTurnStartDelay(int pTurnStartDelay) {
        this.turnStartDelay = pTurnStartDelay;
    }
}
