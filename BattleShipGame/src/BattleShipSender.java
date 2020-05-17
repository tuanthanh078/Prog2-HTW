

import java.io.IOException;

public interface BattleShipSender {
    void sendDice(int random) throws IOException, StatusException;
    void sendShoot(int x, int y) throws IOException, StatusException;
}
