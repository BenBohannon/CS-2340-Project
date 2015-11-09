import com.google.inject.TypeLiteral;
import data.*;
import data.abstractsources.LocationDatasource;
import data.abstractsources.Repository;
import data.abstractsources.StoreDatasource;
import data.abstractsources.TurnDatasource;
import data.concretesources.*;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.entity.Mule;
import model.entity.Player;
import model.entity.PlayerRace;
import model.map.Locatable;
import model.map.Map;
import model.service.DefaultTurnService;
import model.service.StoreService;
import org.h2.jdbcx.JdbcConnectionPool;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import presenters.PresenterContext;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;
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

            @Override
            public void remove(Locatable locatable) {

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

        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure(new File(getClass().getResource("/sql/hibernate.cfg.xml").getFile())) // configures settings from hibernate.cfg.xml
                .build();
        SessionFactory sessionFactory = null;
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            e.printStackTrace();
            StandardServiceRegistryBuilder.destroy(registry);
        }
        final SessionFactory finalSessionFactory = sessionFactory;

        final DefaultTurnService turnService = new DefaultTurnService(playerRepository,
                new StoreService(new SqlStoreDatasource(finalSessionFactory), playerRepository),
                new GameInfoDatasource(), new SqlTurnDatasource(finalSessionFactory));

        PresenterContext context = new PresenterContext((binder) -> {
            binder.bind(StoreDatasource.class).to(SqlStoreDatasource.class);
            binder.bind(TurnDatasource.class).to(SqlTurnDatasource.class);
            binder.bind(new TypeLiteral<Repository<Mule>>(){}).to(SqlMuleRepository.class);
            binder.bind(new TypeLiteral<Repository<Player>>(){}).to(SqlPlayerRepository.class);
            binder.bind(LocationDatasource.class).to(SqlLocationDatasource.class);

            binder.bind(SessionFactory.class).toInstance(finalSessionFactory);
            binder.bind(DefaultTurnService.class).toInstance(turnService);
        }, stage);

        context.showScreen("/presenters/map_grid_tile_select.fxml");
    }


}