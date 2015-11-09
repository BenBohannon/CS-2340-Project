package presenters;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import data.concretesources.StoreRecord;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import view.GameConfigView;

/**
 * Created by brian on 9/10/15.
 */
public class GameConfigPresenter extends Presenter<GameConfigView> {

    private SessionFactory sessionFactory;
    private Session session;

    private StoreRecord initialStoreState;


    @Inject
    public GameConfigPresenter(SessionFactory pSessionFactory,
                               @Named("InitialStoreState")StoreRecord pInitialStoreState) {
        sessionFactory = pSessionFactory;
        initialStoreState = pInitialStoreState;
    }

    public final void finishConfigure(String difficulty, String map, int numPlayers) {
        startTransaction();
        initStoreState();
        closeTransaction();

        //returns presenter of view passed, after stage has been set//
        PlayerConfigPresenter nextPresenter = (PlayerConfigPresenter) getContext().showScreen("player_config.fxml");

        //can pass info to next presenter//
        nextPresenter.setNumPlayersLeft(numPlayers - 1);
    }

    private void startTransaction() {
        if (session != null) {
            throw new RuntimeException("Session instance already created; something's up.");
        }
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    private void closeTransaction() {
        if (session == null) {
            throw new RuntimeException("Session is null; something's up.");
        }
        session.getTransaction().commit();
        session.flush();
        session.close();
        session = null;
    }

    private void initStoreState() {
        session.save(initialStoreState);
    }
}
