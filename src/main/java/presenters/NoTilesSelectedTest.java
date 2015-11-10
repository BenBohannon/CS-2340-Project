package presenters;

import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import data.MemoryPlayerRepository;
import data.Repository;
import data.StoreInfoHolder;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.entity.Player;
import model.entity.PlayerRace;
import model.entity.StoreDatasource;
import model.map.Locatable;
import model.map.LocationDatasource;
import model.map.Map;
import model.map.Tile;
import model.service.DefaultTurnService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Collection;

//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;


/**
 * Created by kylemurray on 11/4/15.
 */
public class NoTilesSelectedTest extends Application {


    private Player p1;
    private Player p2;
//    private MemoryPlayerRepository playerRepository;

    @Inject
    public Map map;
    @Inject
    private TileSelectionPresenter presenter = new TileSelectionPresenter();
    @Inject
    private Repository<Player> playerRepository = new MemoryPlayerRepository();

    @Rule
    public Timeout timeout = new Timeout(200);

//    @Before
//    public void setUp() {
//        presenter = new TileSelectionPresenter();
//    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        //empty datasource for model.map//
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

//        playerRepository = new MemoryPlayerRepository();
        p1 = new Player();
        p1.setName("P1");
        p1.setId(0);
        p1.setColor(Color.ALICEBLUE);
        p1.setRace(PlayerRace.Bonzoid);
        playerRepository.save(p1);

        p2 = new Player();
        p2.setName("P2");
        p2.setId(1);
        p2.setRace(PlayerRace.Buzzite);
        p2.setColor(Color.BLANCHEDALMOND);
        playerRepository.save(p2);


        final Map map = new Map(lds);

        final DefaultTurnService turnService = new DefaultTurnService(playerRepository, new StoreInfoHolder());

        PresenterContext context = new PresenterContext((binder) -> {
            binder.bind(LocationDatasource.class).toInstance(lds);
            binder.bind(new TypeLiteral<Repository<Player>>(){}).toInstance(playerRepository);

            //temp
            binder.bind(Map.class).toInstance(map);
            binder.bind(StoreDatasource.class).toInstance(sds);
            binder.bind(DefaultTurnService.class).toInstance(turnService);
        }, stage);

        context.showScreen("/presenters/map_grid_tile_select.fxml");
    }

    @Test
    public void checkNoneSelected() {

        assert(presenter.noneSelected());

        Tile tile = (Tile) map.getOccupants(1, 1)[0];
        int selectionRound = 0;

        p1.buyProperty(tile, selectionRound > 1 ? -300 : 0);

        assert(!presenter.noneSelected());
    }
}
