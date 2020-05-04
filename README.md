[![HitCount](http://hits.dwyl.com/devham76/springboot2-webservice.svg)](http://hits.dwyl.com/devham76/springboot2-webservice)

# [햄의 기록](http://ec2-15-165-155-30.ap-northeast-2.compute.amazonaws.com:8080/loginView)
"햄의 기록"은 취업 준비 기간을 기록한 개인 사이트입니다.

## 특징

- :sparkles:  이동욱님의 「스프링 부트와 aws로 혼자 구현하는 웹 서비스」책의 실습을 바탕으로 구현했습니다.
- 실습이 완료된 사이트에 기능을 추가하여 구현했습니다.
- 구글과 네이버(개발서버에서만 가능)를 통해 로그인 가능하며 로그인 시에만 열람할 수 있습니다.
- USER로 인증된 사용자만 게시물을 등록할 수 있습니다. 
- AWS EC2에 배포했습니다,
- Travis CI, AWS S3, AWS CodeDeploy를 이용하여 배포를 자동화 했습니다. 


## 프로젝트를 통해 느낀점

```
1. 처음 사용해본 기술이 많아서 쉽지 않았지만 배우고 직접 사용해본 것이 즐거웠습니다.
2. 작은 퍼포먼스만 내도 괜찮은 서비스인데 spring framework로 개발하니 생각했던 것보다 시간이 오래 걸려 아쉽습니다.
```

## 사용된 기술
### 서버 사이드
- Spring boot 2.1.7. (Gradle)
- Spring JPA
- Spring OAuth2
- MariaDB
- AWS
### 프론트 사이드
- JavaScript(Jquery)
- mustache template
- css
- bootstrap


## 화면설명

### 로그인 화면
- 웹 서비스 접속을 위해서는 로그인이 필요합니다
- 모든 사용자가 읽을 수 있으며 글 등록은 허용된 사용자만 가능합니다.
![로그인화면](https://user-images.githubusercontent.com/55946791/76191505-d9296400-6222-11ea-898b-18a4e6fbc4fd.JPG)

```java
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()   
                    .and()
                .authorizeRequests()    // URL별 관리를 설정하는 옵션의 시작점
                    .antMatchers( "/css/**",
                        "/images/**", "/js/**", "/h2-console/**",
                        "/profile", "/loginView").permitAll() // permitAll():전체열람권한
                    // get요청은 로그인한 사용자만 허용
                    .antMatchers(HttpMethod.GET).authenticated()
                    // post요청은 인증된 사용자만 허용
                    .antMatchers(HttpMethod.POST).hasRole(Role.USER.name())
                    .and()
                // 로그인 페이지 커스터마이즈
                .formLogin()
                    .loginPage("/loginView").permitAll()
                    .and()
    // ... 생략
}
```


### 메인 
- 메인 화면입니다.
![메인](https://user-images.githubusercontent.com/55946791/76191420-a7b09880-6222-11ea-8d70-c7d3061e75bb.JPG)

### 공부일지
- 매일의 공부 내용을 기록합니다
- 공부시간에 따라 달력에 표시되는 색이 다릅니다.
![공부일지](https://user-images.githubusercontent.com/55946791/76191419-a7b09880-6222-11ea-8c4c-805b348a9e35.JPG)

### 일지기록
- 그날의 일기를 기록합니다.
![일지](https://user-images.githubusercontent.com/55946791/76191424-a8e1c580-6222-11ea-86d7-bd31a7844575.JPG)

### 목표설정
- 목표와 그에 따른 계획을 설정할 수 있습니다.
![목표설정](https://user-images.githubusercontent.com/55946791/76191422-a8492f00-6222-11ea-9df5-6fbb9e667a46.JPG)

### 채용정보
- 커리어API와 잡플래닛, 링커리어에서 자주 찾아보는 개발자 채용정보를 한페이지에서 확인할 수 있습니다.
![채용정보](https://user-images.githubusercontent.com/55946791/76191416-a67f6b80-6222-11ea-98ed-d7d6884a98c8.JPG)

### 채용달력
-잡코리아의 대졸공채 채용달력을 확인할 수 있습니다.
![채용달력](https://user-images.githubusercontent.com/55946791/76191427-a97a5c00-6222-11ea-8f0e-a60c24c4a77e.JPG)

### 응원글
- 웹 페이지 상단에 표시되는 응원글을 관리하는 화면 입니다.
![응원글](https://user-images.githubusercontent.com/55946791/76191423-a8492f00-6222-11ea-8d16-c4b0d05f41e7.JPG)


## 파일 구성

### 1. java 파일
```
├─main
│  ├─java
│  │  └─webservice
│  │      └─springboot2
│  │          └─test
│  │              │  Application.java
│  │              │
│  │              ├─config
│  │              │  │  JpaConfig.java
│  │              │  │  WebConfig.java
│  │              │  │
│  │              │  └─auth
│  │              │      │  CustomOAuth2UserService.java
│  │              │      │  LoginUser.java
│  │              │      │  LoginUserArgumentResolver.java
│  │              │      │  SecurityConfig.java
│  │              │      │  SessionUser.java
│  │              │      │
│  │              │      └─dto
│  │              │              OAuthAttributes.java
│  │              │
│  │              ├─domain
│  │              │  │  BaseTimeEntity.java
│  │              │  │
│  │              │  ├─plansGoles
│  │              │  │      Goles.java
│  │              │  │      GolesRepository.java
│  │              │  │      Plans.java
│  │              │  │      PlansRepository.java
│  │              │  │
│  │              │  ├─posts
│  │              │  │      Posts.java
│  │              │  │      PostsRepository.java
│  │              │  │
│  │              │  ├─records
│  │              │  │      Records.java
│  │              │  │      RecordsRepository.java
│  │              │  │
│  │              │  ├─recruits
│  │              │  │      Recruits.java
│  │              │  │
│  │              │  ├─underpins
│  │              │  │      Underpins.java
│  │              │  │      UnderpinsRepository.java
│  │              │  │
│  │              │  └─user
│  │              │          Role.java
│  │              │          User.java
│  │              │          UserRepository.java
│  │              │
│  │              ├─service
│  │              │      CrawlingService.java
│  │              │      GolesService.java
│  │              │      PlansService.java
│  │              │      PostsService.java
│  │              │      RecordsService.java
│  │              │      RecruitsService.java
│  │              │      UnderpinsService.java
│  │              │
│  │              └─web
│  │                  │  HelloController.java
│  │                  │  IndexController.java
│  │                  │  PlanGoleApiContorller.java
│  │                  │  PostsApiController.java
│  │                  │  README.java
│  │                  │  RecordsApiController.java
│  │                  │  UnderpinsApiController.java
│  │                  │  UnderpinsController.java
│  │                  │
│  │                  └─dto
│  │                      │  HelloResponseDto.java
│  │                      │  RecruitsDto.java
│  │                      │
│  │                      ├─plansGolesDto
│  │                      │      GolesListResponseDto.java
│  │                      │      GolesResponseDto.java
│  │                      │      GolesSaveRequestDto.java
│  │                      │      GolesUpdateRequestDto.java
│  │                      │      PlansSaveRequestDto.java
│  │                      │
│  │                      ├─PostsDto
│  │                      │      PostsListResponseDto.java
│  │                      │      PostsResponseDto.java
│  │                      │      PostsSaveRequestDto.java
│  │                      │      PostsUpdateRequestDto.java
│  │                      │
│  │                      ├─RecordsDto
│  │                      │      RecordsListResponseDto.java
│  │                      │      RecordsMarkDto.java
│  │                      │      RecordsSaveRequestDto.java
│  │                      │      RecordsUpdateRequestDto.java
│  │                      │
│  │                      └─UnderpinsDto
│  │                              UnderpinsListResponseDto.java
│  │                              UnderpinsResponseDto.java
│  │                              UnderpinsSaveRequestDto.java
│  │                              UnderpinsUpdateRequestDto.java
│  │
│  └─resources
│      │  application-oauth.properties
│      │  application-real.properties
│      │  application.properties

```

### 2. front 관련 파일

```
│      ├─static
│      │  ├─css
│      │  │      animate.min.css
│      │  │      dashboard.css
│      │  │      signin.css
│      │  │      style.css
│      │  │      zabuto_calendar.min.css
│      │  │
│      │  ├─images
│      │  │      me.JPG
│      │  │
│      │  └─js
│      │      └─app
│      │              bootstrap.min.js
│      │              footer.js
│      │              index.js
│      │              jquery.lettering.js
│      │              jquery.textillate.js
│      │              mustache.min.js
│      │              planGole.js
│      │              recruitInfo.js
│      │              underpins.js
│      │              underpinsCookie.js
│      │              weekly.js
│      │              zabuto_calendar.min.js
│      │
│      └─templates
│          │  index.mustache
│          │  jobCalendar.mustache
│          │  login.mustache
│          │  planGole.mustache
│          │  posts-save.mustache
│          │  posts-update.mustache
│          │  posts.mustache
│          │  recruitInfo.mustache
│          │  underpins-save.mustache
│          │  underpins-update.mustache
│          │  underpins.mustache
│          │  weekly.mustache
│          │
│          └─layout
│                  footer.mustache
│                  header.mustache
│                  headTitle.mustache
│                  sidebar.mustache

```

### 3. TEST파일
```
└─test
    ├─java
    │  └─webservice
    │      └─springboot2
    │          └─test
    │              ├─domain
    │              │  └─posts
    │              │          plansGolesRepositoryTest.java
    │              │          PostsRepositoryTest.java
    │              │
    │              └─web
    │                  │  HelloControllerTest.java
    │                  │  IndexControllerTest.java
    │                  │  PlanGoleApiControllerTest.java
    │                  │  PostsApiControllerTest.java
    │                  │
    │                  └─dto
    │                          HelloResponseDtoTest.java
    │
    └─resources
            application.properties

```
