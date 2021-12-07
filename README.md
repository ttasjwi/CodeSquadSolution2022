# 구현과정 상세 설명

## 문제를 받은 소감
- Gist가 생소하다.
- 문제 자체를 보니 정신이 나갈 것 같다. 으아아악

## 1단계

---

### \[1.02 revision] 맵 가져오기
- 최상위 디렉토리의 `Map.txt`파일을 읽어와 File객체 `MAP_FILE` 생성
- 이를 static 상수로 한다.

---

### \[1.03 revision] 텍스트파일의 내용을 스테이지단위로 쪼개는 메서드 생성
- 소스파일의 텍스트를 스테이지단위로 분리하여 List에 나눠 저장하도록 하는 메서드 `splitStageStr`생성
  - 스캐너의 `.useDelimiter("=+")` 을 활용함
  - 나눠진 문자열들의 양 끝의 공백을 `.trim()` 메서드를 이용하여 제거 -> 마지막의 구분자가 제거되고 남은 개행문자도 제거됨.

---

### \[1.04 revision] Stage 클래스 정의  

각 Stage를 정의한 Stage 클래스 정의  
- 멤버
  1. String `stageName` : 스테이지명
  2. Integer[][] `stageMap` : 맵

---

### \[1.05 revision]  Point 클래스 정의

- 어떤 좌표를 정의한 Point 클래스 정의
- 멤버
  1. int x;
  2. int y;
- 메서드
  - equals : 같은 x,y 좌표면 true, 다르면 false 반환
  - hashCode : x,y 좌표를 기준으로 hashCode를 반환하도록 함
  - toString : (x, y)의 형태로 좌표를 문자열로 반환

---

### \[1.06 revision] StageBuilder 클래스 정의

- revision 1.03에서 만들어진 `splitStageStr`을 통해 분리된, 스테이지 단위의 문자열을 읽고, 이를 통해 스테이지를 만드는 용도로 정의한 클래스
- 멤버
  - String `stageName` : 만들고자 하는 stage의 이름
  - List\<Sring> `stageMapSrc` : 스테이지 맵 문자열을 라인단위로 쪼갬
- 생성자
  - 스테이지 문자열을 읽고, 첫번째 줄은 `stageName`, 나머지 줄은 `stageMapSrc`에 라인단위로 저장
- 구현해야할 것
  - 스테이지 문자열들을 분석하여, `Stage`를 만드는 것

---

### \[1.07 revision] 만들 StageMap의 가로 길이를 계산하는 메서드 calWidth 정의
- 멤버 `stageMapSrc`를 읽어, 각 라인 문자열의 길이들의 최댓값을 반환

---

### \[1.08 revision] 만들 StageMap의 세로 길이를 계산하는 메서드 calHeight 정의
- 멤버 `stageMapSrc`를 읽어 size()값에 1을 더한 값이 맵의 세로길이
  - 맵의 마지막 줄에 스테이지 구분값을 채워야함. 따라서 한 줄이 더 필요

---

### \[1.09 revision] 빈 이차원 배열을 생성하는 `makeEmptyStageMap()` 메서드 정의
- `calHeight`로 `calWidth`로 세로, 가로를 구한 뒤, 이를 각각 열, 행의 갯수로 한 Integer[][] 이차원 배열 생성
- 초기화를 해주지 않았기 때문에 모든 값은 어떤 `Integer` 객체도 참조하지 않고 있다.
- 이 이차원 배열은 Stage의 Map으로 사용될 것이다.

---

### \[1.10 revision] 문자를 읽고, 스테이지맵의 오브젝트에 대한 숫자값을 반환하는 메서드 정의
- 문자를 인자로, 스테이지 맵의 오브젝트 값으로 파싱하는 메서드 parseMapObject(char ch) 정의
  - 문자를 인자로 이를 Map의 오브젝트에 대응하는 Integer로 반환
- Map 오브젝트 값이 헷갈릴 수 있으므로, 이를 static 상수화함.
  - `WALL_CODE` == 0, `HALL_CODE` == 1, `BALL_CODE` == 2, `PLAYER_CODE` == 3 `STAGE_DELIM_CODE`==4;

---

