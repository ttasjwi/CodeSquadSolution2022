public class Stage {

    private String stageName; // 스테이지명
    private MapObject[][] originalMap; // 스테이지 맵
    private MapObject[][] currentMap; // 현재 맵

    public Stage(String stageName, MapObject[][] stageMap) {
        this.stageName = stageName;
        this.originalMap = stageMap;
        this.currentMap = reset();
    }

    // 스테이지를 초기화한다.
    private MapObject[][] reset() {
        MapObject[][] copyMap = new MapObject[originalMap.length][originalMap[0].length];
        for (int i=0; i<originalMap.length; i++) {
            for (int j=0; j<originalMap[i].length; j++) {
                char symbol = originalMap[i][j].getSymbol();
                copyMap[i][j] = MapObject.getInstance(symbol);
            }
        }
        return copyMap;
    }

    //stageMap의 각 인덱스의 값 문자로 형식화하고, 문자열로 반환
    public String getMapString() {
        StringBuilder sb = new StringBuilder();

        for (int i=0; i<currentMap.length-1; i++) { //마지막 줄은 제외한다.
            for (int j=0; j<currentMap[i].length; j++) {
                char symbol = currentMap[i][j].getSymbol();
                sb.append(symbol);
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    //StageMap에서 구멍의 갯수를 반환
    public int getNmbOfHall() {
        int countOfHall = 0;
        for (int i=0; i<currentMap.length; i++) {
            for (int j=0; j<currentMap[i].length; j++) {
                if (isHall(currentMap[i][j])) countOfHall ++;
            }
        }
        return countOfHall;
    }

    //SatgeMap에서 공의 갯수를 반환
    public int getNmbOfBall() {
        int countOfBall = 0;
        for (int i=0; i<currentMap.length; i++) {
            for (int j=0; j<currentMap[i].length; j++) {
                if (isBall(currentMap[i][j])) countOfBall ++;
            }
        }
        return countOfBall;
    }

    // 맵의 가로폭 반환
    public int getWidth() {
        return this.currentMap[0].length;
    }

    // 맵의 세로폭 반환
    public int getHeight() {
        return this.currentMap.length;
    }

    // 스테이지명 반환
    public String getStageName() {
        return this.stageName;
    }

    // 플레이어의 좌표 반환
    public Point getPointOfPlayer() {
        for (int i=0; i<currentMap.length; i++) {
            for (int j=0; j<currentMap[i].length; j++) {
                MapObject mapObject = currentMap[i][j];
                if (isPlayer(mapObject)) return new Point(j,i);
            }
        }
        return null;
    }

    // 지정 인덱스에 위치한 맵 오브젝트를 반환한다.
    private MapObject getMapObject(int i, int j) {
        return currentMap[i][j];
    }

    // 지정 인덱스에 위치한 맵 오브젝트를 반환한다.
    private MapObject getMapObject(Point p) {
        return getMapObject(p.getY(), p.getX());
    }

    // 지정 맵 오브젝트가 플레이어인지 여부를 반환
    private boolean isPlayer(MapObject mo) {
        return (mo instanceof Player);
    }

    // 지정 맵 오브젝트가 공인지 여부를 반환
    private boolean isBall(MapObject mo) {
        return (mo instanceof Ball);
    }

    // 지정 맵 오브젝트가 구멍인지 여부를 반환
    private boolean isHall(MapObject mo) {
        return (mo instanceof Hall);
    }

    // 지정 맵 오브젝트가 빈 공간인지 여부를 반환
    private boolean isSpace(MapObject mo) {
        return (mo instanceof Space);
    }

    // p1과 p2에 위치한 객체를 자리바꿈한다.
    private boolean exchange(Point p1, Point p2) {
        if (p1 == p2) return false;
        MapObject mo1 = getMapObject(p1);
        MapObject mo2 = getMapObject(p2);

        if (!(mo1.isMovable()&&mo2.isMovable())) {
            System.out.println("이동 실패");
            return false;
        }
        currentMap[p1.getY()][p1.getX()] = mo2;
        currentMap[p2.getY()][p2.getX()] = mo1;
        System.out.println("이동 성공");
        return true;
    }

    // 플레이어를 동쪽으로 이동시킨다.
    public boolean movePlayerToEast() {
        System.out.println("오른쪽으로 이동합니다.");
        Point eastPoint = getPointOfPlayer().getEastPoint();
        return exchange(getPointOfPlayer(), eastPoint);
    }

    // 플레이어를 남쪽으로 이동시킨다.
    public boolean movePlayerToSouth() {
        System.out.println("아래로 이동합니다.");
        Point southPoint = getPointOfPlayer().getSouthPoint();
        return exchange(getPointOfPlayer(), southPoint);
    }

    // 플레이어를 서쪽으로 이동시킨다.
    public boolean movePlayerToWest() {
        System.out.println("왼쪽으로 이동합니다.");
        Point westPoint = getPointOfPlayer().getWestPoint();
        return exchange(getPointOfPlayer(), westPoint);
    }

    // 플레이어를 북쪽으로 이동시킨다.
    public boolean movePlayerToNorth() {
        System.out.println("위로 이동합니다.");
        Point northPoint = getPointOfPlayer().getNorthPoint();
        return exchange(getPointOfPlayer(), northPoint);
    }

}
