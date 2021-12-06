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

## 2단계

## 3단계
