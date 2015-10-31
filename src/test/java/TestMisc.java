/**
 * Created by brian on 10/31/15.
 */

import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.entity.Player;


public class TestMisc extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        System.out.println(0x000000FF);
        System.out.println(String.format("%h", Color.GREEN.hashCode()));
        System.out.println(String.format("%h", new Player.ColorConverter().convertToDatabaseColumn(Color.GREEN)));
        System.out.println(String.format("%h", new Player.ColorConverter().convertToEntityAttribute(Color.GREEN.hashCode()).hashCode()));
    }
}
