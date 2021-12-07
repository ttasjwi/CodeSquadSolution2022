
public abstract class MapObject {

    // 지정 symbol을 읽고 인스턴스를 반환한다.
    public static MapObject getInstance(Stage stage, Point point, char symbol) {
        switch(symbol) {
            case '#' : return new Wall(stage, point);
            case 'O' : return new Hall(stage, point);
            case 'o' : return new Ball(stage, point);
            case 'P' : return new Player(stage, point);
            case '=' : return new StageDelim(stage, point);
            default: return new Space(stage, point);
        }
    }

    public abstract char getSymbol();

    public abstract boolean isMovable();

    public abstract Point getPoint();
}