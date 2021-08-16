<p align="center" >
<img src="https://user-images.githubusercontent.com/48541984/129441829-aa5bef86-94d7-4728-84fd-c8fa069649e0.png" width="200" height="200" align="center"/>
</p>
<h1 align="center">
  Clone-Coupangeats </br> 
    <img alt="kotlin" src="https://img.shields.io/badge/kotlin-1.5-blueviolet.svg">
    <img alt="Android-Studio" src="https://img.shields.io/badge/AndroidStudio-6.0-brightgreen.svg">
</h1>

## 테스트 기간
2021.08.14 ~ 08.27

## 테스트 [규칙](https://softsquared.notion.site/6c2c0054572945f69423af661eea9158)
* 일일단위 작업 기록해주세요. (1일 1커밋 & 1 개발일지)  
* 일지는 **작업 진행상황**에 대해 작성해주세요. ([예시 보러가기](https://softsquared.notion.site/2d0cef8721e34c10acf70646e776038c))
  * 기획서의 변동사항
  * 기획서 상 계획이 몇퍼센트 완료되었는지
  * 현재 어떤 화면 혹은 기능을 구현중에 있는지
  * 클라이언트 개발자 or 서버 개발자와의 회의에 따른 회의록
  * 개발팀장님의 피드백(1차, 2차)
  * 개발 도중에 발생하는 이슈
* 개발 도중에 생기는 **이슈**에 대해서 모두 작성해주세요.
  * 어떤 점이 어려웠는지,
  * 어떤 점에서 이슈가 발생했는지,
  * 어떻게 해결을 시도하였는지,
  * 해결이 되었는지

## 테스트 역할분담
| 서버 | 클라이언트 |
| :---: | :-----------: |
| Coogie / 정재민 | &#160;&#160;&#160;Sally / 채윤원&#160;&#160;&#160; |

## 테스트 기획서
#### 구글 DOCS [`보러가기` 👆](https://docs.google.com/document/d/1f3Ua5d-3wBIh7YIModxf8_8H15YEf-Swgd18dXQDDhE/edit?usp=sharing)

## 테스트 개발이슈
#### Notion 쿠팡이츠 개발이슈와 구현일정 [`보러가기` 🤘](https://www.notion.so/uunwon/bc814b591c6a4bd594941c0e26cb53ff)
<img src="https://user-images.githubusercontent.com/48541984/129486402-3a21b120-b4b0-4006-baed-a012ccd31247.png" width="60%"/>

## 테스트 진행현황
### 1주차 진행현황 🌈
![Progress](https://progress-bar.dev/45/?scale=100&width=500&suffix=%)  
### 2021-08-14
* 기획서 제출 - 100%  
  * 개발 범위 / 개발 우선 순위  
  * 개발할 기능 및 화면 캡쳐 / 1주차까지 작업해올 범위  
  * 안드로이드 버전 화면 캡쳐  
* 제공받은 안드로이드 템플릿 숙지
* mvp 패턴 적용하여 프로젝트 세팅
* SplashActivity 생성
* MainActivity 내 Bottom Navigation Bar 생성

### 2021-08-15
* 앱 로고 이미지 변경
* FavoriteActivity , OrderFragment 생성
* OrderFragment 내 TabLayout 구현
* FavoriteActivity 생성
* HomeFragment 내 광고 이미지 ViewFlipper 구현
* 로그인/회원가입 창 BottomSheetDialogFragment 구현
* LoginActivity 구현
* JoinActivity 구현
  * (.!!추가할 부분) 비밀번호 화면 표시 이모지 처리

### 2021-08-16
* LoginActivity 내 BottomLoginErrorDialog 활용해 예외 처리 
* JoinActivity 내 각 컬럼 예외처리, 정규식 추가
* 서버 Retrofit 통신
  * 로그인 API : 실패 시 LoginErrorDialog 출력
  * 회원가입 API : 성공 시 생성한 유저 index 값 출력
* HomeFragment 내 가로형 RecyclerView 생성
  * 카테고리 클릭 시 해당 position 값 다음 액티비티에 전달

## 테스트 개발환경
* AWS EC2 / RDS
* Node.js
* Android Studio
