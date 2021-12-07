
public class Space extends MapObject {

    private static final char symbol = ' ';

    private final Stage stage;
    private Point point;

    public Space(Stage stage, Point point) {
        this.stage = stage;
        this.point = point;
    }

    @Override
    public char getSymbol() {
        return this.symbol;
    }

    @Override
    public boolean isMovable() {
        return true;
    }

}