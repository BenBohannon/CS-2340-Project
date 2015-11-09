package presenters;

import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import model.service.DefaultTurnService;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


/**
 * Created by brian on 9/10/15.
 */
public class StartPresenter extends Presenter {

    private SessionFactory sessionFactory;
    private DefaultTurnService turnService;

    @Inject
    public StartPresenter(SessionFactory pSessionFactory, DefaultTurnService pTurnService) {
        sessionFactory = pSessionFactory;
        turnService = pTurnService;
    }

    @FXML
    /**
     * reads past game state from db and starts
     *
     * NOTE: Cyclomatic complexity is high, but the code is easy to follow.
     *  This will be replaced by loading a single GameState object when
     *  multiple saves are implemented
     */
    protected final void handleStartEvent(ActionEvent event) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query playerQuery = session.createQuery("FROM Player");
        if (playerQuery.list() != null) {
            for (Object p : playerQuery.list()) {
                session.delete(p);
            }
        }

        Query muleQuery = session.createQuery("FROM Mule");
        if (muleQuery.list() != null) {
            for (Object m : muleQuery.list()) {
                session.delete(m);
            }
        }

        Query tileQuery = session.createQuery("FROM Tile");
        if (tileQuery.list() != null) {
            for (Object t : tileQuery.list()) {
                session.delete(t);
            }
        }

        Query storeQuery = session.createQuery("FROM StoreRecord");
        if (storeQuery.list() != null) {
            for (Object storeRecord : storeQuery.list()) {
                session.delete(storeRecord);
            }
        }

        Query turnQuery = session.createQuery("FROM TurnRecord");
        if (turnQuery.list() != null) {
            for (Object turnRecord : turnQuery.list()) {
                session.delete(turnRecord);
            }
        }

        // For good measure //
        Query locatableQuery = session.createQuery("FROM PersistableLocatable");
        if (locatableQuery.list() != null) {
            for (Object locatableRecord : locatableQuery.list()) {
                session.delete(locatableRecord);
            }
        }

        session.getTransaction().commit();
        session.flush();
        session.close();

        getContext().showScreen("config_screen.fxml");
    }

    @FXML
    protected final void handleLoadEvent(ActionEvent event) {
        turnService.initializeFromDatasource();
        getContext().showScreen("map_grid_tile_select.fxml");
    }


}
