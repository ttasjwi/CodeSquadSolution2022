
public class Player extends MapObject implements Movable {

    private static final char symbol = 'P';

    private final Stage stage;
    private Point point;
    private Hall ownhall;

    public Player(Stage stage, Point point) {
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

    @Override
    public Point getPoint() {
        return this.point;
    }

    @Override
    public Player addHall(Hall hall) {
        this.ownhall = hall;
        return this;
    }

    @Override
    public Hall removeHall() {
        Hall hall = this.ownhall;
        this.ownhall = null;
        return hall;
    }

    @Override
    public boolean hasHall() {
        return this.ownhall != null;
    }
    @Override
    public void moveTo(Point point) {
        this.point = point;
    }

}