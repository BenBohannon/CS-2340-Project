/**
 * Created by brian on 9/10/15.
 */

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import data.*;
import data.abstractsources.LocationDatasource;
import data.abstractsources.Repository;
import data.abstractsources.StoreDatasource;
import data.abstractsources.TurnDatasource;
import data.concretesources.*;
import javafx.application.Application;
import javafx.stage.Stage;
import model.entity.Mule;
import model.entity.Player;
import model.map.Locatable;
import model.map.Map;
import model.service.DefaultTurnService;
import model.service.StoreService;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.loader.custom.sql.SQLQueryParser;
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

        // A SessionFactory is set up once for an application //
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
        Repository<Player> playerRepository = new SqlPlayerRepository(finalSessionFactory);

        final DefaultTurnService turnService = new DefaultTurnService(playerRepository,
                new StoreService(new SqlStoreDatasource(finalSessionFactory), playerRepository)
                , new GameInfoDatasource(), new SqlTurnDatasource(finalSessionFactory));

        PresenterContext context = new PresenterContext(binder -> {
            // class level bindings //
            binder.bind(StoreDatasource.class).to(SqlStoreDatasource.class);
            binder.bind(TurnDatasource.class).to(SqlTurnDatasource.class);
            binder.bind(LocationDatasource.class).to(SqlLocationDatasource.class);
            binder.bind(new TypeLiteral<Repository<Player>>(){}).to(SqlPlayerRepository.class);
            binder.bind(new TypeLiteral<Repository<Mule>>(){}).to(SqlMuleRepository.class);

            // instance level bindings //
            binder.bind(SessionFactory.class).toInstance(finalSessionFactory);
            binder.bind(DefaultTurnService.class).toInstance(turnService);

            // constants / config //
            binder.bindConstant()
                    .annotatedWith(Names.named("InitialPlayerMoney"))
                    .to(10000);
            binder.bind(StoreRecord.class)
                    .annotatedWith(Names.named("InitialStoreState"))
                    .toInstance(getInitialStoreState());
        }, stage);

        context.showScreen("home_screen.fxml");
    }

    private final StoreRecord getInitialStoreState() {
        StoreRecord record = new StoreRecord();
        record.setFood(16);
        record.setEnergy(16);

        record.setFoodPrice(3000);
        record.setEnergyPrice(2500);
        record.setSmithorePrice(5000);
        record.setCrystitePrice(10000);

        return record;
    }

}
