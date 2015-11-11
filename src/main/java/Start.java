/**
 * Created by brian on 9/10/15.
 */

import com.google.inject.TypeLiteral;
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
import model.service.DefaultTurnService;
import model.service.StoreService;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import presenters.PresenterContext;

import java.io.File;
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

        final DefaultTurnService turnService = new DefaultTurnService(new SqlPlayerRepository(finalSessionFactory),
                new StoreService(new SqlStoreDatasource(finalSessionFactory)), new GameInfoDatasource(),
                new SqlTurnDatasource(finalSessionFactory));

        PresenterContext context = new PresenterContext((binder) -> {
            binder.bind(StoreDatasource.class).to(SqlStoreDatasource.class);
            binder.bind(TurnDatasource.class).to(SqlTurnDatasource.class);
            binder.bind(LocationDatasource.class).to(SqlLocationDatasource.class);
            binder.bind(new TypeLiteral<Repository<Player>>(){}).to(SqlPlayerRepository.class);
            binder.bind(new TypeLiteral<Repository<Mule>>(){}).to(SqlMuleRepository.class);

            binder.bind(SessionFactory.class).toInstance(finalSessionFactory);
            binder.bind(DefaultTurnService.class).toInstance(turnService);
        }, stage);

        context.showScreen("home_screen.fxml");
    }


}