### \[1.11 version] 스테이지 생성 메서드 `build` 정의
- `build()` 메서드 : Stage를 생성하는 메서드
  - 멤버변수 `stageName`을 읽어오고, stageMap을 만들어 이를 토대로 Stage를 생성한다.
- `makeStageMap()` 메서드 : stageMap을 생성하는 메서드
  - `makeEmptyStageMap()`을 통해 빈 StageMap을 생성하고 fill StageMap을 생성한다.
- `fillMapObject(Integer[][] stageMap, int rowNumber)` 메서드 : 행단위로 행에 다응하는 `stageMapSrc`의 인덱스의 라인을 읽어와, 각 라인을 문자단위로 파싱하고 StageMap을 채운다.

---

### \[1.12 version] 스테이지 맵의 오브젝트 코드를 Stage의 static 상수로 변경
- 기존 StageBuilder에서 정의되었던 static 상수였으나, 범용성을 위해 Stage의 static 상수로 함.

---

### \[1.13 version] Integer을 읽고 문자로 변환하는 메서드 정의
- ` getMapSymbol(Integer mapObjectCode)` : 오브젝트 코드(숫자)를 인자로 문자로 형식화한다.

---

### \[1.14 version] `getMapString()` - stageMap의 각 인덱스의 값을 문자로 형식화하고, 문자열로 반환
- stageMap의 각 인덱스를 읽고 그 값을 `getMapSymbol` 메서드를 통해 각각 형식화하여, stageMap 전체를 문자 형식화한다.
- 형식화된 문자를 반환한다.

---

### \[1.15 version] `getNmbOf~()` 메서드 - stageMap에서 지정 오브젝트의 갯수 반환 
- `getNmbOf(Integer mapObjectCode)` : 지정 오브젝트 코드(숫자)에 대응하는 오브젝트의 갯수를 반환
- `getNmbOfBall()` : 공의 갯수 반환
- `getNmbOfHall()` : 구멍의 갯수 반환

---

### \[1.16 version] 맵의 가로폭, 세로폭 반환 메서드
- `getWidth()` : 맵의 가로폭
- `getHeight()` : 맵의 세로폭

---

### \[1.17 revision] `getStageName()` - 스테이지명 반환
- `getStageName()` : 스테이지명 반환

---

### \[1.18 revision] `getPointOfPlayer()` : 플레이어의 위치 `Point` 반환
- `getPointOfPlayer()` : 플레이어 위치 `Point` 반환
- `isPlayer(Integer mapObjectCode)` : 지정 

---

### \[1.19 revision] Main 클래스 - Prompt 클래스 분리
- Main - `main` 메서드 : Prompt 객체 생성, run() 메서드 호출
- Prompt : 실행 내용, 현재 구체적으로 실행 내용구현은 안 됨

---

### \[1.20 revision] Prompt 클래스 - `List<Stage>` 생성 및 초기화
- List<Stage> `stages` : 로딩된 Stage들의 리스트
- List<Stage> `initStages()` : 파일을 읽어와, `stages`를 초기화

### \[1.21 revision] Prompt 클래스 - 모든 스테이지의 내용을 출력
1. 작업상황
   - `printStage(Stage stage)` : 지정 스테이지의 세부사항을 출력
     - 스테이지명
     - 스테이지맵 화면 출력
     - 가로크기, 세로크기
     - 구멍의 수, 공의 수
     - 플레이어 위치
   - `run()` : `stages`의 모든 stage에 대하여  `printStage(Stage stage)`  수행
2. 동작
   - Main 클래스 실행시 run() 메서드를 호출하여, 스테이지의 모든 세부사항을 출력하도록함
3. 결론 : **1단계 완료**

---

## 2단계
### \[2.01 revision] Stage 클래스 - reset() 메서드 생성
1. 작업상황
- `stageMap`을 `originalMap`과 `currentMap` 두가지로 나눔
  - `originalMap`은 Stage 생성시점의 원본 맵
  - `currentMap`은 현재 맵. 플레이어의 조작에 따라 currentMap이 변화하도록 할 것이다.
- `reset()` : `originalMap`을 복제하여 `currentMap`을 초기화함.
  - 깊은 복사를 위하여 내부 MapObject Code 인 Integer 값들을 새로 생성했다.

