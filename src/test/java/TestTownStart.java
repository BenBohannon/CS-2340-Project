/**
 * Created by brian on 9/17/15.
 */

import com.google.inject.TypeLiteral;
import data.Repository;
import data.TestPlayerRepository;
import javafx.application.Application;
import javafx.stage.Stage;
import model.map.Locatable;
import model.map.LocationDatasource;
import model.Entity.Player;
import presenters.PresenterContext;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Collection;

public class TestTownStart extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

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
        };

        PresenterContext context = new PresenterContext((binder) -> {
            binder.bind(new TypeLiteral<Repository<Player>>(){}).to(TestPlayerRepository.class);
            binder.bind(LocationDatasource.class).toInstance(lds);
        }, stage);

        context.showScreen("town.fxml");
    }
}
