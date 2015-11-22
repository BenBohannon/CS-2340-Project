/**
 * Created by brian on 9/10/15.
 */

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
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
import model.service.DefaultTurnService;
import model.service.StoreService;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import presenters.PresenterContext;
import view.MapView;

import java.io.File;

public class Start extends Application {

    private static final int STARTING_WINDOW_HEIGHT = 490;
    private static final int STARTING_WINDOW_WIDTH = 890;

    /**
     * main method to launch program.
     * @param args passed in paramaters
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        // A SessionFactory is set up once for an application //
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure(new File(getClass().getResource(
                        checkForDB() ? "/sql/hibernate.cfg.xml" : "/sql/createhibernate.cfg.xml").getFile()))
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
        Repository<Player> playerRepository = new SqlPlayerRepository(finalSessionFactory);

        final DefaultTurnService turnService = new DefaultTurnService(playerRepository,
                new StoreService(new SqlStoreDatasource(finalSessionFactory), playerRepository)
                , new GameInfoDatasource(), new SqlTurnDatasource(finalSessionFactory));

        PresenterContext context = new PresenterContext(new Module() {
            @Override
            public void configure(Binder binder) {
                // class level bindings //
                binder.bind(StoreDatasource.class).to(SqlStoreDatasource.class);
                binder.bind(TurnDatasource.class).to(SqlTurnDatasource.class);
                binder.bind(LocationDatasource.class).to(SqlLocationDatasource.class);
                binder.bind(new TypeLiteral<Repository<Player>>() {}).to(SqlPlayerRepository.class);
                binder.bind(new TypeLiteral<Repository<Mule>>() {}).to(SqlMuleRepository.class);

                // instance level bindings //
                binder.bind(SessionFactory.class).toInstance(finalSessionFactory);
                binder.bind(DefaultTurnService.class).toInstance(turnService);

                bindConstants(binder);
            }
        }, stage, STARTING_WINDOW_WIDTH, STARTING_WINDOW_HEIGHT);

        context.showScreen("home_screen.fxml");
    }

    /**
     * helper method for setting the desired initial values for each of these properties.
     * @return the data object set to the desired initial state.
     */
    private StoreRecord getInitialStoreState() {
        StoreRecord record = new StoreRecord();
        record.setFood(16);
        record.setEnergy(16);

        record.setFoodPrice(3000);
        record.setEnergyPrice(2500);
        record.setSmithorePrice(5000);
        record.setCrystitePrice(10000);

        return record;
    }

    /**
     * helper method that binds constant values to named annotations.
     * This prevents the use of magic numbers, and also allows all
     * configuration to reside in the same place.
     * @param binder the binder that will be used to bind the constants.
     */
    private void bindConstants(Binder binder) {
        binder.bindConstant()
                .annotatedWith(Names.named("InitialPlayerMoney"))
                .to(10000);
        binder.bind(StoreRecord.class)
                .annotatedWith(Names.named("InitialStoreState"))
                .toInstance(getInitialStoreState());
        binder.bindConstant()
                .annotatedWith(Names.named("MapRows"))
                .to(5);
        binder.bindConstant()
                .annotatedWith(Names.named("MapCols"))
                .to(9);
        binder.bindConstant()
                .annotatedWith(Names.named("MaxRandomEventMoney"))
                .to(200);
        binder.bindConstant()
                .annotatedWith(Names.named("MinRandomEventMoney"))
                .to(-200);
        binder.bindConstant()
                .annotatedWith(Names.named("TurnStartDelay"))
                .to(5010);
        binder.bindConstant()
                .annotatedWith(Names.named("PlayerImageDimensions"))
                .to(25);
        binder.bindConstant()
                .annotatedWith(Names.named("PlayerStartingX"))
                .to(340);
        binder.bindConstant()
                .annotatedWith(Names.named("PlayerStartingY"))
                .to(235);
        binder.bindConstant()
                .annotatedWith(Names.named("TileDimensions"))
                .to(100);
        binder.bindConstant()
                .annotatedWith(Names.named("TimeRemainingBarWidth"))
                .to(200);
        binder.bindConstant()
                .annotatedWith(Names.named("TimeRemainingBarHeight"))
                .to(20);
        binder.bindConstant()
                .annotatedWith(Names.named("MuleDimensions"))
                .to(40);
        binder.bindConstant()
                .annotatedWith(Names.named("PlayerImageAnimationSpeed"))
                .to(80);
        binder.bindConstant()
                .annotatedWith(Names.named("AnimationFrameTime"))
                .to(16);
        binder.bindConstant()
                .annotatedWith(Names.named("MouseAnimationDelay"))
                .to(300);
        binder.bindConstant()
                .annotatedWith(Names.named("TextInitialX"))
                .to(250);
        binder.bindConstant()
                .annotatedWith(Names.named("TextInitialY"))
                .to(120);
        binder.bindConstant()
                .annotatedWith(Names.named("MainFontSize"))
                .to(40);
        binder.bindConstant()
                .annotatedWith(Names.named("MovementStartDelay"))
                .to(2000);
        binder.bindConstant()
                .annotatedWith(Names.named("OwnershipRectWidth"))
                .to(10);
        binder.bindConstant()
                .annotatedWith(Names.named("MaxPlayers"))
                .to(4);
        binder.bindConstant()
                .annotatedWith(Names.named("TileIterationInterval"))
                .to(200);
        binder.bindConstant()
                .annotatedWith(Names.named("TileIterationStartDelay"))
                .to(1000);
        binder.bindConstant()
                .annotatedWith(Names.named("MulePrice"))
                .to(300);
        binder.bindConstant()
                .annotatedWith(Names.named("StartingWindowWidth"))
                .to(STARTING_WINDOW_WIDTH);
        binder.bindConstant()
                .annotatedWith(Names.named("StartingWindowHeight"))
                .to(STARTING_WINDOW_HEIGHT);
        binder.bindConstant()
                .annotatedWith(Names.named("FirstPubRoundThreshold"))
                .to(3);
        binder.bindConstant()
                .annotatedWith(Names.named("SecondPubRoundThreshold"))
                .to(6);
        binder.bindConstant()
                .annotatedWith(Names.named("FirstPubWinningLimit"))
                .to(50);
        binder.bindConstant()
                .annotatedWith(Names.named("SecondPubWinningLimit"))
                .to(100);
        binder.bindConstant()
                .annotatedWith(Names.named("ThirdPubWinningLimit"))
                .to(150);
        binder.bindConstant()
                .annotatedWith(Names.named("StartBiddingDelay"))
                .to(4000);
        binder.bindConstant()
                .annotatedWith(Names.named("InvertTurnOrderThreshold"))
                .to(7);

        binder.requestStaticInjection(MapView.class);
    }

    private boolean checkForDB() {
        return new File(System.getProperty("user.home") + "/.mule.h2.db").exists();
    }

}
