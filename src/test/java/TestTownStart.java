/**
 * Created by brian on 9/17/15.
 */

import com.google.inject.TypeLiteral;
import data.Repository;
import data.TestPlayerRepository;
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

        PresenterContext context = new PresenterContext((binder) -> {
            binder.bind(new TypeLiteral<Repository<Player>>(){}).to(TestPlayerRepository.class);
        }, stage);

        context.showScreen("town.fxml");
    }
}
