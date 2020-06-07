

import java.io.IOException;

public interface GameReceiver {
    void receiveDice(int random) throws IOException, StatusException;
    void receiveShoot(int x, int y) throws IOException, StatusException, GameException;
    void receiveShootResult(int[] result) throws IOException, StatusException;
}