2. 동작
   - 실행시 콘솔에 보이는 내용은 이전 revision과 별 차이 없음. 실행 시 모든 stage의 세부사항을 출력함.

---

### \[2.02 revision] MapObject 인터페이스 정의, 모든 맵오브젝트 객체화
1. 작업상황
- Stage 맵에 존재하는 모든 오브젝트를 Integer로 관리하다보니 불편함을 느껴서, 모든 맵 오브젝트를 Integer 대신, MapObject 인터페이스의 구현체로 객체화했다.
- 이에 따라 기존에 Integer로 맵의 오브젝트를 다루던 클래스들을 전면적으로 개편하지 않을 수 없었고 이번 커밋은 커밋 단위가 매우 커져버렸다.
  - 공백을 `Space`로 추가 정의했다.
  - MapObject 인터페이스는 `getSymbol()` 메서드를 추상메서드로 하였고, 이를 구현해야 인스턴스를 생성할 수 있다.
  - MapObject 인터페이스는 static 메서드 getInstance(char symbol)를 통해, 문자를 통하여 symbol에 대응하는 구현체 인스턴스를 반환한다. 
  - StageBuilder 클래스의 parseMapObject 클래스는 MapObject의 getInstace() 메서드가 그 역할을 수행하도록 하고, 제거했다.

2. 동작
  - 실행시 콘솔에 보이는 내용은 이전 revision과 별 차이 없음. 실행 시 모든 stage의 세부사항을 출력함.

---

### \[2.03 revision] 지정 인덱스에 위치한 `MapObject`를 반환하는 메서드 생성
1. 작업상황
   - 지정 인덱스에 위치한 MapObject를 반환하는 메서드 `getMapObject`를 정의

2. 동작
   - 실행시 콘솔에 보이는 내용은 이전 revision과 별 차이 없음. 실행 시 모든 stage의 세부사항을 출력함.

---

### \[2.04 revision] 지정 Point에 대응하는 인덱스에 위치한 `MapObject`를 반환하는 메서드 생성
1. 작업상황
  - 지정 Point에 대응하는 인덱스에 위치한 MapObject를 반환하는 메서드 `getMapObject`를 정의.

2. 동작
  - 실행시 콘솔에 보이는 내용은 이전 revision과 별 차이 없음. 실행 시 모든 stage의 세부사항을 출력함.

---

### \[2.05 revision] 실행 시 Stage2의 상태가 보이도록 함

1.작업상황
- Prompt 클래스의 `run()`메서드 호출 시, Stage2의 스테이지명을 출력하고, 현재 상태를 출력하도록 함.
  - 기존의 `printStage()` 메서드 제거
  - `printStageName` : 스테이지명 출력
  - `printCurrentStatus` : 스테이지의 현재 상태를 출력(맵, 가로크기, 세로크기, 구멍수, 공수, 플레이어 위치)

2. 동작
- 실행 시 Stage2의 스테이지명이 출력되고, 현재 스테이지 상태를 출력하면서 종료된다.

---

### \[2.06 revision] 지정좌표의 동서남북 좌표를 반환하는 메서드 각각 생성

1.작업상황
- Point 클래스에서 `getEastPoint()`, `getSouthPoint()`, `getWestPoint()`, `getNorthPoint()` 메서드 정의
  - 각각 Point 기준 동쪽, 남쪽, 서쪽, 북쪽 좌표를 반환함
- Prompt 클래스의 `printCurrentStatus` 메서드가, 플레이어 기준 동남서북의 좌표를 출력하도록 함.

2. 동작
- 실행 시 Stage2의 스테이지명이 출력되고, 현재 스테이지 상태를 출력하면서 종료된다.
  - 추가내용 : 플레이어의 동남서북 좌표도 출력

---

### \[2.07 revision] `getMapObject(Point p)` 메서드 오류 수정

1. 작업상황
   - `getMapObject(Point p)` 메서드는 `getMapObject(int i, int j)` 메서드를 참조하는데, 이때 i,j에 각각 y, 좌표를 넘겨야했던 것을 x, y 좌표로 넘기는 문제가 있었음. 이를 정정함.
   - `README.md`에서 2.07 revision의 오타 수정(동서남푹-> 동서남북)
