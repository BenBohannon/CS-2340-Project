package UnitTests;

import junit.framework.TestCase;
import model.entity.Player;
import model.service.DefaultTurnService;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by connor on 11/5/15.
 */
public class CalculateRankTestTest extends TestCase {
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private ArrayList<Player> players;
    private Object[] players2;


    public void setUp() {
        player1 = new Player();
        player2 = new Player();
        player3 = new Player();
        player4 = new Player();

        player1.setMoney(100);
        player2.setMoney(150);
        player3.setMoney(75);
        player4.setMoney(200);

        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        players2 = players.toArray();
    }

    @Test
    public void initialRanks() {
        DefaultTurnService.calculateRank(players2);
        assertEquals(1, player4.getRank());
        assertEquals(2, player2.getRank());
        assertEquals(3, player1.getRank());
        assertEquals(4, player3.getRank());
    }

}