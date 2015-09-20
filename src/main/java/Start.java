/**
 * Created by brian on 9/10/15.
 */

import javafx.application.Application;
import javafx.stage.Stage;
import map.Locatable;
import map.LocationDatasource;
import presenters.PresenterContext;

import java.util.ArrayList;
import java.util.Collection;

public class Start extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        //empty datasource for map//
        LocationDatasource lds = new LocationDatasource() {
            @Override
            public Collection<Locatable> get(int row, int col) {
                return new ArrayList<>();
            }
        };

        PresenterContext context = new PresenterContext((binder) -> {
            binder.bind(LocationDatasource.class).toInstance(lds);
        }, stage);

        context.showScreen("home_screen.fxml");
    }


}
