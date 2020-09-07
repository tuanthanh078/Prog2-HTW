package gameEngine;

import java.io.IOException;

/**
 * This class is an interface for a sender of game data.
 *
 */
public interface GameSender {
    
    /**
     * Sends dice number
     * 
     * @param random    dice number
     * @throws IOException
     * @throws StatusException
     */
    void sendDice(int random) throws IOException, StatusException;
    
    /**
     * Sends coordinates of the point to shoot
     * 
     * @param x     coordinate x of the point
     * @param y     coordinate y of the point
     * @throws IOException
     * @throws StatusException
     * @throws GameException
     */
    void sendShoot(int x, int y) throws IOException, StatusException, GameException;
    
    /**
     * Sends result after being shot
     * 
     * @param result    description of result
     * @throws IOException
     * @throws StatusException
     */
    void sendShootResult(int[] result) throws IOException, StatusException;
}
