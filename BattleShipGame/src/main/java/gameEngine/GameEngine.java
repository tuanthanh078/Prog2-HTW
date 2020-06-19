package gameEngine;


import java.io.IOException;
import java.util.Random;

import dataStructure.BoardCell;
import dataStructure.RecordBoard;
import dataStructure.ShipBoard;
import dataStructure.ShootResults;

public class GameEngine implements GameReceiver, GameUsage {
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
    
    //////////////////////////////////////////////////////////////////////////////////////////
    ////                                   Receiver                                       ////
    //////////////////////////////////////////////////////////////////////////////////////////
    
    @Override
    public void receiveDice(int random) throws IOException, StatusException {
        if (this.status != GameStatus.START
            && this.status != GameStatus.DICE_SENT) {
                throw new StatusException();
        }
        this.receivedRandom = random;
        if(this.status == GameStatus.DICE_SENT) {
            this.decideWhoStarts();
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
        this.status = GameStatus.ACTIVE;
        if (shipBoard.isGameOver()) {
            enemyShootResults[0] = ShootResults.GAMEOVER.getValue();
            this.status = GameStatus.LOST;
            System.out.println("Game Over");
            System.out.println("You lost");
        }
        this.sendShootResult(enemyShootResults);
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
                System.out.println("Sunk 1 ship");
                break;
            case 2:
                shipLocationData = new int[4];
                shipLocationData[0] = result[1];
                shipLocationData[1] = result[2];
                shipLocationData[2] = result[3];
                shipLocationData[3] = result[4];
                recordBoard.sunk(shipLocationData);
                status = GameStatus.WON;
                System.out.println("Game Over");
                System.out.println("You won");
                break;
            default:
                System.err.print("Invalid result!");
                break;
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    ////                                   Sender                                         ////
    //////////////////////////////////////////////////////////////////////////////////////////
    
    public void sendDice(int random) throws IOException, StatusException {
        if(this.status != GameStatus.START) {
            throw new StatusException();
        }
        this.status = GameStatus.DICE_SENT;
        sender.sendDice(random);
    }

    public void sendShoot(int x, int y) throws IOException, StatusException, GameException {
        if (this.status != GameStatus.ACTIVE) {
            throw new StatusException();
        }
        recordBoard.checkValidShoot(x, y);
        this.lastX = x;
        this.lastY = y;
        sender.sendShoot(x, y);
        this.status = GameStatus.PASSIVE;
    }
    
    public void sendShootResult(int[] result) throws IOException, StatusException {
        sender.sendShootResult(result);
    }

    @Override
    public void doDice() throws StatusException, IOException {
        if(this.status != GameStatus.START
           && this.status != GameStatus.DICE_RECEIVED) {
                throw new StatusException();
        }
        Random r = new Random();
        this.sentDice = r.nextInt();
        this.sender.sendDice(this.sentDice);
        if (this.status == GameStatus.DICE_RECEIVED) {
            this.decideWhoStarts();
        } else {
            this.status = GameStatus.DICE_SENT;
        }
    }
    
    private void decideWhoStarts() {
        if (this.receivedRandom == this.sentDice) {
            this.status = GameStatus.START;
        } else if (this.receivedRandom > this.sentDice) {
            this.status = GameStatus.PASSIVE;
        } else {
            this.status = GameStatus.ACTIVE;
        }
    }
    
    @Override
    public boolean isActive() {
        return this.status == GameStatus.ACTIVE;
    }
    
    public boolean isPassive() {
        return this.status == GameStatus.PASSIVE;
    }
    
    public boolean isGameOver() {
        return this.status == GameStatus.WON || this.status == GameStatus.LOST;
    }
    
    @Override
    public void shoot(int x, int y) throws GameException, StatusException, IOException {

    }
    
    public void placeShip(int x, int y, int length, int isHorizontal) throws GameException {
        shipBoard.placeShip(x, y, length, isHorizontal);
    }
    
    public void displayShipBoard() {
        shipBoard.display();
    }
    
    public void displayRecordBoard() {
        recordBoard.display();
    }
}
