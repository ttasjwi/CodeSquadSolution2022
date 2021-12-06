public class Stage {

    private String stageName; // 스테이지명
    private Integer[][] stageMap; // 스테이지 맵

    public static final Integer WALL_CODE = 0; // 벽
    public static final Integer HALL_CODE = 1; // 구멍
    public static final Integer BALL_CODE = 2; // 공
    public static final Integer PLAYER_CODE = 3; // 플레이어
    public static final Integer STAGE_DELIM_CODE = 4; // 스테이지 구분

    public Stage(String stageName, Integer[][] stageMap) {
        this.stageName = stageName;
        this.stageMap = stageMap;
    }

}
