import java.util.Map;
import java.util.Stack;

public class Stage {

    private String stageName; // 스테이지명
    private MapObject[][] originalMap; // 원본 맵
    private MapObject[][] currentMap; // 현재 맵
    private Stack<MapObject[][]> beforeStack = new Stack<>(); // 이전 턴들을 Stack으로 쌓음.
    private Stack<MapObject[][]> afterStack = new Stack<>(); // 돌악가기를 시도할 때마다 현재 턴의 데이터를 AfterTurn에 쌓는다.

    // 스테이지를 초기화한다.
    public void reset() {
        System.out.println("R : 스테이지를 초기화합니다.");
        this.currentMap = copyMap(originalMap);
        this.beforeStack.clear();
        this.afterStack.clear();
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

    // 생성한 스테이지를 초기화한다.
    private void init(String stageName, MapObject[][] stageMap) {
        this.stageName = stageName;
        this.originalMap = stageMap;
        this.currentMap = copyMap(originalMap);
    }

    // stage Map을 깊은 복사해서 반환한다.
    private MapObject[][] copyMap(MapObject[][] stageMap) {
        MapObject[][] copyMap = new MapObject[stageMap.length][stageMap[0].length];
        for (int i=0; i<stageMap.length; i++) {
            for (int j=0; j<stageMap[i].length; j++) {
                Point point = new Point(j,i);
                char symbol = stageMap[i][j].getSymbol();
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

    //StageMap에서 구멍에 들어가진 공의 갯수를 반환
    public int getNmbOfInputtedBall() {
        int countOfInputtedBall = 0;
        for (int i=0; i<currentMap.length; i++) {
            for (int j=0; j<currentMap[i].length; j++) {
                countOfInputtedBall = InputtedBallCount(countOfInputtedBall, currentMap[i][j]); // hall이 있는 ball이면 countOfBall을 증가시켜서 반환
            }
        }
        return countOfInputtedBall;
    }

    // 지정 MapObject가 구멍을 가진 Ball이면 매개변수 int값을 증가시켜서 반환
    private int InputtedBallCount(int countOfInputtedBall, MapObject mo) {
        if (isBall(mo)) {
            Ball ball = (Ball)mo;
            countOfInputtedBall += (ball.hasHall())? 1 : 0;
        }
        return countOfInputtedBall;
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

    private Player getPlayer() {
        return (Player) getMapObject(getPointOfPlayer());
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

    // 지정 맵 오브젝트가 Passable 맵 오브젝트인지 여부를 반환
    private boolean isPassable(MapObject mo) {
        return (mo instanceof Passable);
    }

    // 스테이지를 출력한다.
    public void printStageMap() {
        System.out.println(getMapString());
        return;
    }

    private boolean pushBall(Point fromBallPoint, Point arrivalPoint) {
        MapObject fromObject = getMapObject(fromBallPoint);
        if (!isBall(fromObject)) return false;
        Ball fromBall = (Ball) fromObject;
        MapObject arrivalObject = getMapObject(arrivalPoint);
        if (isSpace(arrivalObject)) {
            moveBallToSpace(fromBall, arrivalPoint);
            return true;
        }
        if (isHall(arrivalObject)) {
            moveBallToHall(fromBall, arrivalPoint);
            return true;
        }
        return false;
    }

    private void moveBallToHall(Ball fromBall, Point arrivalPoint) {
        splitBallFromMap(fromBall);

        Hall arrivalHall = (Hall) getMapObject(arrivalPoint);
        fromBall.addHall(arrivalHall);

        currentMap[arrivalPoint.getY()][arrivalPoint.getX()] = fromBall;
        fromBall.moveTo(arrivalPoint);
    }

    private void moveBallToSpace(Ball fromBall, Point arrivalPoint) {
        splitBallFromMap(fromBall);
        currentMap[arrivalPoint.getY()][arrivalPoint.getX()] = fromBall;
        fromBall.moveTo(arrivalPoint);
    }

    //Movable을 스테이지에서 분리
    private Movable splitMovableFromMap(Movable movable) {
        if (movable.hasHall()) {
            Hall wasOwnedHall = movable.removeHall(); // 플레이어에게서 Hall을 제거
            this.currentMap[movable.getPoint().getY()][movable.getPoint().getX()] = wasOwnedHall; // 소지했던 hall을 맵에 놓는다.
            movable.moveTo(null);
            return movable;
        }
        Space space = new Space(this, movable.getPoint());
        this.currentMap[movable.getPoint().getY()][movable.getPoint().getX()] = space; // Space의 인덱스를 Movable에게 계승시킨다.
        movable.moveTo(null);
        return movable;
    }

    // Map에서 지정 Ball을 분리시킨다.
    private Ball splitBallFromMap(Ball ball) {
        return (Ball)splitMovableFromMap(ball);
    }

    // Map에서 플레이어를 분리시킨다.
    private Player splitPlayerFromMap() {
        Player player = getPlayer();
        return (Player)splitMovableFromMap(player);
    }

    // 플레이어를 Passable이 위치한 자리로 이동시킨다.
    private boolean movePlayerToPassable(Point spacePoint) {
        MapObject arrivalObject = getMapObject(spacePoint);
        if (!isPassable(arrivalObject)) {
            return false;
        }
        Player player = splitPlayerFromMap(); // 플레이어를 Map에서 분리시킨다.

        if (isHall(arrivalObject)) {
            Hall arrivalHall = (Hall) arrivalObject; // arrivalObject를 hall로 형변환
            player.addHall(arrivalHall); // 플레이어에게 오른쪽 hall을 추가시킨다.
        }

        this.currentMap[spacePoint.getY()][spacePoint.getX()] = player;
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

    // 플레이어를 동쪽(오른쪽)으로 이동시킨다.
    public boolean movePlayerToEast() {
        Point playerPoint = getPointOfPlayer();
        Point eastPoint = playerPoint.getEastPoint();
        MapObject eastObject = getMapObject(eastPoint);
        if (isPassable(eastObject)) {
            recordBeforeTurn();
            System.out.println("D : 플레이어를 오른쪽으로 이동합니다.");
            movePlayerToPassable(eastPoint);
            printStageMap();
            return true;
        }
        if (isBall(eastObject)) {
            Point eastOfEastPoint = eastObject.getPoint().getEastPoint();
            boolean pushSuccess = pushBall(eastPoint, eastOfEastPoint);
            if (pushSuccess) return movePlayerToEast();
        }
        System.out.println("D : (경고!) 해당 명령을 수행할 수 없습니다!");
        printStageMap();
        return false;
    }


    // 플레이어를 남쪽(아래)으로 이동시킨다.
    public boolean movePlayerToSouth() {
        Point playerPoint = getPointOfPlayer();
        Point southPoint = playerPoint.getSouthPoint();
        MapObject southObject = getMapObject(southPoint);
        if (isPassable(southObject)) {
            recordBeforeTurn();
            System.out.println("S : 플레이어를 아래로 이동합니다.");
            movePlayerToPassable(southPoint);
            printStageMap();
            return true;
        }
        if (isBall(southObject)) {
            Point southOfSouthPoint = southObject.getPoint().getSouthPoint();
            boolean pushSuccess = pushBall(southPoint, southOfSouthPoint);
            if (pushSuccess) return movePlayerToSouth();
        }
        System.out.println("S : (경고!) 해당 명령을 수행할 수 없습니다!");
        printStageMap();
        return false;
    }

    // 플레이어를 서쪽(왼쪽)으로 이동시킨다.
    public boolean movePlayerToWest() {
        Point playerPoint = getPointOfPlayer();
        Point westPoint = playerPoint.getWestPoint();
        MapObject westObject = getMapObject(westPoint);
        if (isPassable(westObject)) {
            recordBeforeTurn();
            System.out.println("A : 플레이어를 왼쪽으로 이동합니다.");
            movePlayerToPassable(westPoint);
            printStageMap();
            return true;
        }
        if (isBall(westObject)) {
            Point westOfWestPoint = westObject.getPoint().getWestPoint();
            boolean pushSuccess = pushBall(westPoint, westOfWestPoint);
            if (pushSuccess) return movePlayerToWest();
        }
        System.out.println("A : (경고!) 해당 명령을 수행할 수 없습니다!");
        printStageMap();
        return false;
    }

    // 플레이어를 북쪽(위)으로 이동시킨다.
    public boolean movePlayerToNorth() {
        Point playerPoint = getPointOfPlayer();
        Point northPoint = playerPoint.getNorthPoint();
        MapObject northObject = getMapObject(northPoint);
        if (isPassable(northObject)) {
            recordBeforeTurn();
            System.out.println("W : 플레이어를 위로 이동합니다.");
            movePlayerToPassable(northPoint);
            printStageMap();
            return true;
        }
        if (isBall(northObject)) {
            Point northOfNorthPoint = northObject.getPoint().getNorthPoint();
            boolean pushSuccess = pushBall(northPoint, northOfNorthPoint);
            if (pushSuccess) return movePlayerToNorth();
        }
        System.out.println("W : (경고!) 해당 명령을 수행할 수 없습니다!");
        printStageMap();
        return false;
    }

    public boolean isCleared() {
        return getNmbOfBall() == getNmbOfInputtedBall();
    }

    public int getCurrentTurn() {
        return this.beforeStack.size();
    }

    // 변화 직전, beforeStack에 Map상태를 push(저장)한다.
    private void recordBeforeTurn() {
        MapObject[][] beforeMap = copyMap(currentMap); // 깊은 복사(내부 객체까지 새로 복사)를 해야한다.
        beforeStack.push(beforeMap); // 현재 맵을 beforeStack에 push한다.
        if (!afterStack.empty()) { // afterStack(본래 세계선을 과거에 박제)이 비어있지 않다 == 되돌아간 상황 ==> 별도의 행동을 한다 ==> 미래가 바뀜. 두 세계선이 갈라짐.
            afterStack.clear();
        }
    }

    // 이전 턴으로 돌아간다.
    public boolean redo() {
        if (beforeStack.empty()) {
            System.out.println("u : (경고!) 해당 명령을 수행할 수 없습니다. 더 이상 돌아갈 수 없습니다.");
            printStageMap();
            return false;
        }
        System.out.println("u : 이전 턴으로 돌아갑니다.");
        afterStack.push(currentMap); // 현재 턴의 데이터를 AfterStack에 쌓는다.
        MapObject[][] beforeMap = beforeStack.pop(); // beforeStack에서 이전 턴의 데이터를 가져온다.
        this.currentMap = beforeMap; // 이전 턴을 currentMap으로 한다.
        printStageMap();
        return true;
    }

    // 이전 턴으로 돌아갔던 것을 취소한다. (앞으로 돌아가기)
    public boolean cancelRedo() {
        if (afterStack.empty()) {
            System.out.println("u : (경고!) 해당 명령을 수행할 수 없습니다. 현재 되돌아가지 않은 상황입니다.");
            printStageMap();
            return false;
        }
        System.out.println("U : 다음 턴으로 다시 돌아갑니다. (실행 취소)");
        beforeStack.push(currentMap); // 현재 턴의 데이터를 다시 beforeStack에 쌓는다.
        MapObject[][] afterMap = afterStack.pop(); // afterStack에서 다음 턴의 데이터를 다시 가져온다.
        this.currentMap = afterMap;
        printStageMap();
        return true;
    }

}
