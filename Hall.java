
public class Hall extends MapObject {
    private static final char symbol = 'O';

    private final Stage stage;
    private Point point;

    public Hall(Stage stage, Point point) {
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
}

