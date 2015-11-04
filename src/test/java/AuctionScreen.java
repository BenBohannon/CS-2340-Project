/**
 * Created by kylemurray on 10/26/15.
 */
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
import model.service.DefaultTurnService;
import presenters.PresenterContext;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Collection;

public class AuctionScreen extends Application {

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

        Player p3 = new Player();
        p3.setName("P3");
        p3.setId(2);
        p3.setColor(Color.AQUAMARINE);
        p3.setRace(PlayerRace.Ugaite);
        playerRepository.save(p3);

        Player p4 = new Player();
        p4.setName("P4");
        p4.setId(3);
        p4.setRace(PlayerRace.Human);
        p4.setColor(Color.BLACK);
        playerRepository.save(p4);


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

        context.showScreen("/presenters/auction.fxml");
    }


}