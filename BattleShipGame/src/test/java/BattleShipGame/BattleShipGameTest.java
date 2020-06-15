package BattleShipGame;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;


import gameEngine.GameEngine;
import gameEngine.GameException;
import gameEngine.StatusException;

public class BattleShipGameTest {
    @Test
    public void usageTest() throws GameException, StatusException, IOException {
        ShortCut sender1 = new ShortCut();
        GameEngine game1 = new GameEngine(sender1);

        ShortCut sender2 = new ShortCut();
        GameEngine game2 = new GameEngine(sender2);

        // connect both games
        sender1.setReceiver(game2);
        sender2.setReceiver(game1);

        // test methods
        game1.doDice();
        game2.doDice();

        GameEngine diceWinner = game1.isActive() ? game1 : game2;
        GameEngine diceLoser = game1.isActive() ? game2 : game1;
        System.out.println(diceWinner.isActive());
        System.out.println(diceLoser.isActive());
        diceWinner.sendShoot(0, 0);
        Assert.assertFalse(diceWinner.isActive());
        System.out.println(diceWinner.isActive());
        System.out.println(diceLoser.isActive());
        diceLoser.sendShoot(9, 9);
        Assert.assertFalse(diceLoser.isActive());
        
    }
}
