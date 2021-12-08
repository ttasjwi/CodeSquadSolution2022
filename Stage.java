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
        if (player.hasHall()) countOfHall ++;
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

    // 지정 좌표에 위치한 맵 오브젝트를 반환한다.
    public MapObject getMapObject(Point p) {
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

    // 플레이어를 동쪽(오른쪽)으로 이동시킨다.
    public void movePlayerToEast() {
        Point playerPoint = player.getPoint();
        Point eastPoint = playerPoint.getEastPoint();
        MapObject eastObject = getMapObject(eastPoint);
        if (isSpace(eastObject)) {
            movePlayerToSpace(eastPoint);
            System.out.println("D : 플레이어를 오른쪽으로 이동합니다.");
            printStageMap();
            return;
        }

        if (isHall(eastObject)) {
            movePlayerToHall(eastPoint);
            System.out.println("D : 플레이어를 오른쪽으로 이동합니다.");
            printStageMap();
            return;
        }
        System.out.println("D : (경고!) 해당 명령을 수행할 수 없습니다!");
        printStageMap();
        return;
    }

    // 플레이어를 Space로 이동시킨다.
    private boolean movePlayerToSpace(Point spacePoint) {
        MapObject arrivalObject = getMapObject(spacePoint);
        if (!isSpace(arrivalObject)) {
            return false;
        }
        Player player = splitPlayerFromMap();
        this.currentMap[spacePoint.getY()][spacePoint.getX()] = player; // Space의 인덱스를 Player에게 계승시킨다.
        player.moveTo(spacePoint); // Space의 좌표를 player에게 계승시킨다.
        return true;
    }

    // 플레이어를 Hall로 이동시킨다.
    private boolean movePlayerToHall(Point hallPoint) {
        MapObject arrivalObject = getMapObject(hallPoint); // 도착 지점의 MapObject
        if (!isHall(arrivalObject)) return false; // Hall이 아니면 false 반환

        Hall arrivalHall = (Hall) arrivalObject; // arrivalObject를 hall로 형변환
        Player player = splitPlayerFromMap(); // 플레이어를 Map에서 분리시킨다.
        player.addHall(arrivalHall); // 플레이어에게 오른쪽 hall을 추가시킨다.
        this.currentMap[hallPoint.getY()][hallPoint.getX()] = player; // 플레이어의 맵에서의 위치를 오른쪽으로 이동시킴.
        player.moveTo(hallPoint); // 플레이어의 왼쪽
        return true;
    }

    // Map에서 플레이어를 분리시킨다.
    private Player splitPlayerFromMap() {
        Player player = this.player;

        if (player.hasHall()) {
            Hall wasOwnedHall = player.removeHall(); // 플레이어에게서 Hall을 제거
            this.currentMap[player.getPoint().getY()][player.getPoint().getX()] = wasOwnedHall; // 소지했던 hall을 맵에 놓는다.
            player.moveTo(null);
            return player;
        }

        Space space = new Space(this, player.getPoint());
        this.currentMap[player.getPoint().getY()][player.getPoint().getX()] = space;
        player.moveTo(null);
        return player;
    }

    // 플레이어를 남쪽(아래)으로 이동시킨다.
    public void movePlayerToSouth() {
        Point playerPoint = player.getPoint();
        Point southPoint = playerPoint.getSouthPoint();
        MapObject southObject = getMapObject(southPoint);

        if (isSpace(southObject)) {
            System.out.println("S : 플레이어를 아래로 이동합니다.");
            movePlayerToSpace(southPoint);
            printStageMap();
            return;
        }
        if (isHall(southObject)) {
            System.out.println("S : 플레이어를 아래로 이동합니다.");
            movePlayerToHall(southPoint);
            printStageMap();
            return;
        }
        System.out.println("S : (경고!) 해당 명령을 수행할 수 없습니다!");
        printStageMap();
        return;
    }

    // 플레이어를 서쪽(왼쪽)으로 이동시킨다.
    public void movePlayerToWest() {
        Point playerPoint = player.getPoint();
        Point westPoint = playerPoint.getWestPoint();
        MapObject westObject = getMapObject(westPoint);

        if (isSpace(westObject)) {
            System.out.println("A : 플레이어를 왼쪽으로 이동합니다.");
            movePlayerToSpace(westPoint);
            printStageMap();
            return;
        }
        if (isHall(westObject)) {
            System.out.println("A : 플레이어를 왼쪽으로 이동합니다.");
            movePlayerToHall(westPoint);
            printStageMap();
            return;
        }
        System.out.println("A : (경고!) 해당 명령을 수행할 수 없습니다!");
        printStageMap();
        return;
    }

    // 플레이어를 북쪽(위)으로 이동시킨다.
    public void movePlayerToNorth() {
        Point playerPoint = player.getPoint();
        Point northPoint = playerPoint.getNorthPoint();
        MapObject northObject = getMapObject(northPoint);

        if (isSpace(northObject)) { // 이동하려는 곳이 공백이면
            System.out.println("W : 플레이어를 위로 이동합니다.");
            movePlayerToSpace(northPoint);
            printStageMap();
            return;
        }
        if (isHall(northObject)) {
            System.out.println("W : 플레이어를 위로 이동합니다.");
            movePlayerToHall(northPoint);
            printStageMap();
            return;
        }
        System.out.println("W : (경고!) 해당 명령을 수행할 수 없습니다!");
        printStageMap();
        return;
    }

}
