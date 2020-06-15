package gameEngine;

import java.io.IOException;

public interface GameUsage {
    void doDice() throws StatusException, IOException;
    boolean isActive();
    void shoot(int x, int y) throws GameException, StatusException, IOException;
}
