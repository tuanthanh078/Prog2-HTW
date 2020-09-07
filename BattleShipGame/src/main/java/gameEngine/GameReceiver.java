package gameEngine;

import java.io.IOException;

/**
 * This class is an interface for a receiver of game data.
 *
 */
public interface GameReceiver {
    
    /**
     * Receives dice number
     * 
     * @param random    dice number
     * @throws IOException
     * @throws StatusException
     */
    void receiveDice(int random) throws IOException, StatusException;
    
    /**
     * Receives coordinates of the point to shoot
     * 
     * @param x     coordinate x of the point
     * @param y     coordinate y of the point
     * @throws IOException
     * @throws StatusException
     * @throws GameException
     */
    void receiveShoot(int x, int y) throws IOException, StatusException, GameException;
    
    /**
     * Receives result after being shot
     * 
     * @param result    description of result
     * @throws IOException
     * @throws StatusException
     */
    void receiveShootResult(int[] result) throws IOException, StatusException;
}
