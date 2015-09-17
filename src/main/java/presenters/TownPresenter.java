package presenters;

import com.google.inject.Inject;
import data.Repository;
import model.Player;

/**
 * Created by brian on 9/17/15.
 */
public class TownPresenter extends Presenter {

    @Inject
    Repository<Player> playerRepository;

}
