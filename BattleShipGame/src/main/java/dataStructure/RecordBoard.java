package dataStructure;

import gameEngine.GameException;

public class RecordBoard {
    public static final int DIM = 10;
    private BoardCell[][] board = new BoardCell[DIM][DIM];
    
    public RecordBoard() {
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                board[i][j] = BoardCell.WATER;
            }
        }
    }
    
    public void record(int x, int y, BoardCell cell) {
       board[x][y] = cell;
    }
    
    public void display() {
        System.out.println("Record Board");
        System.out.println("   1 2 3 4 5 6 7 8 9 10");
        for (int i = 0; i < DIM; i++) {
            
            if (i == 9) System.out.print(i+1 + " ");
            else System.out.print(" " + (i+1) + " ");
            
            for (int j = 0; j < DIM; j++) {
                System.out.print(board[i][j].getSymbol() + " ");
            }
            System.out.println();
        }
        
    }
    
    private void checkValidCoordinate(int x, int y) throws GameException {
        if (x >= DIM || y >= DIM || x < 0 || y < 0) {
            throw new GameException("Error: Unvalid coordinates");
        }
    }
    
    public void checkValidShoot(int x, int y) throws GameException {
        checkValidCoordinate(x, y);
        if (this.board[x][y] == BoardCell.MISSED ||
            this.board[x][y] == BoardCell.SHOT ||
            this.board[x][y] == BoardCell.SUNK) {
            throw new GameException("Error: Position already occupied");
        }
    }
    
    public void sunk(int[] shipLocationData) {
        int x = shipLocationData[0];
        int y = shipLocationData[1];
        int length = shipLocationData[2];
        int isHorizontal = shipLocationData[3];
        if (isHorizontal == 0) {
            for (int i = 0; i < length; i++) {
                this.record(x++, y, BoardCell.SUNK);
            }
        } else {
            for (int i = 0; i < length; i++) {
                this.record(x, y++, BoardCell.SUNK);
            }
        }
    }
}
