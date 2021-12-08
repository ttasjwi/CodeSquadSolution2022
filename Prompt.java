import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Prompt {

    private static final String MAP_FILE_DIR = "Map.txt";
    private static final File MAP_FILE = new File(MAP_FILE_DIR);
    private static final String PROMPT = "SOKOBAN > ";
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
        System.out.println("\n소코반의 세계에 당도한 것을 환영하오. 낯을 환영하오, 낯선이여~");
        System.out.println("즐겁게 놀다가시오~\n");
        List<String> stageStrSplits = splitStageStr(MAP_FILE);
        List<Stage> stages = new ArrayList<>();

        for(String stageSrc : stageStrSplits) {
            Stage stage = Stage.build(stageSrc);
            stages.add(stage);
        }
        return stages;
    }

    // 실행
    public void execute() {
        List<Stage> stages = initStages();
        Scanner sc = new Scanner(System.in);
        for (Stage stage : stages) {
            playStage(stage, sc);
            if (!isPlaying) break;
        }
        if (isPlaying) System.out.println("전체 게임을 클리어하셨습니다!\n축하드립니다!");
        sc.close();
        return;
    }

    // 실제로 각 스테이지를 사용자가 플레이하는 부분의 메서드
    private void playStage(Stage stage, Scanner sc) {
        System.out.println(stage.getStageName()+'\n');
        stage.printStageMap();
        while(isPlaying) {
            System.out.print(PROMPT);
            runCommandQueue(stage, inputCommandQueue(sc));
            if (stage.isCleared()) {
                System.out.println("빠밤! "+stage.getStageName() +" 클리어! ");
                System.out.println("턴수 : "+stage.getCurrentTurn()+"\n");
                break;
            }
        }
        return;
    }

    // stage에 대하여 지정 Queue의 내용을 순차적으로 수행하고, 수행이 종료되면 종료하기까지 소요된 turn을 반환한다.
    private void runCommandQueue(Stage stage, Queue<String> commandQueue) {
        Map<String, Runnable> commandMap = initCommandMap(stage);
        while(isPlaying&& commandQueue.size()>0) {
            String commandKey = commandQueue.poll();
            Runnable command = commandMap.get(commandKey);
            if (command == null) {
                System.out.println(commandKey+" : (경고!) 해당 명령을 수행할 수 없습니다!");
                stage.printStageMap();
                continue;
            }
            command.run();
        }
        return;
    }

    // 지정 stage에 대하여 수행할 작업들을 저장할, commandMap을 생성하여 반환함.
    private Map<String, Runnable> initCommandMap(Stage stage) {
        Map<String, Runnable> commandMap = new HashMap<>();
        commandMap.put("Q", () -> isPlaying=false);
        commandMap.put("q", () -> isPlaying=false);
        commandMap.put("W", () -> stage.movePlayerToNorth());
        commandMap.put("w", () -> stage.movePlayerToNorth());
        commandMap.put("A", () -> stage.movePlayerToWest());
        commandMap.put("a", () -> stage.movePlayerToWest());
        commandMap.put("S", () -> stage.movePlayerToSouth());
        commandMap.put("s", () -> stage.movePlayerToSouth());
        commandMap.put("D", () -> stage.movePlayerToEast());
        commandMap.put("d", () -> stage.movePlayerToEast());
        commandMap.put("u", () -> stage.redo());
        commandMap.put("R", () -> stage.reset());
        commandMap.put("r", () -> stage.reset());
        return commandMap;
    }

    // 사용자로부터 명령을 라인단위로 입력받는다.
    private Queue<String> inputCommandQueue(Scanner sc) {
        String inputLine = sc.nextLine().trim();
        Queue cmdQueue = new LinkedList(Arrays.asList(inputLine.split("")));
        return cmdQueue;
    }

}
