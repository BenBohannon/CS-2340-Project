package presenters;

import data.MemoryPlayerRepository;
import data.StoreInfoHolder;
import model.entity.StoreDatasource;
import model.map.Locatable;
import model.map.LocationDatasource;
import model.map.Map;
import model.service.DefaultTurnService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;


/**
 * Created by kylemurray on 11/4/15.
 */
public class AddPlayerTest {



//    private MemoryPlayerRepository playerRepository;

    /*
        The injector will magically put the dependencies in the Objects it generates,
        but this -- the NoTilesSelectedTest Object -- is going to be instantiated by
        the JUnit framework when you run the tests with JUnit. You have created a
        start() method below, for a JavaFX application, but you aren't running this
        as a JavaFX application; you are running in JUnit. JUnit does not know to call the
        start() method, as JavaFX does.

        This is okay though. You don't need to have the JavaFX UI framework running to call the
        Presenter's methods. The presenter is just a regular old Object. We allow JavaFX
        to know which methods should be called when particular UI events happen by
        by annotating methods with @FXML. However, the Presenter is still just an Object, so we can
        call these methods too.

        presenter.onHandleSomeButtonClick(new ActionEvent()) // totally valid.

        The catch to this is that the TileSelectionPresenter also uses JavaFX UI Objects
        such as the "GridPane grid". You may be able to get away with just instantiating one
        and assigning it to the Presenter (presenter.grid = new GridPane();), but the
        UI objects that JavaFX would normally be creating were configured by the tile_selection
        fxml file, so they might not work. They might work though.

        For instatiating just one Presenter, you don't need dependency injection; you
        can do it yourself; you just have to do all of the stuff that javafx would normally do.

        first create the Presenter:

        "private TileSelectionPresenter presenter = new TileSelectionPresenter();"

        then assign its dependencies

        presenter.playerRepository = playerRepository;
        presenter.grid = new GridPane()...
        ...

        then call initialize, as JavaFX  would

        presenter.initialize()

        then call all of the UI stuff that JavaFX normally would

        presenter.handle(new KeyEvent([pretty much null everything except for key code]);

        see the bottom of the start method.
     */

//    @Inject
//    public Map map;
//    @Inject
//    private TileSelectionPresenter presenter = new TileSelectionPresenter();
//    @Inject
//    private Repository<Player> playerRepository = new MemoryPlayerRepository();

//    DefaultTurnService turnService;

    @Rule
    public Timeout timeout = new Timeout(200);

    @Test
    public void checkAddPlayer() {

        LocationDatasource lds = new LocationDatasource() {
            @Override
            public Collection<Locatable> get(int row, int col) {
                return new ArrayList<>();
            }

            @Override
            public void save(int row, int col, Locatable locatable) {
                throw new NotImplementedException();
            }

            @Override
            public void saveAll(int row, int col, Collection<Locatable> locatables) {
                throw new NotImplementedException();
            }
        };

        final StoreDatasource sds = new StoreDatasource() {

            private int energyAmount = energy;
            private int foodAmount = food;
            private int smithoreAmount = smithore;
            private int crystiteAmount = crystite;

            private int energyStorePrice = energyPrice;
            private int foodStorePrice = foodPrice;
            private int smithoreStorePrice= smithorePrice;
            private int crystiteStorePrice = crystitePrice;

            @Override
            public void saveAmount(int energy, int food, int smithore, int crystite) {
                energyAmount = energy;
                foodAmount = food;
                smithoreAmount = smithore;
                crystiteAmount = crystite;
            }

            @Override
            public void savePrice(int energyPrice, int foodPrice, int smithorePrice, int crystitePrice) {
                energyStorePrice = energyPrice;
                foodStorePrice = foodPrice;
                smithoreStorePrice = smithorePrice;
                crystiteStorePrice = crystitePrice;
            }

            @Override
            public int getEnergy() {
                return energyAmount;
            }

            @Override
            public int getFood() {
                return foodAmount;
            }

            @Override
            public int getSmithore() {
                return smithoreAmount;
            }

            @Override
            public int getCrystite() {
                return crystiteAmount;
            }

            @Override
            public int getEnergyPrice() {
                return energyStorePrice;
            }

            @Override
            public int getFoodPrice() {
                return foodStorePrice;
            }

            @Override
            public int getSmithorePrice() {
                return smithoreStorePrice;
            }

            @Override
            public int getCrystitePrice() {
                return crystiteStorePrice;
            }
        };



        final MemoryPlayerRepository playerRepository = new MemoryPlayerRepository();
//        Player p1 = new Player();
//        p1.setName("P1");
//        p1.setId(0);
//        p1.setColor(Color.ALICEBLUE);
//        p1.setRace(PlayerRace.Bonzoid);
//        playerRepository.save(p1);
//
//        Player p2 = new Player();
//        p2.setName("P2");
//        p2.setId(1);
//        p2.setRace(PlayerRace.Buzzite);
//        p2.setColor(Color.BLANCHEDALMOND);
//        playerRepository.save(p2);

//        Player p3 = new Player();
//        p3.setName("P3");
//        p3.setId(2);
//        p3.setColor(Color.AQUAMARINE);
//        p3.setRace(PlayerRace.Ugaite);
//        playerRepository.save(p3);
//
//        Player p4 = new Player();
//        p4.setName("P4");
//        p4.setId(3);
//        p4.setRace(PlayerRace.Human);
//        p4.setColor(Color.BLACK);
//        playerRepository.save(p4);


        final Map map = new Map(lds);

        final DefaultTurnService turnService = new DefaultTurnService(playerRepository, new StoreInfoHolder());

        assertEquals(turnService.getAllPlayers().size(), 0);

        turnService.addPlayer();
        assertEquals(turnService.getAllPlayers().size(), 1);

        turnService.addPlayer();
        assertEquals(turnService.getAllPlayers().size(), 2);

        turnService.addPlayer();
        assertEquals(turnService.getAllPlayers().size(), 3);

        turnService.addPlayer();
        assertEquals(turnService.getAllPlayers().size(), 4);

        turnService.addPlayer();
        assertEquals(turnService.getAllPlayers().size(), 4);

    }
}