2. 동작
   - 실행시 콘솔에 보이는 내용은 이전 revision과 별 차이 없음. 실행 시 Stage의 스테이지명이 출력되고, 현재 스테이지 상태를 출력하면서 종료된다.

---

### \[2.08 revision] `isSpace()` 메서드 추가

1. 작업상황
   - `isSpace(MapObject mo)` 메서드 : 지정 MapObject가 빈 공간인지 여부를 반환
2. 동작
   - 실행시 콘솔에 보이는 내용은 이전 revision과 별 차이 없음. 실행 시 Stage의 스테이지명이 출력되고, 현재 스테이지 상태를 출력하면서 종료된다.

---

### \[2.09 revision] `MapObject` 인터페이스에 추상메서드 `isMovable` 추가

1. 작업상황
    - `isMovable()` : 움직일 수 있는 지 여부를 반환
    - 구현체인 Wall, Hall, Ball, Player, Space, StageDelim 에서 이를 구현하도록 함ㅁ.
2. 동작
    - 실행시 콘솔에 보이는 내용은 이전 revision과 별 차이 없음. 실행 시 Stage의 스테이지명이 출력되고, 현재 스테이지 상태를 출력하면서 종료된다.

---

### \[2.10 revision] `getPointOfPlayer()` 오류 수정

1. 작업상황
   - `getPointOfPlayer()` 메서드의 심각한 오류 수정
   - : 행-열을 y-x로 바꿔야했는데 x-y로 해서 Point를 생성했었음. **여태까지 출력했던 플레이어 위치는 x,y가 뒤바뀐 것이였다!**
       ```
           // 플레이어의 좌표 반환
           public Point getPointOfPlayer() {
               for (int i=0; i<currentMap.length; i++) {
                   for (int j=0; j<currentMap[i].length; j++) {
                       MapObject mapObject = currentMap[i][j];
                       if (isPlayer(mapObject)) return new Point(j,i); // i,j 순으로 작성해버렸는데 j,i였다.
                   }
               }
               return null;
           }
       ```

---


2. 동작
    - 실행시 콘솔에 보이는 내용은 이전과 달라졌을 것임. 플레이어의 x,y좌표가 바뀌었을 것이다. (정정)
    
---

### \[2.11 revision] `exchange(Point p1, Point p2)` 메서드 생성
1. 작업상황 
   - `exchange(Point p1, Point p2)` : p1과 p2에 위치한 MapObject를 자리바꿈한다.
     - 두 MapObject 어느 한쪽이라도 `isMovable()` 메서드가 false를 반환하면 이동할 수 없다.
2. 동작
    - 실행시 콘솔에 보이는 내용은 이전 revision과 별 차이 없음. 실행 시 Stage의 스테이지명이 출력되고, 현재 스테이지 상태를 출력하면서 종료된다.

---

### \[2.12 revision] `movePlayerTo~()` 메서드 생성
1. 작업상황
    - 플레이어를 동쪽,남쪽,서쪽,북쪽으로 이동시키는 메서드 `movePlayerToEast`, `movePlayerToSouth`, `movePlayerToWest`, `movePlayerToNorth` 생성\
    - Prompt의 `run()` 메서드에서, 위의 메서드를 한번씩 호출하고 그 결과를 맵의 상태를 매번 출력하게 함.
2. 동작
    - 실행시, 스테이지명이 뜨고 플레이어의 현재 상태가 출력된다.
    - 동쪽, 남쪽, 서쪽, 북쪽 순서대로 플레이어가 이동을 시도하고, 이동할 때마다 맵 상태가 출력된다.

---

### \[2.13 revision] `printStageMap()` 메서드 생성
1. 작업상황
    - 스테이지의 맵을 출력하는 메서드를 Stage에서 정의함.
    - Prompt에서 `getMapString()` 메서드를 이용해 출력했었으나, 이 메서드가 추가되어서 더 이상 사용할 필요가 없어져, printStageMap() 메서드로 대체
    - `getMapString()` 메서드의 접근제어자를 private로 변경
