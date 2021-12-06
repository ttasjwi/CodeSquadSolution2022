public class Stage {

    private String stageName; // 스테이지명
    private Integer[][] stageMap; // 스테이지 맵

    public static final Integer WALL_CODE = 0; // 벽
    public static final Integer HALL_CODE = 1; // 구멍
    public static final Integer BALL_CODE = 2; // 공
    public static final Integer PLAYER_CODE = 3; // 플레이어
    public static final Integer STAGE_DELIM_CODE = 4; // 스테이지 구분

    public static final char WALL_SYMBOL = '#';
    public static final char HALL_SYMBOL = 'O';
    public static final char BALL_SYMBOL = 'o';
    public static final char PLAYER_SYMBOL = 'P';
    public static final char STAGE_DELIM_SYMBOL = '=';
    public static final char SPACE_SYMBOL = ' ';

    public Stage(String stageName, Integer[][] stageMap) {
        this.stageName = stageName;
        this.stageMap = stageMap;
    }

    //Integer을 읽고 문자로 형식화한다.
    public char getMapSymbol(Integer mapObjectCode) {
        if (mapObjectCode==null) return SPACE_SYMBOL;
        switch (mapObjectCode) {
            case 0 : return WALL_SYMBOL;
            case 1 : return HALL_SYMBOL;
            case 2 : return BALL_SYMBOL;
            case 3 : return PLAYER_SYMBOL;
            case 4 : return STAGE_DELIM_SYMBOL;
        }
        return SPACE_SYMBOL;
    }

    //stageMap의 각 인덱스의 값 문자로 형식화하고, 문자열로 반환
    public String getMapString() {
        StringBuilder sb = new StringBuilder();

        for (int i=0; i<stageMap.length-1; i++) { //마지막 줄은 제외한다.
            for (int j=0; j<stageMap[i].length; j++) {
                sb.append(getMapSymbol(stageMap[i][j]));
            }
            sb.append('\n');
        }
        return sb.toString();
    }

}
