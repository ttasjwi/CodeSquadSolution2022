
public class Hall implements MapObject {
    private static final char symbol = 'O';

    @Override
    public char getSymbol() {
        return this.symbol;
    }

    @Override
    public boolean isMovable() {
        return false;
    }
}

