/**
 * Created by brian on 9/10/15.
 */

import javafx.application.Application;
import javafx.stage.Stage;
import map.Map;
import presenters.PresenterContext;

public class Start extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        Map map = new Map();

        //This module doesn't need to bind anything yet//
        PresenterContext context = new PresenterContext((binder) -> {
            binder.bind(Map.class).toInstance(map);
        }, stage);

        context.showScreen("home_screen.fxml");
    }


}
