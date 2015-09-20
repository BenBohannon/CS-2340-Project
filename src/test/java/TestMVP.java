/**
 * Created by brian on 9/19/15.
 */

import javafx.application.Application;
import javafx.stage.Stage;
import map.Locatable;
import map.LocationDatasource;
import presenters.PresenterContext;

import java.util.ArrayList;
import java.util.Collection;

public class TestMVP extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        //an empty data source for the map//
        LocationDatasource lds = new LocationDatasource() {
            @Override
            public Collection<Locatable> get(int row, int col) {
                return new ArrayList<>(0);
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
