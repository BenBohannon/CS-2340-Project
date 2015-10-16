import com.google.inject.TypeLiteral;
import data.MemoryPlayerRepository;
import data.Repository;
import data.StoreInfoHolder;
import data.TestPlayerRepository;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.entity.Player;
import model.entity.PlayerRace;
import model.map.Locatable;
import model.map.LocationDatasource;
import model.map.Map;
import model.service.DefaultTurnService;
import presenters.PresenterContext;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by brian on 10/5/15.
 */
public class StartTileChoice extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) {

        //empty datasource for model.map//
        LocationDatasource lds = new LocationDatasource() {
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



        final MemoryPlayerRepository playerRepository = new MemoryPlayerRepository();
        Player p1 = new Player();
        p1.setName("P1");
        p1.setId(0);
        p1.setColor(Color.ALICEBLUE);
        p1.setRace(PlayerRace.Bonzoid);
        playerRepository.save(p1);

        Player p2 = new Player();
        p2.setName("P2");
        p2.setId(1);
        p2.setRace(PlayerRace.Buzzite);
        p2.setColor(Color.BLANCHEDALMOND);
        playerRepository.save(p2);


        final Map map = new Map(lds);

        final DefaultTurnService turnService = new DefaultTurnService(playerRepository, new StoreInfoHolder());

        PresenterContext context = new PresenterContext((binder) -> {
            binder.bind(LocationDatasource.class).toInstance(lds);
            binder.bind(new TypeLiteral<Repository<Player>>(){}).toInstance(playerRepository);

            //temp
            binder.bind(Map.class).toInstance(map);
            binder.bind(DefaultTurnService.class).toInstance(turnService);
        }, stage);

        context.showScreen("/presenters/map_grid_tile_select.fxml");
    }


}