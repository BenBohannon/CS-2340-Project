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
     *  multiple saves are implemented.
     */
    protected final void handleStartEvent(ActionEvent event) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        clearTable("Player", session);

        clearTable("Mule", session);

        clearTable("Tile", session);

        clearTable("StoreRecord", session);

        clearTable("TurnRecord", session);

        clearTable("PersistableLocatable", session);

        session.getTransaction().commit();
        session.flush();
        session.close();

        getContext().showScreen("config_screen.fxml");
    }

    private void clearTable(String tableName, Session session) {
        String queryString = new StringBuilder("FROM ")
                .append(tableName)
                .toString();
        Query query = session.createQuery(queryString);
        if (query.list() != null) {
            for (Object locatableRecord : query.list()) {
                session.delete(locatableRecord);
            }
        }
    }

    @FXML
    protected final void handleLoadEvent(ActionEvent event) {
        turnService.initializeFromDatasource();
        getContext().showScreen("map_grid_tile_select.fxml");
    }


}
