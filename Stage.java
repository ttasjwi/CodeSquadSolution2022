public class Stage {

    private String stageName; // 스테이지명
    private MapObject[][] originalMap; // 스테이지 맵
    private MapObject[][] currentMap; // 현재 맵
    private Player player; // 플레이어

    // 스테이지를 초기화한다.
    public void reset() {
        this.currentMap = copyOriginalMap();
        System.out.println("R : 스테이지를 초기화합니다.");
        printStageMap();
        return;
    }

    // 스테이지를 생성해서 반환한다.
    public static Stage build(String stageText) {
        Stage stage = new Stage();
        String stageName = StageBuilder.getStageName(stageText);
        MapObject[][] stageMap = StageBuilder.buildStageMap(stage, stageText);
        stage.init(stageName, stageMap);
        return stage;
    }

    // 스테이지를 초기화한다.
    private void init(String stageName, MapObject[][] stageMap) {
        this.stageName = stageName;
        this.originalMap = stageMap;
        this.currentMap = copyOriginalMap();
        this.player = (Player) getMapObject(getPointOfPlayer());
    }

    // Original Map을 복사해서 반환한다.
    private MapObject[][] copyOriginalMap() {
        MapObject[][] copyMap = new MapObject[originalMap.length][originalMap[0].length];
        for (int i=0; i<originalMap.length; i++) {
            for (int j=0; j<originalMap[i].length; j++) {
                Point point = new Point(j,i);
                char symbol = originalMap[i][j].getSymbol();
                copyMap[i][j] = MapObject.getInstance(this, point, symbol);
            }
        }
        return copyMap;
    }

    //stageMap의 각 인덱스의 값 문자로 형식화하고, 문자열로 반환
    private String getMapString() {
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

    // 스테이지를 출력한다.
    public void printStageMap() {
        System.out.println(getMapString());
        return;
    }

    // p1과 p2에 위치한 객체를 자리바꿈한다.
    private boolean exchange(Point p1, Point p2) {
        if (p1 == p2) return false;
        MapObject mo1 = getMapObject(p1);
        MapObject mo2 = getMapObject(p2);

        if (!(mo1.isMovable()&&mo2.isMovable())) {
            return false;
        }
        currentMap[p1.getY()][p1.getX()] = mo2;
        currentMap[p2.getY()][p2.getX()] = mo1;
        return true;
    }

    // 플레이어를 동쪽(오른쪽)으로 이동시킨다.
    public void movePlayerToEast() {
        Point eastPoint = getPointOfPlayer().getEastPoint();
        boolean exchange = exchange(getPointOfPlayer(), eastPoint);
        System.out.println((exchange)? "D : 오른쪽으로 이동합니다." : "D : (경고!) 해당 명령을 수행할 수 없습니다!");
        printStageMap();
        return;
    }

    // 플레이어를 남쪽(아래)으로 이동시킨다.
    public void movePlayerToSouth() {
        Point southPoint = getPointOfPlayer().getSouthPoint();
        boolean exchange = exchange(getPointOfPlayer(), southPoint);
        System.out.println((exchange)? "S : 아래로 이동합니다." : "S : (경고!) 해당 명령을 수행할 수 없습니다!");
        printStageMap();
        return;
    }

    // 플레이어를 서쪽(왼쪽)으로 이동시킨다.
    public void movePlayerToWest() {
        Point westPoint = getPointOfPlayer().getWestPoint();
        boolean exchange = exchange(getPointOfPlayer(), westPoint);
        System.out.println((exchange)? "A : 왼쪽으로 이동합니다." : "A : (경고!) 해당 명령을 수행할 수 없습니다!");
        printStageMap();
        return;
    }

    // 플레이어를 북쪽(위)으로 이동시킨다.
    public void movePlayerToNorth() {
        Point northPoint = getPointOfPlayer().getNorthPoint();
        boolean exchange = exchange(getPointOfPlayer(), northPoint);
        System.out.println((exchange)? "W : 위로 이동합니다." : "W : (경고!) 해당 명령을 수행할 수 없습니다!");
        printStageMap();
        return;
    }

}
