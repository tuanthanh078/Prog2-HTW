package dataStructure;

public enum BoardCell {
    WATER("-"), 
    SHIP("s"), 
    SUNK("X"),
    SHOT("x"),
    MISSED("O");
    
    private String symbol;
    
    private BoardCell(String symbol) {
        this.symbol = symbol; 
    }
    
    public String getSymbol() {
        return this.symbol;
    }
}
