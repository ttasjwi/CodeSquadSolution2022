public interface Movable {

    void moveTo(Point p);

    boolean hasHall();

    Movable addHall(Hall hall);

    Hall removeHall();

    Point getPoint();

}
