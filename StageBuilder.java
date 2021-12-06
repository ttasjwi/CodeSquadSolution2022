import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class StageBuilder {

    private static final Integer WALL_CODE = 0; // 벽
    private static final Integer HALL_CODE = 1; // 구멍
    private static final Integer BALL_CODE = 2; // 공
    private static final Integer PLAYER_CODE = 3; // 플레이어
    private static final Integer STAGE_DELIM_CODE = 4; // 스테이지 구분

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
    private Integer[][] makeEmptyStageMap() {
        int height = this.calHeight();
        int width = this.calWidth();
        Integer[][] emptyStageMap = new Integer[height][width];
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
        Integer[][] stageMap = makeStageMap();
        return new Stage(this.stageName, stageMap);
    }

    // 스테이지의 오브젝트들을 담을 이차원배열, StageMap을 생성한다.
    private Integer[][] makeStageMap() {
        Integer[][] stageMap = makeEmptyStageMap();
        for (int i=0; i< stageMap.length; i++) {
            fillMapObject(stageMap, i); // i번째 요소를 기반으로 i행(가로줄)을 채운다.
        }
        return stageMap;
    }

    // 행 단위로(라인), stageMap의 각 인덱스에 맵오브젝트를 채운다.
    private void fillMapObject(Integer[][] stageMap, int rowNumber) {
        if (rowNumber == stageMap.length-1) { // 마지막 행은 STAGE_DELIM_CODE로 채운다.
            Arrays.fill(stageMap[rowNumber], STAGE_DELIM_CODE);
            return;
        }

        String line = stageMapSrc.get(rowNumber); // 해당 행에 대응하는 stageMapSrc의 라인
        char[] chars = line.toCharArray(); // 문자의 배열로 쪼갠다.
        for (int j=0; j<chars.length; j++) {
            Integer mapObjectCode = parseMapObject(chars[j]); // 문자별로 맵 오브젝트 코드값으로 변환한다.
            stageMap[rowNumber][j] = mapObjectCode; // 채운다.
        }
        return;
    }

    // 문자를 인자로 맵의 오브젝트 값으로 반환한다.
    private Integer parseMapObject(char ch) {
        switch(ch) {
            case '#' : return WALL_CODE;
            case 'O' : return HALL_CODE;
            case 'o' : return BALL_CODE;
            case 'P' : return PLAYER_CODE;
            default: return null;
        }
    }

}

