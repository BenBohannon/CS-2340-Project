/**
 * Created by brian on 9/10/15.
 */

import com.google.inject.TypeLiteral;
import data.*;
import data.abstractsources.LocationDatasource;
import data.abstractsources.Repository;
import data.abstractsources.StoreDatasource;
import data.abstractsources.TurnDatasource;
import data.concretesources.MemoryPlayerRepository;
import data.concretesources.SqlStoreDatasource;
import data.concretesources.SqlTurnDatasource;
import javafx.application.Application;
import javafx.stage.Stage;
import model.entity.Player;
import model.map.Locatable;
import model.map.Map;
import model.service.DefaultTurnService;
import model.service.StoreService;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import presenters.PresenterContext;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

public class Start extends Application {

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

        final Map map = new Map(lds);

        // A SessionFactory is set up once for an application!
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
            StandardServiceRegistryBuilder.destroy(registry);
        }
        final SessionFactory finalSessionFactory = sessionFactory;

        final DefaultTurnService turnService = new DefaultTurnService(playerRepository,
                new StoreService(new SqlStoreDatasource(finalSessionFactory)), new GameInfoDatasource(),
                new SqlTurnDatasource(finalSessionFactory));

        PresenterContext context = new PresenterContext((binder) -> {
            binder.bind(StoreDatasource.class).to(SqlStoreDatasource.class);
            binder.bind(TurnDatasource.class).to(SqlTurnDatasource.class);

            binder.bind(LocationDatasource.class).toInstance(lds);
            binder.bind(new TypeLiteral<Repository<Player>>(){}).toInstance(playerRepository);
            binder.bind(SessionFactory.class).toInstance(finalSessionFactory);

            //temp
            binder.bind(Map.class).toInstance(map);
            binder.bind(DefaultTurnService.class).toInstance(turnService);
        }, stage);

        context.showScreen("home_screen.fxml");
    }


}
