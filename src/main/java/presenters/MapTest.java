package presenters;

import com.google.inject.Inject;
import data.MemoryPlayerRepository;
import javafx.scene.paint.Color;
import model.entity.Mule;
import model.entity.Player;
import model.entity.PlayerRace;
import model.map.Map;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.awt.*;

//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;

/**
 * Created by kylemurray on 11/4/15.
 */
public class MapTest {

    @Inject
    public Map map;

    private MapPresenter presenter;

    @Rule
    public Timeout timeout = new Timeout(200);

    @Before
    public void setUp() {
        presenter = new MapPresenter();
    }

    @Test
    public void checkNoneSelected() {
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

        Player p3 = new Player();
        p3.setName("P3");
        p3.setId(2);
        p3.setColor(Color.AQUAMARINE);
        p3.setRace(PlayerRace.Ugaite);
        playerRepository.save(p3);

        Player p4 = new Player();
        p4.setName("P4");
        p4.setId(3);
        p4.setRace(PlayerRace.Human);
        p4.setColor(Color.BLACK);
        playerRepository.save(p4);

//        Tile tile = (Tile) map.getOccupants(1, 1)[0];
        boolean occupied = map.getOccupants(1, 1, Mule.class).length > 0;
        assert(!occupied);

//        int selectionRound = 0;

//        p1.buyProperty(tile, selectionRound > 1 ? -300 : 0);

        presenter.onClick(new Point(1, 1));
        occupied = map.getOccupants(1, 1, Mule.class).length > 0;
        assert(occupied);
    }
}

