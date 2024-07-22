<h1 align="center">🚑<strong> findER - Refactoring </strong>🚑</h1>

<div align="center">
  <strong>2023년 제17회 공개 SW 개발자대회</strong>
  <br><br>
  <em>실시간 응급실 정보 제공 모바일 앱, findER(Emergency Room)</em>
  <br><br>
  <em>written by wingunkh & gretea5</em>
</div>

<div align="center">
  <h3>
    <!--
    <a href="https://malalove.notion.site/API-2f5e86d852ca4f73b2e66c21b8a31e3d?pvs=4">
      📜 REST API 명세서
    </a>
    <span> | </span>
    -->
    <a href="https://github.com/gretea5/findER">
      🖼️ Frontend
    </a>
  </h3>
</div>

<br>

## 📑 목차
- ✍🏻 **프로젝트 개요**
- ⭐ **주요 기능**
- 📚 **기술 스택**
- 🔗 **API 문서**
- 🗺️ **시스템 구조도**
- 👩‍👩‍👧‍👦 **팀원 및 담당 파트**
- 🛠️ **리팩터링 히스토리**

<br>

## ✍🏻 프로젝트 개요
응급실 병상이 없어서 환자가 떠돌다가 숨지는, 이른바 **'응급실 뺑뺑이'** 현상이 사회적 이슈로 부각되고 있습니다.
<br>
이에 저희 팀은 이러한 사회적 문제를 해결하는 데 일조하고자 **실시간 잔여 병상 수를 포함한 다양한 응급실 관련 정보를 제공하는 모바일 앱**을 개발하였습니다.
<br>
<br>
📰 [<ins>'응급실 뺑뺑이' 잇단 사망‥왜 반복되나</ins> (MBC 뉴스)](https://imnews.imbc.com/replay/2023/nwtoday/article/6488902_36207.html)

<br>

## ⭐ 주요 기능

### 응급실 위치 확인 기능
<img width="300" height="600" src="https://github.com/wingunkh/findER-Refactoring/assets/58140360/f3221479-39f3-403d-83d1-4a1067896c7b">

Kakao Map API 지도에서 응급실 위치를 확인할 수 있습니다. <br>
또한, Kakao Local API를 통해 검색 기능을 제공합니다.

<br>

### 응급실 프리뷰 기능
<img width="300" height="600" src="https://github.com/wingunkh/findER-Refactoring/assets/58140360/2dc5d9e4-8cd8-429a-ae2e-642125e28ec3">

국립중앙의료원 API를 통해 실시간 응급실 잔여 병상 수를 제공합니다. (1분 간격 갱신) <br>
또한, Kakao Mobility API를 통해 이동 거리와 예상 이동 소요 시간을 제공합니다.

<br>

### 응급실 상세보기 기능
<img width="300" height="600" src="https://github.com/wingunkh/findER-Refactoring/assets/58140360/f58e2489-443a-4c8f-80a5-3679bbe6f573">

구급차, CT, MRI 가용 여부와 진료 과목 등 프리뷰에서 제공하지 않는 응급실 정보를 추가적으로 제공합니다.

<br>

### 길 찾기 기능
<img width="300" height="600" src="https://github.com/wingunkh/findER-Refactoring/assets/58140360/3ed94866-fc5a-47f6-a54e-2d51a02bd721">

응급실 상세보기에서 버튼을 클릭해 카카오 맵 길 찾기 기능을 사용할 수 있습니다.

<br>

### 문진표 CRUD 기능
<img width="300" height="600" src="https://github.com/wingunkh/findER-Refactoring/assets/58140360/49c91fee-2cd2-4d5d-b10b-5bd709c8fa30">
<img width="300" height="600" src="https://github.com/wingunkh/findER-Refactoring/assets/58140360/2ff6ccdc-20e9-40e2-b6ba-93887e51a3e4">

응급 상황에 유용하게 사용될 수 있도록 문진표 관련 기능을 제공합니다.

<br>

### 사용자 간 문진표 공유 기능
<img width="300" height="600" src="https://github.com/wingunkh/findER-Refactoring/assets/58140360/c192b75d-4d4a-4cc7-8dd9-69086d3272d1">
<img width="300" height="600" src="https://github.com/wingunkh/findER-Refactoring/assets/58140360/792c881d-811f-4313-b36e-e8b4c594ee9a">

다른 사용자의 시리얼 번호를 통해 공유 관계를 설정하고, 문진표를 공유할 수 있습니다.

<br>

## 📚 기술 스택
<div>
    <table>
        <tr>
            <td colspan="2" align="center">
                Language
            </td>
            <td colspan="4">
                <img src="https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white"> 
                <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=openjdk&logoColor=white">
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                Framework
            </td>
            <td colspan="4">
                <img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
                <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                API
            </td>
            <td colspan="4">
                <img src="https://img.shields.io/badge/DATA.go.kr-00529B?style=for-the-badge&logo=D&logoColor=white"> 
                <img src="https://img.shields.io/badge/Kakao Map-FFCD00?style=for-the-badge&logo=kakao&logoColor=black"> 
                <img src="https://img.shields.io/badge/Kakao Mobility-FFCD00?style=for-the-badge&logo=kakao&logoColor=black">
                <img src="https://img.shields.io/badge/Kakao Local-FFCD00?style=for-the-badge&logo=kakao&logoColor=black"> 
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                Server
            </td>
            <td colspan="4">
                <img src="https://img.shields.io/badge/amazon ec2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white"> 
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                Database
            </td>
            <td colspan="4">
                <img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white">
                <img src="https://img.shields.io/badge/oracle-F80000?style=for-the-badge&logo=oracle&logoColor=white">
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                Tool
            </td>
            <td colspan="4">
                <img src="https://img.shields.io/badge/Android Studio-3DDC84?style=for-the-badge&logo=androidstudio&logoColor=white">
                <img src="https://img.shields.io/badge/IntelliJ IDEA-000000?style=for-the-badge&logo=intellijidea&logoColor=white">
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                etc.
            </td>
            <td colspan="4">
                <img src="https://img.shields.io/badge/postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white">
                <img src="https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=notion&logoColor=white">
            </td>
        </tr>
    </table>
</div>

<br>

## 🔗 API 문서

🔗[<ins>Notion</ins>](https://finder-api.notion.site/9964c0217c97499ab30a76baa1f660e8?v=fb3bc95f4e53471ea71213b282109a83&pvs=4)

<br>

## 🗺️ 시스템 구조도

<img width="600" height="360" src="https://github.com/wingunkh/findER-Refactoring/assets/58140360/8580f70c-7bee-40a5-ae9e-9c0196064ede"> <br>

<br>

## 👩‍👩‍👧‍👦 팀원 및 담당 파트

<table>
  <tr>
    <td align="center">Front-End</td>
    <td align="center">Back-End</td>
  </tr>
  
  <tr>
    <td align="center">
        <img src="https://avatars.githubusercontent.com/u/120379834?v=4" width="120px;" alt="박장훈"/>
    </td>
    <td align="center">
        <img src="https://avatars.githubusercontent.com/u/58140360?v=4" width="120px" alt="김현근"/>
    </td>
  </tr>

  <tr>
    <td align="center">
      <a href="https://github.com/gretea5">박장훈</a>
    </td>
    <td align="center">
      <a href="https://github.com/wingunkh">김현근</a>
    </td>
  </tr>

  <tr>
    <td align="center" width="50%">Android Mobile Application 개발 (Kotlin)</td>
    <td align="center" width="50%">Spring Web Application 개발 (Java)</td>
  </tr>
</table>

<br>

## 🛠️ 리팩터링 히스토리

> 리팩터링은 2023년 9월 기준 <br>
제가 담당한 기능 (문진표 CRUD, 문진표 공유, 응급실 실시간 잔여 병상 수 갱신, 서버 구축, 데이터베이스 구축 등)을 포함하여 <br>
다른 팀원이 담당한 기능 (로그인, 응급실 정보 저장, 카카오 모빌리티 API 활용 등)을 모두 포함합니다.

##### 1. 로그인 방식 변경 (2024-02-22)
<details>
  <summary>자세히 보기</summary> <br>
  - 전 : Spring Security <br>
  - 후 : SHA-256 + Salt <br>
  - 이유 : 실제 서비스 여부가 결정되지 않았으로, 우선 로그인 방식을 간단한 방법으로 유지하기 위함
</details>

##### 2. 비즈니스 로직 위치 변경 (2024-03-15)
<details>
  <summary>자세히 보기</summary> <br>
  - 전 : 비즈니스 로직이 Repository 계층에 네이티브 쿼리로서 존재 <br>
  - 후 : Spring Data JPA Query Method를 활용하여 네이티브 쿼리 제거, 비즈니스 로직을 Service 계층으로 이동 <br>
  - 개선 : Spring Web Application 계층 책임을 더욱 명확하게 분리
</details>

##### 3. 문진표 공유 로직 변경 (2024-03-15)
<details>
  <summary>자세히 보기</summary> <br>
  - 전 : 제한 시간 내 두 사용자가 서로에게 공유 요청 전송 <br>
  - 후 : 사용자에게 고유한 시리얼 번호 부여, 이를 바탕으로 공유 요청 전송 <br>
  - 개선 : 클라이언트 요청 횟수 감소
</details>

##### 4. 응급실 정보 저장 로직 변경 (2024-03-29)
<details>
  <summary>자세히 보기</summary> <br>
  - 전 : 응급의료기관 정보 조회 API를 호출 후 응급실 정보만 별도 추출 <br>
  - 후 : 응급실 실시간 잔여 병상 수 조회 API를 통해 응급실 식별자 추출, 이를 바탕으로 응급실에 대해서만 응급의료기관 정보 조회 API 호출 <br>
  - 개선 : 속도 향상 (전 약 200,000ms → 후 약 65,000ms)
</details>

##### 5. 응급실 병상 수 변동 추이 그래프 제거 (2024-04-17)
<details>
  <summary>자세히 보기</summary> <br>
  - 전 : 실시간 잔여 병상 수 DB에 400 (응급실 = 약 400개) * 1440 (하루 = 1440분) = 약 600,000개의 엔티티 유지 <br>
  - 후 : 실시간 잔여 병상 수 DB에 400개의 엔티티 유지 (더 이상 1분 간격으로 엔티티가 누적되지 않고 갱신됨) <br>
  - 이유 : 사용자 관점에서 유의미한 정보가 아니라고 판단 <br>
  - 개선 : 유지해야 하는 엔티티의 수 대폭 감소
</details>

##### 6. 외부 API 활용 클래스 추상화 (2024-05-05)
<details>
  <summary>자세히 보기</summary> <br>
  - 전 : 국립중앙의료원 API 활용 클래스와 Kakao Mobility API 활용 클래스가 독립적 <br>
  - 후 : 추상 클래스로 일반화, 각 클래스가 추상 클래스의 메서드를 오버라이드 <br>
  - 개선 : 코드 중복 제거, 유지보수성 향상
</details>

##### 7. 응급실 도메인에 진료과목 추가 (2024-05-06)
<details>
  <summary>자세히 보기</summary> <br>
  - 예 : 가정의학과, 구강내과, 내과 ... <br>
  - 개선 : 사용자 관점에서 유의미한 정보라고 판단 <br>
</details>

##### 8. 응급실 위치 정보 응답 로직 변경 (2024-05-20)
<details>
  <summary>자세히 보기</summary> <br>
  - 전 : 사용자가 Kakao Map을 드래그할 때마다 범위 내 모든 응급실에 대한 위치 정보 응답 <br>
  - 후 : Kakao Map 화면 이동 시 (or 사용자 로그인 시) 모든 응급실에 대한 위치 정보 응답, 프론트엔드 측에서 ShraedPreference를 통해 유지 <br>
  - 개선 : 클라이언트 요청 횟수 감소
</details>

##### 9. Spring Boot 버전 업데이트 (2024-05-24)
<details>
  <summary>자세히 보기</summary> <br>
  - 전 : SpringBootVersion = 2.7.1 <br>
  - 후 : SpringBootVersion = 3.0.0
</details>

##### 10. ResponseEntity 결정 계층 변경 (2024-05-29)
<details>
  <summary>자세히 보기</summary> <br>
  - 전 : Service 계층 <br>
  - 후 : Controller 계층 <br>
  - 이유 : Service 계층은 비즈니스 로직을 처리하고, Controller 계층이 웹 관련 요청에 대한 응답을 처리하도록 하기 위해 <br>
  - 개선 : Spring Web Application 계층 책임을 더욱 명확하게 분리
</details>

##### 11. HTTP Status Code 개선 (2024-05-29)
<details>
  <summary>자세히 보기</summary> <br>
  - 전 : 200 (OK), 404 (Not Found)만 사용 <br>
  - 후 : 201 (Created), 204 (No Content), 409 (Conflict) 등 추가
</details>

##### 12. 예외 처리 로직 수정 (2024-06-09)
<details>
  <summary>자세히 보기</summary> <br>
  - 전 : Checked Exception <br>
  - 후 : Unchecked Exception <br>
  - 개선 : 가독성 향상, 유지보수성 향상
</details>

##### 13. @RestControllerAdvice 사용 전역 예외 처리 구현 (2024-06-19)
<details>
  <summary>자세히 보기</summary> <br>
  - 전 : 각 클래스에서 예외 처리 <br>
  - 후 : @RestControllerAdvice 클래스에서 전역적으로 예외 처리 <br>
  - 개선 : 가독성 향상, 유지보수성 향상
</details>

##### 14. @Aspect 사용 로깅에 AOP 적용 (2024-07-10)
<details>
  <summary>자세히 보기</summary> <br>
  - 전 : 각 클래스에서 로깅 <br>
  - 후 : @Aspect 클래스를 통해 공통 관심 사항 (로깅) 분리 <br>
  - 개선 : 가독성 향상, 유지보수성 향상
</details>
