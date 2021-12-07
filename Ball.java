public class Ball extends MapObject {

    private static final char symbol = 'o';

    private final Stage stage;
    private Point point;
    private Hall ownhall;

    public Ball(Stage stage, Point point) {
        this.stage = stage;
        this.point = point;
    }

    @Override
    public char getSymbol() {
        return (hasHall())? '0' : this.symbol;
    }

    @Override
    public boolean isMovable() {
        return false;
    }

    @Override
    public Point getPoint() {
        return this.point;
    }

    public boolean hasHall() {
        return this.ownhall == null;
    }

    public void addHall(Hall hall) {
        this.ownhall = hall;
    }

    public Hall removeHall() {
        Hall hall = this.ownhall;
        this.ownhall = null;
        return hall;
    }

}