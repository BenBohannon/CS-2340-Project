/**
 * Created by brian on 9/19/15.
 */

import javafx.application.Application;
import javafx.stage.Stage;
import model.map.Locatable;
import model.map.LocationDatasource;
import presenters.PresenterContext;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Collection;

public class TestMVP extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        //an empty data source for the model.map//
        LocationDatasource lds = new LocationDatasource() {
            @Override
            public Collection<Locatable> get(int row, int col) {
                return new ArrayList<>(0);
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

        //Bind LocationDatasource to our instance//
        //it will be insert wherever a class injects LocationDatasource//
        PresenterContext context = new PresenterContext((binder) -> {
            binder.bind(LocationDatasource.class).toInstance(lds);
        }, stage);

        context.showScreen("test_view.fxml");
    }

}
