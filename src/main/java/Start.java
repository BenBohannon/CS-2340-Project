/**
 * Created by brian on 9/10/15.
 */

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

public class Start extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        final Injector injector = Guice.createInjector();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/home_screen.fxml"));
        loader.setControllerFactory(injector::getInstance);
        Parent root = null;
        try {
            root = loader.<Parent>load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene titleScene = new Scene(root, 800, 800);

        stage.setTitle("MULE");
        stage.setScene(titleScene);

        stage.show();

        //user pressed enter//
    }


}
