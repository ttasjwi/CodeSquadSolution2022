
class Wall extends MapObject {
    private static final char symbol = '#';

    private final Stage stage;
    private Point point;

    public Wall(Stage stage, Point point) {
        this.stage = stage;
        this.point = point;
    }

    @Override
    public char getSymbol() {
        return this.symbol;
    }

    @Override
    public boolean isMovable() {
        return false;
    }

    @Override
    public Point getPoint() {
        return this.point;
    }

}