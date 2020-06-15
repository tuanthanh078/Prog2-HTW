package gameEngine;



import java.io.IOException;

public interface GameSender {
    void sendDice(int random) throws IOException, StatusException;
    void sendShoot(int x, int y) throws IOException, StatusException, GameException;
    void sendShootResult(int[] result) throws IOException, StatusException;
}
