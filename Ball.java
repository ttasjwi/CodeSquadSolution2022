public class Ball extends MapObject implements Movable {

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
    public Point getPoint() {
        return this.point;
    }

    @Override
    public void moveTo(Point p)  {
        this.point = p;
    }
    @Override
    public boolean hasHall() {
        return this.ownhall != null;
    }
    @Override
    public Ball addHall(Hall hall) {
        this.ownhall = hall;
        return this;
    }

    @Override
    public Hall removeHall() {
        Hall hall = this.ownhall;
        this.ownhall = null;
        return hall;
    }

}