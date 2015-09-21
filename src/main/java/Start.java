/**
 * Created by brian on 9/10/15.
 */

import service.TurnService;
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

        //TurnService from the test class.
        final TurnService turnService = new TurnService() {
            model.Player player;

            @Override
            public Player getCurrentPlayer() {
                System.out.println("TurnService#getCurrentPlayer()");
                if (player == null) {
                    player = new Player();
                }
                return player;
            }

            @Override
            public int getRoundNumber() {
                System.out.println("TurnService#getRoundNumber()");
                return 1;
            }

            @Override
            public int getTimeLeftInTurn() {
                System.out.println("TurnService#getTimeLeftInTurn");
                return 50;
            }
        };

        PresenterContext context = new PresenterContext((binder) -> {
            binder.bind(LocationDatasource.class).toInstance(lds);
            binder.bind(TurnService.class).toInstance(turnService);
        }, stage);

        context.showScreen("home_screen.fxml");
    }


}
