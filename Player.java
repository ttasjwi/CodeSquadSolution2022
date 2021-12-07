
public class Player extends MapObject {

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

    public void addHall(Hall hall) {
        this.ownhall = hall;
    }

    public Hall removeHall() {
        Hall hall = this.ownhall;
        this.ownhall = null;
        return hall;
    }

    public boolean hasHall() {
        return this.ownhall == null;
    }

}