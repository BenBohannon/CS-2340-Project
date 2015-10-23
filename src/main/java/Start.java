/**
 * Created by brian on 9/10/15.
 */

import com.google.inject.TypeLiteral;
import data.MemoryPlayerRepository;
import data.Repository;
import javafx.application.Application;
import javafx.stage.Stage;
import model.entity.Player;
import data.StoreDatasource;
import model.map.Locatable;
import data.LocationDatasource;
import model.map.Map;
import model.service.DefaultTurnService;
import model.service.StoreService;
import presenters.PresenterContext;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Collection;

public class Start extends Application {

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
            private int muleCount;

            @Override
            public void saveAmount(int energy, int food, int smithore, int crystite) {
                energyAmount = energy;
                foodAmount = food;
                smithoreAmount = smithore;
                crystiteAmount = crystite;
                muleCount = 10;
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

        final Map map = new Map(lds);

        final DefaultTurnService turnService = new DefaultTurnService(playerRepository, new StoreService(sds));

        PresenterContext context = new PresenterContext((binder) -> {
            binder.bind(LocationDatasource.class).toInstance(lds);
            binder.bind(new TypeLiteral<Repository<Player>>(){}).toInstance(playerRepository);
            binder.bind(StoreDatasource.class).toInstance(sds);

            //temp
            binder.bind(Map.class).toInstance(map);
            binder.bind(DefaultTurnService.class).toInstance(turnService);
        }, stage);

        context.showScreen("home_screen.fxml");
    }


}
