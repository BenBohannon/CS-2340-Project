/**
 * Created by brian on 9/10/15.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Start extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/presenters/home_screen.fxml"));

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
