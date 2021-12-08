public interface Movable {

    void moveTo(Point p);

    boolean hasHall();

    void addHall(Hall hall);

    Hall removeHall();

    Point getPoint();

}
