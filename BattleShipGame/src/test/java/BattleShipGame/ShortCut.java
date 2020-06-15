package BattleShipGame;

import java.io.IOException;

import gameEngine.GameException;
import gameEngine.GameReceiver;
import gameEngine.GameSender;
import gameEngine.StatusException;

public class ShortCut implements GameSender {
    private GameReceiver receiver;
    
    public void setReceiver(GameReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void sendDice(int random) throws IOException, StatusException {
        this.receiver.receiveDice(random);
    }

    @Override
    public void sendShoot(int x, int y) throws IOException, StatusException, GameException {
        this.receiver.receiveShoot(x, y);
    }

    @Override
    public void sendShootResult(int[] result) throws IOException, StatusException {
        this.receiver.receiveShootResult(result);       
    }
}
