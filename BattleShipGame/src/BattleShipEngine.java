

import java.io.IOException;

public class BattleShipEngine implements BattleShipSender, BattleShipReceiver {
    public static final int UNDEFINED_DICE = -1;

    private BattleShipStatus status;
    private int sentDice = UNDEFINED_DICE;
    
    public BattleShipEngine() {
        this.status = BattleShipStatus.START;
    }
    
    @Override
    public void receiveDice() throws IOException, StatusException {
        int random = 0; // some tcp codes
        if (this.status != BattleShipStatus.START
                && this.status != BattleShipStatus.DICE_SENT) {
                throw new StatusException();
        }
        if (this.status == BattleShipStatus.DICE_SENT) {
            if (random == this.sentDice) {
                this.status = BattleShipStatus.START;
            } else if (random > this.sentDice) {
                this.status = BattleShipStatus.PASSIVE;
            } else {
                this.status = BattleShipStatus.ACTIVE;
            }
        }
    }

    @Override
    public int[] receiveShootData() throws IOException, StatusException {
        if(this.status != BattleShipStatus.PASSIVE) {
            throw new StatusException();
        }
        // some tcp codes
        return null;
    }

    @Override
    public void sendDice(int random) throws IOException, StatusException {
        if(this.status != BattleShipStatus.START) {
            throw new StatusException();
        }
        this.status = BattleShipStatus.DICE_SENT;
        // some tcp codes
    }

    @Override
    public void sendShoot(int x, int y) throws IOException, StatusException {
        if(this.status != BattleShipStatus.ACTIVE) {
            throw new StatusException();
        }
        // some tcp codes
    }

}
