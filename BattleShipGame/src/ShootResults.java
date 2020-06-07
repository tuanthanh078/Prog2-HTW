
public enum ShootResults {
    SHOT(1), MISSED(-1), SUNK_1_SHIP(0), GAMEOVER(2);
    
    private int value;
    
    private ShootResults(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return this.value;
    }
}
