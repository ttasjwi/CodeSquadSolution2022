public class Ball extends MapObject {

    private static final char symbol = 'o';

    private final Stage stage;
    private Point point;

    public Ball(Stage stage, Point point) {
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