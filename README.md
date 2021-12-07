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

## 3단계
