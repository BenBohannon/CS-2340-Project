/**
 * Created by brian on 9/17/15.
 */

import com.google.inject.TypeLiteral;
import data.Repository;
import data.TestPlayerRepository;
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

public class TestTownStart extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        final TurnService testTurnService = new TurnService() {
            Player player;

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
            binder.bind(TurnService.class).toInstance(testTurnService);
            binder.bind(LocationDatasource.class).toInstance(lds);
        }, stage);

        context.showScreen("town.fxml");
    }
}
