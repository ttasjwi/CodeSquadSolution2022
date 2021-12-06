import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

}

