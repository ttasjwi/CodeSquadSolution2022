import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Prompt {

    private static final String MAP_FILE_DIR = "Map.txt";
    private static final File MAP_FILE = new File(MAP_FILE_DIR);
    private static final List<Stage> stages = initStages();
    private boolean isPlaying = true;

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
    public void execute() {
        Stage stage2 = stages.get(1);
        System.out.println(stage2.getStageName());

        Map<String, Runnable> commandMap = initCommandMap(stage2);
        Queue<String> queue = new LinkedList<>();
        queue.add("D");
        queue.add("S");
        queue.add("A");
        queue.add("W");
        queue.add("Q");
        queue.add("S");

        while (isPlaying && queue.size()>0) {
            String command = queue.poll();
            Runnable runnable = commandMap.get(command);
            runnable.run();
        }
        System.out.println("bye~");
    }

    // 지정 stage에 대하여 수행할 작업들을 저장할, commandMap을 생성하여 반환함.
    private Map<String, Runnable> initCommandMap(Stage stage) {
        Map<String, Runnable> commandMap = new HashMap<>();
        commandMap.put("Q", () -> isPlaying=false);
        commandMap.put("W", () -> stage.movePlayerToNorth());
        commandMap.put("A", () -> stage.movePlayerToWest());
        commandMap.put("S", () -> stage.movePlayerToSouth());
        commandMap.put("D", () -> stage.movePlayerToEast());
        return commandMap;
    }

}
