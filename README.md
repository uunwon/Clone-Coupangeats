</br>
<p align="center">
<img src="https://user-images.githubusercontent.com/48541984/131235545-fe074a5f-d222-4d6f-a4e2-781c5a6039b4.jpg" height="130" align="center"/>
</p>
<h1 align="center">
  Clone-Coupangeats </br> 
    <img alt="Ubuntu" src="https://img.shields.io/badge/Ubuntu-18.04-important.svg">
    <img alt="Nodejs" src="https://img.shields.io/badge/Nodejs-14.15.3-success.svg">
    <img alt="MySQL" src="https://img.shields.io/badge/MySQL-8.0.x-informational.svg">
    <img alt="AndroidStudio" src="https://img.shields.io/badge/AndroidStudio-4.2-brightgreen.svg">
</h1>

## 개발 기간
2021.08.14 ~ 08.27

## 개발 역할분담 <img src="https://img.shields.io/badge/Slack-4A154B?style=plastic&logo=Slack&logoColor=#4A154B">
**서버** : 정재민  

**클라이언트** : 채윤원

## 서비스 소개
### 배달 앱 '[쿠팡이츠](https://play.google.com/store/apps/details?id=com.coupang.mobile.eats&hl=ko&gl=US)' 클론코딩 실전 프로젝트
#### (1) 회원가입과 로그인
* 신규 사용자는 아래 탭 중 하나을 클릭해 **회원가입**을 진행합니다.
* 가입 이력이 있는 휴대폰 번호는 가입할 수 없습니다.
* 사용자의 텍스트를 실시간 감지하여 조건 충족 여부를 확인합니다.  
* 기존 회원은 **로그인**을 진행해주세요.  

#### (2) 메인 화면
* 메인 화면 내 카테고리를 선택하여 원하는 가게 리스트를 확인합니다.  
* 가게 **필터링**으로 추천순, 신규매장순으로 정렬 가능합니다.  

#### (3) 배달지 주소 등록
* 주문을 진행하기 전 메인 화면 상단 주소를 클릭해 **주소를 등록**해주세요.
* 현재 위치 기반으로 주소를 작성할 수 있습니다.
* 가장 최근에 작성된 주소를 기반으로 배달됩니다.

#### (4) 가게 즐겨찾기
* 각 가게 상세 페이지 내 하트 클릭 시 **즐겨찾는 가게**로 등록됩니다. `🤍 -> 🖤`
* 즐겨찾기 탭을 통해 즐겨찾는 가게 목록을 확인해보세요!

#### (5) 음식 주문하기
* 원하는 메뉴를 선택해 **카트**에 담아주세요!
* 카트에 음식이 담긴 경우 가게 상세 페이지 하단에 `카트 보기` 탭이 생성됩니다.
* 카트 내 `결제하기` 하단 탭을 클릭해 결제를 진행해주세요.
* 테스트 결제 시 유의해주세요, **실제 PAYAPP 을 통해 카드 결제**됩니다!
* 주문 내역 탭을 통해 과거 주문 내역과 현 준비중 주문 내역을 확인해보세요.

#### (6) 리뷰 작성하기
* 주문한 건에 한하여 리뷰를 작성할 수 있습니다.
* 각 가게 상세 페이지 내 리뷰 `>` 클릭 시 리뷰 목록과 평점을 확인할 수 있습니다.

#### (7) 로그이아웃
* My이츠 탭 내 `설정` 버튼 클릭 시 로그아웃 됩니다.

### 활용 기술
* `mvp` design pattern
* Retrofit 2.9.0
* Coroutines
* ViewFlipper for banner images
* RecyclerView with adpater pattern
* TextWatcher, addTextChangedListener in Join Service
* CollapsingToolbarLayout + TabLayout + ViewPager
* JWT authorization, SharedPreference

### 라이브러리 & 오픈 API
* Circle ImageView [(Github)](https://github.com/hdodenhof/CircleImageView)
* Spin kit [(Github)](https://github.com/ybq/Android-SpinKit)
* 개발자센터 도로명 주소 검색 API
* 네이버 Mobile Dynamic Map API (Android SDK 3.0)
* 네이버 Reverse Geocoding API
* 부트페이 결제 연동 API / PAYAPP 카드결제

### 서비스 화면
🌈 추후 화면 캡쳐본 추가 예정

## 서비스 설계
### REST API 명세서
`Google sheets`, `postman` 사용해 명세서 작성  

### 소스 구조
> MVP (Model-View-Presenter) pattern 을 따르기 위해 config, src, util 로 나누어 개발 진행
* `config` : 프로젝트의 근간이 되는 베이스 코드를 모아놓은 폴더
* `src` : 베이스 코드를 이용해 구현된 코드를 모아놓은 폴더, 도메인별로 구분됨
* `util` : 프로젝트 내 도구처럼 사용되는 코드를 모아놓은 폴더
```text
com.yunwoon.coupangeatsproject
  > * config
    | ApplicationClass.kt // 앱 실행될 때 맨 처음 1번 실행되는 코드, 싱글톤 객체 보관
    | BaseActivity.kt // 액티비티 설계 시 공통적으로 사용되는 구조를 위한 모델 클래스
    | BaseFragment.kt // 프래그먼트 설계 시 〃〃
    | BaseResponse.kt // 서버로부터 받는 통신 응답 공통적으로 사용되는 구조를 위한 모델 클래스
    | XAccessTokenInterceptor.kt // 서버가 클라이언트를 식별하기 위한 jwt 토큰 관련 코드
  > * src
    > main // BottomNavigationView 들어가는 도메인별 분리
      > home
        > models // 서버와 어떤 형태로 데이터를 주고받을지 작성하는 모델 클래스
          | CategoryResponse.kt 
          | HomeResponse.kt
          | ResultCategory.kt
          | ResultHome.kt
          | ResultHomeRestaurants.kt
      | HomeFragment.kt // 메인 UI를 조작하는 곳
      | HomeFragmentView.kt // 네트워크와 화면(액티비티,프래그먼트)를 연결해주는 곳
      | HomeRetrofitInterface.kt // Retrofit API Interface 명세서
      | HomeService.kt // 실제로 서버에 요청을 보내는 코드를 작성하는 곳
      > favorite 
      > login
      > mypage
      > order
      | MainActivity.kt
      ㆍ
      ㆍ
  > * util
    > storeRecycler
      | StoreAdapter.kt // 리사이클러뷰에 표시될 아이템 뷰를 생성하는 곳
      | StoreData.kt // 리사이클러뷰에 들어갈 아이템의 데이터 클래스
    | LoadingDialog.kt // 로딩 화면 다이얼로그
    ㆍ
    ㆍ
build.gradle // gradle 빌드시에 필요한 dependency 설정하는 곳
.gitignore // git 에 포함되지 않아야 하는 폴더, 파일들을 작성 해놓는 곳

```

### 개발환경
* AWS EC2 / RDS
* Node.js
* MySQL
* Android Studio
