package model.service;

import junit.framework.TestCase;
import model.entity.Player;

import java.util.ArrayList;

/**
 * Created by connor on 11/5/15.
 */
public class CalculateRankTest extends TestCase {
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private ArrayList<Player> players;
    private Object[] players2;

    //@Before
    /*public void setUp() {
        player1 = new Player();
        player2 = new Player();
        player3 = new Player();
        player4 = new Player();
        System.out.println(player1);
        player1.setMoney(100);
        player2.setMoney(150);
        player3.setMoney(75);
        player4.setMoney(200);

        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        players2 = players.toArray();
    }*/

    //@Test
    public void testInitialRanks() {
        player1 = new Player(100);
        player2 = new Player(150);
        player3 = new Player(75);
        player4 = new Player(200);

        players2 = new Object[4];
        players2[0] = player1;
        players2[1] = player2;
        players2[2] = player3;
        players2[3] = player4;
        DefaultTurnService.calculateRank(players2);
        assertEquals(1, player4.getRank());
        assertEquals(2, player2.getRank());
        assertEquals(3, player1.getRank());
        assertEquals(4, player3.getRank());
    }

    //@Test
    public void testDiffRanks() {
        player1 = new Player(50);
        player2 = new Player(200);
        player3 = new Player(500);
        player4 = new Player(100);

        players2 = new Object[4];
        players2[0] = player1;
        players2[1] = player2;
        players2[2] = player3;
        players2[3] = player4;

        DefaultTurnService.calculateRank(players2);
        assertEquals(1, player3.getRank());
        assertEquals(2, player2.getRank());
        assertEquals(3, player4.getRank());
        assertEquals(4, player1.getRank());
    }

    public void testOneMoreRanksTest() {
        player1 = new Player(500);
        player2 = new Player(10);
        player3 = new Player(300);
        player4 = new Player(150);

        players2 = new Object[4];
        players2[0] = player1;
        players2[1] = player2;
        players2[2] = player3;
        players2[3] = player4;

        DefaultTurnService.calculateRank(players2);
        assertEquals(1, player1.getRank());
        assertEquals(2, player3.getRank());
        assertEquals(3, player4.getRank());
        assertEquals(4, player2.getRank());
    }

    public void testOnePlayerRank() {
        player1 = new Player(200);
        players2 = new Object[1];
        players2[0] = player1;

        DefaultTurnService.calculateRank(players2);
        assertEquals(1, player1.getRank());
    }

    public void testSameScores() {
        player1 = new Player(100);
        player2 = new Player(100);
        player3 = new Player(100);
        player4 = new Player(100);

        players2 = new Object[4];
        players2[0] = player1;
        players2[1] = player2;
        players2[2] = player3;
        players2[3] = player4;

        DefaultTurnService.calculateRank(players2);
        assertEquals(1, player1.getRank());
        assertEquals(2, player2.getRank());
        assertEquals(3, player3.getRank());
        assertEquals(4, player4.getRank());
    }

}