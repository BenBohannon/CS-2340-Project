/**
 * Created by brian on 9/10/15.
 */

import com.google.inject.TypeLiteral;
import data.Repository;
import javafx.application.Application;
import javafx.stage.Stage;
import model.entity.Player;
import model.map.Locatable;
import model.map.LocationDatasource;
import data.MemoryPlayerRepository;
import model.map.Map;
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



        final MemoryPlayerRepository playerRepository = new MemoryPlayerRepository();
        final Map map = new Map(lds);

        PresenterContext context = new PresenterContext((binder) -> {
            binder.bind(LocationDatasource.class).toInstance(lds);
            binder.bind(new TypeLiteral<Repository<Player>>(){}).toInstance(playerRepository);

            //temp
            binder.bind(Map.class).toInstance(map);
        }, stage);

        context.showScreen("home_screen.fxml");
    }


}
