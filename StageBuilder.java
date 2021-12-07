import java.util.*;

public class StageBuilder {

    public static String getStageName(String stageText) {
        Scanner srcScanner = new Scanner(stageText);
        String stageName = srcScanner.nextLine();
        srcScanner.close();
        return stageName;
    }

    public static MapObject[][] buildStageMap(Stage stage, String stageText) {
        List<String> stageMapSrc = makeStageMapSrc(stageText);
        MapObject[][] stageMap = makeStageMap(stage, stageMapSrc);
        return stageMap;
    }

    private static List<String> makeStageMapSrc(String stageText) {
        Scanner srcScanner = new Scanner(stageText);
        srcScanner.nextLine(); // 첫번째 라인은 버린다.
        List<String> stageMapSrc = new ArrayList<>();
        while(srcScanner.hasNextLine()) {
            stageMapSrc.add(srcScanner.nextLine()); // 나머지 라인은 List<String>에 라인별로저장
        }
        srcScanner.close();
        return stageMapSrc;
    }

    //빈 스테이지 맵을 반환한다.
    private static MapObject[][] makeEmptyStageMap(Stage stage, List<String> stageMapSrc) {
        int height = calHeight(stageMapSrc);
        int width = calWidth(stageMapSrc);

        MapObject[][] emptyStageMap = new MapObject[height][width];
        for (int i=0; i<emptyStageMap.length; i++) {
            for (int j=0; j<emptyStageMap[i].length; j++) {
                Point point = new Point(j,i);
                emptyStageMap[i][j] = new Space(stage, point);
            }
        }
        return emptyStageMap;
    }

    // 만들 스테이지 맵의 가로크기를 구하여 반환한다.
    private static int calWidth(List<String> stageMapSrc) {
        int maxLength = stageMapSrc.get(0).length(); // 라인별 문자열의 길이의 최댓값
        for (String stageMapLine : stageMapSrc) {
            int currentLineLength = stageMapLine.length();
            if (maxLength < currentLineLength) {
                maxLength = currentLineLength;
            }
        }
        return maxLength;
    }

    // 만들 스테이지 맵의 세로크기를 구하여 반환한다.
    private static int calHeight(List<String> stageMapSrc) {
        int width = stageMapSrc.size()+1; // 스테이지 구분용으로 한 줄 더 넣어야함.
        return width;
    }

    // 스테이지의 오브젝트들을 담을 이차원배열, StageMap을 생성한다.
    private static MapObject[][] makeStageMap(Stage stage, List<String> stageMapSrc) {
        MapObject[][] stageMap = makeEmptyStageMap(stage, stageMapSrc);
        for (int i=0; i< stageMap.length; i++) {
            fillMapObject(stage, stageMapSrc, stageMap, i); // i번째 요소를 기반으로 i행(가로줄)을 채운다.
        }
        return stageMap;
    }

    // 행 단위로(라인), stageMap의 각 인덱스에 맵오브젝트를 채운다.
    private static void fillMapObject(Stage stage, List<String> stageMapSrc, MapObject[][] stageMap, int rowNumber) {
        if (rowNumber == stageMap.length-1) { // 마지막 행은 STAGE_DELIM_CODE로 채운다.
            for (int j=0; j<stageMap[rowNumber].length; j++) {
                Point point = new Point(j,rowNumber);
                MapObject mapObject = new  StageDelim(stage, point);
                stageMap[rowNumber][j] = mapObject;
            }
            return;
        }

        String line = stageMapSrc.get(rowNumber); // 해당 행에 대응하는 stageMapSrc의 라인
        char[] chars = line.toCharArray(); // 문자의 배열로 쪼갠다.
        for (int j=0; j<chars.length; j++) {
            Point point = new Point(j,rowNumber);
            MapObject mapObject = MapObject.getInstance(stage, point,  chars[j]); // 문자별로 맵 오브젝트 코드값으로 변환한다.
            stageMap[rowNumber][j] = mapObject; // 채운다.
        }
        return;
    }

}

