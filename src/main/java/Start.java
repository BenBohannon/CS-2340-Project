/**
 * Created by brian on 9/10/15.
 */

import com.google.inject.TypeLiteral;
import data.Repository;
import data.TurnInfoHolder;
import javafx.application.Application;
import javafx.stage.Stage;
import map.Locatable;
import map.LocationDatasource;
import model.Player;
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

        //empty datasource for map//
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

        //TurnInfoHolder from the test class.
        final TurnInfoHolder turnInfoHolder = new TurnInfoHolder() {
            model.Player player;

            @Override
            public Player getCurrentPlayer() {
                System.out.println("TurnInfoHolder#getCurrentPlayer()");
                if (player == null) {
                    player = new Player();
                }
                return player;
            }

            @Override
            public int getRoundNumber() {
                System.out.println("TurnInfoHolder#getRoundNumber()");
                return 1;
            }

            @Override
            public int getTimeLeftInTurn() {
                System.out.println("TurnInfoHolder#getTimeLeftInTurn");
                return 50;
            }
        };

        PresenterContext context = new PresenterContext((binder) -> {
            binder.bind(LocationDatasource.class).toInstance(lds);
            binder.bind(TurnInfoHolder.class).toInstance(turnInfoHolder);
        }, stage);

        context.showScreen("home_screen.fxml");
    }


}
