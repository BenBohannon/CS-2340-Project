/**
 * Created by brian on 9/10/15.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import presenters.PresenterContext;

import java.io.IOException;

public class Start extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        PresenterContext context = new PresenterContext((binder) -> {
            return;
        }, stage);
        context.showScreen("home_screen.fxml");
    }


}
