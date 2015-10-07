import data.MemoryPlayerRepository;
import data.StoreInfoHolder;
import junit.framework.Assert;
import model.entity.Player;
import model.service.DefaultTurnService;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;
/**
 * Created by brian on 9/30/15.
 */
public class TestDefaultTurnService {

    DefaultTurnService turnService;

    MemoryPlayerRepository playerRepository;
    StoreInfoHolder storeInfoHolder;

    @Before
    public void setUp() {
        playerRepository = new MemoryPlayerRepository();
        storeInfoHolder = new StoreInfoHolder();
        Player[] players = new Player[4];
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player();
            players[i].setId(-1);
            playerRepository.save(players[i]);
        }
        turnService = new DefaultTurnService(playerRepository, storeInfoHolder);
    }


    @Test
    public void testStartTurn() {
        turnService.beginRound();
        for (int i = 0; i < 4; i++) {
            Player p = turnService.beginTurn();
            turnService.endTurn();
        }
        Assert.assertTrue("all players should be done", turnService.isAllTurnsOver());

        turnService.beginRound();
        Assert.assertEquals("should be round 2", 2, turnService.getRoundNumber());

        Assert.assertNull("current player should be null", turnService.getCurrentPlayer());

        Assert.assertFalse("Turn should not be in progress", turnService.isTurnInProgress());

        turnService.beginTurn();

        Assert.assertTrue("time should be greater than one", turnService.getTimeLeftInTurn() > 1);
        Assert.assertTrue("turn should be ongoing", turnService.isTurnInProgress());
    }
}
