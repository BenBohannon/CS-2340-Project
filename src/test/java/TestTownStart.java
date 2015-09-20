/**
 * Created by brian on 9/17/15.
 */

import com.google.inject.TypeLiteral;
import data.Repository;
import data.TestPlayerRepository;
import data.TurnManager;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Player;
import presenters.PresenterContext;

public class TestTownStart extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        final TurnManager testTurnManager = new TurnManager() {
            Player player;

            @Override
            public Player getCurrentPlayer() {
                System.out.println("TurnManager#getCurrentPlayer()");
                if (player == null) {
                    player = new Player();
                }
                return player;
            }

            @Override
            public int getRoundNumber() {
                System.out.println("TurnManager#getRoundNumber()");
                return 1;
            }

            @Override
            public int getTimeLeftInTurn() {
                System.out.println("TurnManager#getTimeLeftInTurn");
                return 50;
            }
        };

        PresenterContext context = new PresenterContext((binder) -> {
            binder.bind(new TypeLiteral<Repository<Player>>(){}).to(TestPlayerRepository.class);
            binder.bind(TurnManager.class).toInstance(testTurnManager);
        }, stage);

        context.showScreen("town.fxml");
    }
}
