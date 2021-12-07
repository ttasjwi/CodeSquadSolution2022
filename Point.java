import java.util.Objects;

public class Point {

    private int x; // x 좌표
    private int y; // y 좌표

    //생성자
    public Point (int x, int y) {
        this.x = x;
        this.y = y;
    }

    //x 좌표 반환
    public int getX() {
        return x;
    }

    //y 좌표 반환
    public int getY() {
        return y;
    }

    // 왼쪽 좌표 반환
    public Point getWestPoint() {
        return new Point(this.getX()-1, this.getY());
    }

    // 오른쪽 좌표 반환
    public Point getEastPoint() {
        return new Point(this.getX()+1, this.getY());
    }

    // 아랫쪽(남쪽) 좌표 반환
    public Point getSouthPoint() {
        return new Point(this.getX(), this.getY()+1);
    }

    // 윗쪽(윗쪽) 좌표 반환
    public Point getNorthPoint() {
        return new Point(this.getX(), this.getY()-1);
    }

    @Override
    //좌표의 상등
    public boolean equals(Object o) {
        if (!(o instanceof Point)) return false;
        Point p = (Point) o;
        return (this.x == p.getX() && this.y == p.getY());
    }

    @Override
    //좌표의 해시코드
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

    @Override
    //toString : (x, y)의 형태로 좌표문자열 반환
    public String toString() {
        return "("+this.x+", "+this.y+")";
    }
}
