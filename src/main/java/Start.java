/**
 * Created by brian on 9/10/15.
 */

import javafx.application.Application;
import javafx.stage.Stage;
import presenters.PresenterContext;

public class Start extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        //This module doesn't need to bind anything yet//
        PresenterContext context = new PresenterContext((binder) -> {
            return;
        }, stage);

        context.showScreen("home_screen.fxml");
    }


}