2. 동작
    - 실행시 콘솔에 보이는 내용은 이전 revision과 별 차이 없음. 실행 시 Stage의 스테이지명이 출력되고, 동쪽, 남쪽, 서쪽, 북쪽 순서대로 플레이어가 이동을 시도한 뒤, 이동할 때마다 맵 상태가 출력된다.

---

### \[2.14 revision] `Prompt` 클래스의 불필요한 메서드 제거
1. 작업상황
    - 스테이지의 이름을 출력하는 printStageName() 메서드는 더 이상 사용하지 않음.
    - 스테이지의 상태를 출력하는 메서드 `printCurrentStatus()` 메서드는 더 이상 사용하지 않음. 제거.
    - 이동하였을 때 맵이 어떻게 변했는지만 출력하도록 함.
2. 동작
    - 실행 시 스테이지 명이 바로 뜨고, 맵이 출력된다.
    - 맵이 출력되고 동, 남, 서, 북으로 각각 한번씩 이동을 시도한다.
    - 이동 시도 이후 맵의 변화를 출력한다.

---

### \[2.15 revision] `movePlayerTo~()`메서드가 이동 후 Map 출력의 기능도 수행하도록 함.
1. 작업상황
    - `exchange(Point p1, Point p2)` 메서드는 두 좌표의 객체 교환 시도 및 true, false 반환만 하도록 함.
    - `movePlayerTo~()` 메서드는 `exchange~`메서드에서 성공, 실패 여부를 화면에 출력하도록 함.
    - `movePlayerTo~()` 메서드는 플레이어의 이동 시도 결과 맵의 상태를 출력하는 역할도 하도록 함.
    - 이에 따라, Prompt에서 별도로 이동 후 `printStageMap` 메서드를 호출할 필요가 없어짐.
2. 동작
    - 실행 시 스테이지 명이 바로 뜨고, 맵이 출력된다.
    - 맵이 출력되고 동, 남, 서, 북으로 각각 한번씩 이동을 시도한다.
    - 이동 시도 이후 맵의 변화를 출력한다.

---

### \[2.16 revision] Prompt의 `run()`메서드 이름을 `execute`로 변경

1. 작업상황
    - Prompt의 `run()` 메서드의 메서드명을 `execute`로 변경

2. 동작 (변경 없음)
    - 실행 시 스테이지 명이 바로 뜨고, 맵이 출력된다.
    - 맵이 출력되고 동, 남, 서, 북으로 각각 한번씩 이동을 시도한다.
    - 이동 시도 이후 맵의 변화를 출력한다.

---

### \[2.17 revision] `initCommandMap(Stage stage)` 메서드 추가

1. 작업상황 
   - 멤버변수 `isPlaying` : 초기값은 `true`, 종료 명령이 들어왔을 때 `false`로 변한다. 무한 반복문을 종료하기위한 용도로 사용.
   - `initCommandMap(Stage stage)` : stage를 기반으로 하여, 수행할 작업들을 모아둔 `Map<String, Runnable>`을 생성하는 메서드
     - key값 : 으로 W,A,S,D,Q와 같은 입력값
     - value값 : W,A,S, D에 대해서는 stage의 플레이어 이동을 수행하는 Runnable을 저장, Q에 대해서는 현재 Prompt의 `isRunnable`을 `false`로 변경하도록 함.
   - `execute` 메서드
     - commandMap 생성 : `initCommandMap` 메서드를 통해 `stage`의 commandMap 생성
     - Queue<String>에 명령어 D,S,A,W,Q,S를 순차적으로 삽입
     - while문에서, queue에서 poll한 값들로 commandQueue에서 value인 Runnable을 찾도록 한다.
     - 각각 Runnable의 작업을 수행하도록 한다.
2. 동작
    - 실행 시 스테이지 명이 바로 뜨고, 맵이 출력된다.
    - stage의 command맵이 생성된다.
    - 맵이 출력되고 D, S, A, F, Q, S 문자열을 Queue에 삽입한다.
    - 반복문을 통해 queue의 작업이 순차적으로 출력된다.
      - while문 도중에 Q 명령에 의해 `isPlaying`이 false가 되고 반복문을 탈출하여 프로그램이 종료된다.
---

## 3단계
