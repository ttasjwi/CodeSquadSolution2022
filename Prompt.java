import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Prompt {

    private static final String MAP_FILE_DIR = "Map.txt";
    private static final File MAP_FILE = new File(MAP_FILE_DIR);
    private static final List<Stage> stages = initStages();

    //소스파일에서 스테이지단위로 텍스트를 분리한다.
    private static List<String> splitStageStr(File mapFile) {
        List<String> stageStrSplits = new ArrayList<>();
        try {
            Scanner fileScanner = new Scanner(mapFile).useDelimiter("=+"); // 파일을 읽어와서, 문자열 1개 이상의 "="을 구분자로 하여 쪼개는 Scanner 생성
            while(fileScanner.hasNext()) {
                String stageStr = fileScanner.next().trim(); // "==="앞을 읽어오고 양끝의 공백(개행문자, 공백)을 제거한다.
                stageStrSplits.add(stageStr); // List에 추가한다.
            }
        } catch (FileNotFoundException e) {
            System.out.println("파일 읽기 실패");
            System.exit(0);
        }
        return stageStrSplits; // List를 반환한다.
    }

    // List<Stage> 초기화
    private static List<Stage> initStages() {
        List<String> stageStrSplits = splitStageStr(MAP_FILE);
        List<Stage> stages = new ArrayList<>();

        for(String stageSrc : stageStrSplits) {
            StageBuilder stageBuilder = new StageBuilder(stageSrc);
            Stage stage = stageBuilder.build();
            stages.add(stage);
        }
        return stages;
    }

    // 실행
    public void run() {
        printStages();
    }

    //TODO : 모든 스테이지들을 출력한다.
    private void printStages() {
        System.out.println("프로그램 실행 확인");
        System.out.println("할 것 : 모든 스테이지를 출력할 것");
    }

}
