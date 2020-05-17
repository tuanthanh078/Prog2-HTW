

import java.io.IOException;

public interface BattleShipReceiver {
    void receiveDice() throws IOException, StatusException;
    int[] receiveShootData() throws IOException, StatusException;
}
