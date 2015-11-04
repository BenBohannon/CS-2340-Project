package data; /**
 * Created by brian on 10/26/15.
 */

import javafx.application.Application;
import javafx.stage.Stage;
import model.entity.MuleType;
import model.entity.PlayerRace;
import model.map.TileType;

public class GetOrdinals extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        for (PlayerRace race : PlayerRace.values()) {
            System.out.println(race.toString() + " " + race.ordinal());
        }


        for (TileType race : TileType.values()) {
            System.out.println(race.toString() + " " + race.ordinal());
        }


        for (MuleType race : MuleType.values()) {
            System.out.println(race.toString() + " " + race.ordinal());
        }
    }
}
