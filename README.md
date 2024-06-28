<h1 align="center">🚑<strong> findER - Refactoring </strong>🚑</h1>

<div align="center">
  <strong>2023년 제17회 공개 SW 개발자대회</strong>
  <br><br>
  <em>실시간 응급실 정보 제공 애플리케이션, findER(Emergency Room)</em>
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
- 🛠️ **리팩터링**

<br>

## ✍🏻 프로젝트 개요
응급실 병상이 없어서 환자가 떠돌다가 숨지는, 이른바 **'응급실 뺑뺑이'** 현상이 사회적 이슈로 부각되고 있습니다.
<br>
이에 저희 팀은 이러한 사회적 문제를 해결하는 데 일조하고자 **실시간 잔여 병상 수를 포함한 다양한 응급실 관련 정보를 제공하는 애플리케이션**을 개발하였습니다.
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

### 사용자 간 문진표 연동 기능
<img width="300" height="600" src="https://github.com/wingunkh/findER-Refactoring/assets/58140360/c192b75d-4d4a-4cc7-8dd9-69086d3272d1">
<img width="300" height="600" src="https://github.com/wingunkh/findER-Refactoring/assets/58140360/792c881d-811f-4313-b36e-e8b4c594ee9a">

다른 사용자의 시리얼 번호를 통해 연동 관계를 설정하고, 문진표를 공유할 수 있습니다.

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

<!-- ## 🛠️ 리팩터링
blank
-->
