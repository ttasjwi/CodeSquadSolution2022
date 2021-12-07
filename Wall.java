
class Wall extends MapObject {
    private static final char symbol = '#';

    @Override
    public char getSymbol() {
        return this.symbol;
    }

    @Override
    public boolean isMovable() {
        return false;
    }
}