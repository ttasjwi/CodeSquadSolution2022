
public class Player implements MapObject {
    private static final char symbol = 'P';

    @Override
    public char getSymbol() {
        return this.symbol;
    }

    @Override
    public boolean isMovable() {
        return true;
    }
}