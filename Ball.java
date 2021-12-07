public class Ball extends MapObject {

    private static final char symbol = 'o';

    @Override
    public char getSymbol() {
        return this.symbol;
    }

    @Override
    public boolean isMovable() {
        return false;
    }

}