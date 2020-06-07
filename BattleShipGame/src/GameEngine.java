

import java.io.IOException;

public class GameEngine implements GameReceiver, GameSender {
    public static final int UNDEFINED_DICE = -1;
    public static final int DIM = 10;
    private final GameSender sender;
    private ShipBoard shipBoard = new ShipBoard();
    private RecordBoard recordBoard = new RecordBoard();
    private GameStatus status;
    private int sentDice = UNDEFINED_DICE;
    private int receivedRandom;
    private int lastX;
    private int lastY;
    private int[] enemyShootResults;
    
    public GameEngine(GameSender sender) {
        this.status = GameStatus.START;
        this.sender = sender;
    }
    
    @Override
    public void receiveDice(int random) throws IOException, StatusException {
        if (this.status != GameStatus.START
            && this.status != GameStatus.DICE_SENT) {
                throw new StatusException();
        }
        this.receivedRandom = random;
        if(this.status == GameStatus.DICE_SENT) {
            if(this.receivedRandom == this.sentDice) {
                this.status = GameStatus.START;
            } else if (this.receivedRandom > this.sentDice) {
                this.status = GameStatus.PASSIVE;
            } else {
                this.status = GameStatus.ACTIVE;
            }
        } else {
            this.status = GameStatus.DICE_RECEIVED;
        }
    }

    @Override
    public void receiveShoot(int x, int y) throws IOException, StatusException, GameException {
        if(this.status != GameStatus.PASSIVE) {
            throw new StatusException();
        }
        enemyShootResults = shipBoard.shoot(x, y);
        if (shipBoard.isGameOver()) {
            enemyShootResults[0] = ShootResults.GAMEOVER.getValue();
            this.status = GameStatus.LOST;
        }
    }
    
    @Override
    public void receiveShootResult(int[] result) throws IOException, StatusException {
        
        switch(result[0]) {
            case 1:
                recordBoard.record(lastX, lastY, BoardCell.SHOT);
                break;
            case -1:
                recordBoard.record(lastX, lastY, BoardCell.MISSED);
                break;
            case 0:
                int[] shipLocationData = new int[4];
                shipLocationData[0] = result[1];
                shipLocationData[1] = result[2];
                shipLocationData[2] = result[3];
                shipLocationData[3] = result[4];
                recordBoard.sunk(shipLocationData);
                break;
            case 2:
                shipLocationData = new int[4];
                shipLocationData[0] = result[1];
                shipLocationData[1] = result[2];
                shipLocationData[2] = result[3];
                shipLocationData[3] = result[4];
                recordBoard.sunk(shipLocationData);
                status = GameStatus.WON;
                break;
            default:
                System.err.print("Invalid result!");
                break;
        }
    }

    @Override
    public void sendDice(int random) throws IOException, StatusException {
        if(this.status != GameStatus.START) {
            throw new StatusException();
        }
        this.status = GameStatus.DICE_SENT;
        // some tcp codes sending random
    }

    @Override
    public void sendShoot(int x, int y) throws IOException, StatusException, GameException {
        if (this.status != GameStatus.ACTIVE) {
            throw new StatusException();
        }
        recordBoard.checkValidShoot(x, y);
        this.lastX = x;
        this.lastY = y;
        // some tcp codes sending x, y
        this.status = GameStatus.PASSIVE;

    }
    
    @Override
    public void sendShootResult(int[] result) throws IOException, StatusException {
        // some tcp codes sending enemyShootResults
    }

}
