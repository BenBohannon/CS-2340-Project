import com.google.inject.TypeLiteral;

import data.*;

import data.abstractsources.LocationDatasource;
import data.abstractsources.Repository;
import data.abstractsources.StoreDatasource;
import data.concretesources.MemoryPlayerRepository;
import data.concretesources.SqlTurnDatasource;
import model.entity.Player;
import model.entity.PlayerRace;
import model.map.Locatable;
import model.map.Map;
import model.service.DefaultTurnService;
import model.service.StoreService;
import org.h2.jdbcx.JdbcConnectionPool;
import presenters.PresenterContext;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Collection;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


/**
 * Created by brian on 10/5/15.
 */
public class StartFourPlayers extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) {

        //empty datasource for model.map//
        final LocationDatasource lds = new LocationDatasource() {
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

            @Override
            public void remove(Locatable locatable) {

            }
        };

        final StoreDatasource sds = new StoreDatasource() {

            private int foodAmount = 16;
            private int energyAmount = 16;
            private int smithoreAmount = 0;
            private int crystiteAmount = 0;


            private int foodStorePrice = -3000;
            private int energyStorePrice = -2500;
            private int smithoreStorePrice = -5000;
            private int crystiteStorePrice = -10000;

            private int muleCount = 10;
            @Override
            public void saveAmount(int energy, int food, int smithore, int crystite) {
                energyAmount = energy;
                foodAmount = food;
                smithoreAmount = smithore;
                crystiteAmount = crystite;
            }

            @Override
            public void savePrice(int energyPrice, int foodPrice,
                                  int smithorePrice, int crystitePrice) {
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

            @Override
            public int getMuleCount() {
                return muleCount;
            }

            @Override
            public void setMuleCount(int muleCount) {
                this.muleCount = muleCount;
            }
        };



        final MemoryPlayerRepository playerRepository = new MemoryPlayerRepository();
        Player p1 = new Player();
        p1.setName("P1");
        p1.setId(0);
        p1.setColor(Color.ALICEBLUE);
        p1.setRace(PlayerRace.Bonzoid);
        playerRepository.save(p1);

        Player p2 = new Player();
        p2.setName("P2");
        p2.setId(1);
        p2.setRace(PlayerRace.Buzzite);
        p2.setColor(Color.BLANCHEDALMOND);
        playerRepository.save(p2);

        final Map map = new Map(lds, 5, 9);

        final DefaultTurnService turnService = new DefaultTurnService(playerRepository,
                new StoreService(sds, playerRepository),
                new SqlTurnDatasource(null));

        final JdbcConnectionPool connectionPool = JdbcConnectionPool.create("jdbc:h2:~/.mule", "sa", "sa");

        PresenterContext context = new PresenterContext((binder) -> {
                binder.bind(LocationDatasource.class).toInstance(lds);
                binder.bind(new TypeLiteral<Repository<Player>>(){}).toInstance(playerRepository);
            binder.bind(JdbcConnectionPool.class).toInstance(connectionPool);

                //temp
                binder.bind(Map.class).toInstance(map);
                binder.bind(StoreDatasource.class).toInstance(sds);
                binder.bind(DefaultTurnService.class).toInstance(turnService);
        }, stage, 890, 490);

        context.showScreen("/presenters/map_grid_tile_select.fxml");
    }


}