import java.util.*;

public class StageBuilder {

    private String stageName; // 스테이지명
    private List<String> stageMapSrc; // 스테이지 맵에 대한 라인별 문자열

    // 생성자 : 문자열 src를 읽어오고 라인단위로 쪼갠뒤, 스테이지명과, stageMap의 소스로 구분한다.
    public StageBuilder (String stageText) {
        Scanner srcScanner = new Scanner(stageText);
        this.stageName = srcScanner.nextLine(); // 첫번째 라인은 Stage 명으로 설정

        List<String> stageMapSrc = new ArrayList<>();
        while(srcScanner.hasNextLine()) {
            stageMapSrc.add(srcScanner.nextLine()); // 나머지 라인은 List<String>에 라인별로저장
        }
        srcScanner.close();
        this.stageMapSrc = stageMapSrc;
    }

    //빈 스테이지 맵을 반환한다.
    private MapObject[][] makeEmptyStageMap() {
        int height = this.calHeight();
        int width = this.calWidth();

        MapObject[][] emptyStageMap = new MapObject[height][width];
        for (int i=0; i<emptyStageMap.length; i++) {
            for (int j=0; j<emptyStageMap[i].length; j++) {
                emptyStageMap[i][j] = new Space();
            }
        }
        return emptyStageMap;
    }

    // 만들 스테이지 맵의 가로크기를 구하여 반환한다.
    private int calWidth() {
        int maxLength = this.stageMapSrc.get(0).length(); // 라인별 문자열의 길이의 최댓값
        for (String stageMapLine : this.stageMapSrc) {
            int currentLineLength = stageMapLine.length();
            if (maxLength < currentLineLength) {
                maxLength = currentLineLength;
            }
        }
        return maxLength;
    }

    // 만들 스테이지 맵의 세로크기를 구하여 반환한다.
    private int calHeight() {
        int width =  this.stageMapSrc.size()+1; // 스테이지 구분용으로 한 줄 더 넣어야함.
        return width;
    }

    // Stage를 생성해서 반환한다.
    public Stage build() {
        MapObject[][] stageMap = makeStageMap();
        return new Stage(this.stageName, stageMap);
    }

    // 스테이지의 오브젝트들을 담을 이차원배열, StageMap을 생성한다.
    private MapObject[][] makeStageMap() {
        MapObject[][] stageMap = makeEmptyStageMap();
        for (int i=0; i< stageMap.length; i++) {
            fillMapObject(stageMap, i); // i번째 요소를 기반으로 i행(가로줄)을 채운다.
        }
        return stageMap;
    }

    // 행 단위로(라인), stageMap의 각 인덱스에 맵오브젝트를 채운다.
    private void fillMapObject(MapObject[][] stageMap, int rowNumber) {
        if (rowNumber == stageMap.length-1) { // 마지막 행은 STAGE_DELIM_CODE로 채운다.
            Arrays.fill(stageMap[rowNumber], new StageDelim());
            return;
        }

        String line = stageMapSrc.get(rowNumber); // 해당 행에 대응하는 stageMapSrc의 라인
        char[] chars = line.toCharArray(); // 문자의 배열로 쪼갠다.
        for (int j=0; j<chars.length; j++) {
            MapObject mapObject = MapObject.getInstance(chars[j]); // 문자별로 맵 오브젝트 코드값으로 변환한다.
            stageMap[rowNumber][j] = mapObject; // 채운다.
        }
        return;
    }

}

