
public interface MapObject {

    // 지정 symbol을 읽고 인스턴스를 반환한다.
    static MapObject getInstance(char symbol) {
        switch(symbol) {
            case '#' : return new Wall();
            case 'O' : return new Hall();
            case 'o' : return new Ball();
            case 'P' : return new Player();
            default: return new Space();
        }
    }

    char getSymbol();

    boolean isMovable();
}