
## gradle maven (gradle을 선택한이유)
- maven은 xml로 정의하기 때문에 설정 내용이 길어지고 __가독성이__ 떨어진다
- 빌드와 테스트 실행 결과 gradle이 더 __빠르다__ 
(캐시를 사용하기 때문에 테스트 반복시 차이가 더 커진다)

## entity에 @Setter를 생성하지 않은 이유
- 언제 어디서 변해야 하는지 코드상으로 명확하게 구분하기 힘들기 때문
- 막연하게 Setter사용x,  __목적과 의도를 나타낼 수 있는 메소드 추가__ 해야한다.
- @Setter를 사용해서 값을 채우지 않고, @Builder의 빌더 클래스를 통해 값을 채운다.

```java
//-- 잘못 사용된 예, setter사용
public class Order{
    public void setStatus(boolean status){
        this.status = status;
    }    
}

public void 주문서비스의_취소이벤트(){
    order.setStatus(false);
}
//-- 올바르게 사용 
public class Order{
    public void cancleOrder(){
        this.status = false;
    }    
}

public void 주문서비스의_취소이벤트(){
    order.Order();
}
```

## @Builder를 사용하는 이유
- 어느 필드에 어떤 값을 채워야 하는지 명확하게 인지 가능

```java
public Example(String a, String b){
    this.a = a;
    this.b = b;
}

Example ex = new Example(b,a); // 위치가 변경되도 문제를 찾을 수 없다.

// -- builder
Example.builder()
.a(a)
.b(b)
.builder();
```

## @Autowired 말고 @RequiredArgsConstructor를 사용한이유
### DI의 3가지 방법 -> 생성자 주입 (o), 필드 주입(x)
- DI의 방법 : 생성자 주입, 필드 주입, setter주입
- 필드 주입은 작성하기 쉽지만, 여러가지 단점이있다
- 생성자 주입의 장점
1. 의존관계 설정이 되지 않으면 객체생성 불가 -> 컴파일 타임에 인지 가능, NPE 방지
2. 의존성 주입이 필요한 필드를 final 로 선언가능 -> Immutable
3. (스프링에서) 순환참조 감지가능 / field 주입시 에러발생안해서 순환참조의 문제인지 알기 어렵다

```java
  // AService > BService
  AService aService = new AService(BService);

  //BService > AService
  BService bService = new BService(AService);
```

4. 테스트 코드 작성 용이
> [https://yaboong.github.io/spring/2019/08/29/why-field-injection-is-bad/]

### @RequiredArgsConstructor는 생성자에 DI를 주입
- @NonNull이나 final이 붙은 필드에 대해서 생성자를 생성한다
- @Autowired없이 생성자에 DI주입이 가능하다.

```java
/** Autowired를 사용한 의존성 주입 */
  @Controller
  public class AutowiredUsedController {
    @Autowired
    private XXXService xxxService;
  }

  /** @RequiredArgsConstructor를 사용한 의존성 주입 */
  @Controller
  @RequiredArgsConstructor
  public class RacUseController {
    private final XXXService xxxService;
  }

```

## Entity class와 Controller에서 쓸 DTO를 분리하는 이유
- Entity class는 DB와 맞닿은 핵심 클래스이다
- Request와 Response시에 View를 위한 클래스는 자주 변경될 수 있다
- 이때 Entity class를 변경하는 것은 DB와 연결되어있기 때문에 너무 큰 리스크를 지닌다.
- 따라서 자주 변경되는 view를 위한 DTO 클래스는 분리해서 사용해야 된다.

## Mustache를 사용한이유
- __로직 코드를 사용할 수없어 view역할과 서버역할을 명확하게 분리__ 된다.
- 화면역할에만 충실하다(따라서 유지보수에 유리하다)
- 문법이 다른 템플릿 엔진보다 심플하다
- jsp, thymelefr등은 인텔리제이 커뮤니티 버전 지원X


## OAuth를 사용하는 이유 (소셜 로그인 사용이유)
- OAuth 로그인 구현 시 아래의 기능을 소셜에서 제공해주기 때문에 __서비스 개발에만 집중__ 가능하다.
    - 로그인 시 보안
    - 비밀번호 찾기/변경
    - 회원가입 시 이메일 혹은 전화번호 인증
    - 회원정보 변경

## AWS RDS에서 MariaDB를 선택한 이유
- 오라클, MSSQL보다 동일 사용 대비 가격이 저렴
- 동일 하드웨어 사양으로 MySQL보다 향상된 성능
- MySQL보다 다양한 기능, 좀 더 활성화된 커뮤니티 등

## CI도구중에 Travis CI를 선택한이유
- Travis CI는 깃허브에서 제공하는 무료 CI서비스이다
- 젠킨스 -> 설치형이기 때문에 이를 위한 EC2인스턴스가 하나 더 필요하다 (부담스럽다)
- AWS CodeBuild제공 -> 빌드 시간만큼 요금 부과

---

## .gitignore
- 자동으로 생성되는 파일은 모두 이그노어 처리한다


## @SpringBootAplication
- 프로젝트의 메인 클래스
- 해당 어노테이션에 의해 스프링 부트의 자동 설정, 스프링 Bean 읽기 생성 자동 설정
- 항상 프로젝트의 최상단에 위치해야 한다
- @SpringBootAplication으로 인해 내장 WAS가 실행된다
    - 내장 WAS : 외부에 WAS를 두지 않고 애플리케이션 실행 시 내부 WAS를 실행 하는것
    - 서버에 톰캣을 설치할 필요X, Jar파일 실행 가능
    - 내장 WAS 사용을 권장한다 -> 언제 어디서나 같은 환경에서 스프링 부트를 배포 할 수 있기 때문
    
```
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

## 롬복
- Getter, Setter, 기본생성자, toString 등을 어노테이션으로 자동 생성해준다

## @RequestParam , @RequestBody
### @RequestParam
- 외부에서 API로 넘긴 파라메터 가져오는 어노테이션이다
- 1:1로 파라미터를 받을경우

```java
@RequestMapping("/")
public String home(@RequestParam(value="id", defaultValue="false") String id) {
    return "home";
}
```

### @RequestBody
- 반드시 POST형식으로 응답을 받는 구조

```java
@PostMapping("/")
public String home(@ReqeustBody Student student) {
    return "home";
}
```

## JPA
- 자바 표준 ORM(Object Relation Mapping)
- ORM : __객체를 매핑하는 것__
- MyBatis, iBatis는 ORM (x), SQL Mapper(O) -> 쿼리를 매핑하는 것 
- 객체지향 프로그램을 RDBMS에 맞게 __SQL을 대신 생성해서 실행하는것__
- DB를 사용하는 서비스를 객체지향적으로 구현하는데 도움을 준다

```java
@Getter // 클래스 내 모든 필드의 Getter 메소드를 자동생성
@NoArgsConstructor  // 파라메터 없는 기본생성자 생성

// 테이블과 링크 될 클래스임을 알린다
// JPA의 어노데이션
@Entity
public class Posts extends BaseTimeEntity {
    @Id // pk필드
    @GeneratedValue(strategy = GenerationType.IDENTITY) // pk생성규칙, auto_increment
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    // 해당 클래스의 빌더 패턴 클래스를 생성
    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

```

### JpaRepository
- entity 클래스로 db접근을 해주는 클래스
- Dao라고 불리는 DB Layer접근자
- 인터페이스로 생성한다
- JpaRepository<Entity class, PK Type> 
- CRUD 메소드가 자동으로 생성된다

### 조회용 프레임워크
- querydsl, jooq, MyBatis 등
- FK의 조인, 복잡한 조건 등으로 인해 Entity클래스만으로 처리하기 어려워 조회용 프레임워크를 추가로 사용한다
- 프레임워크를 통해 조회하고, 추가/수정/삭제 등은 SpringDataJpa를 통해 진행한다
 
## enum

- DB로 관리하게 될 경우
    - 변경이 잦은 데이터일 경우 사용한다.
    - 단점
        - 개발자가 개발/운영시에 전체 데이터를 한눈에 볼 수 x
        - 컴파일 단계에서 검증하기가 어렵다
- enum으로 관리하게 될 경우
    - 변경에는 DB때보다 어렵지만 (변경이 필요할 경우 배포가 필요하게 됨) 
    - 개발자가 개발/운영시에 한눈에 전체 데이터를 확인하고, 
    - 컴파일러에서 직접 체크가 가능하기 때문에 실수할 여지가 줄어듭니다.
    
- 참고) JPA사용시 Entity에 @Enumerated(EnumType.SRTING)
    - JPA로 DB저장시 Enum값을 어떤 형태로 저장할지 결정
    - default : int형
    - 숫자로 저장되면 값을 파악할수없는 경우 string으로 변경한다

> <https://jojoldu.tistory.com/122>

## 클라우드 서비스
- 인터넷(클라우드)을 통해 서버, 스토리지(파일 저장소), 데이터베이스, 네트워크, 소프트웨어, 모니터링 등
- 컴퓨팅 서비스를 제공하는것.

## AWS EC2 설정
1. 인스턴스 생성
    - AMI(EC2 인스턴스 시작에 필요한 정보를 이미지로 만든것)을 이용해 linux 환경으로 생성
    - Amazon Linux AMI에는 필요한 Java, Docker, MySQL등이 포함되어 별도의 설치가 필요하지 않다
2. 방화벽 설정
    - pem키(비밀키) 관리와 __지정된 IP에서만 ssh 접속 가능__ 하도록 구성
    - pem키 : 비밀키와 대칭키는 암호화 복호화할때 사용

3. Elastie IP(EIP, 고정IP, 닽력적 IP) 할당
    - 같은 인스턴스를 중지하고 다시 시작할때 새IP가 할당 된다
    - 따라서 고정IP를 가지게 한다

4. Windows에서 서버 접속
    - puttygen.exe 에서 pem키->ppk 파일로 변환한다
    - putty.exe 실행, port 22(ssh)

5. EC2 서버 환경설정
    - JAVA8설치 (아마존 리눅스1은 JAVA7이 설치되어있음)
    - 타임존 변경 (미국->한국) 
    - 호스트네임 변경

## AWS RDS
    - 데이터베이스 설정, 패치, 백업, 하드웨어 프로비저닝 등 운영 작업을 자동화하여 
    - 개발자가 개발에 집중할수 있게 지원하는 서비스

## 배포 과정
    1. git clone, git pull을 통해 새 버전의 프로젝트를 받음
        - git clone
    2. Gradle or Maven을 통해 프로젝트 테스트와 빌드
        - ./gradlew build
    3. EC2 서버에서 해당 프로젝트 실행 밀 재실행
        - build 파일 복사 
        - 현재 실행중인 애플리케이션 id 확인(pgrep -f ${project_name}*.jar
        - 실행 중인 프로세스 있으면 kill $pid
        - __ls -tr $REPOSITORY | grep *.jar | tail -n 1__
            - : $REPOSITORY 폴더를 생성순으로 정렬 + grep으로 jar로 끝나는 파일 찾기 | 그중 제일 아래 1개만 출력
        - __nohup java -jar 폴더/실행파일 2>&1 &__
            - : nohup으로 실행 -> 데몬프로그램으로 실행 (사용자가 제어x, 백그라운드에서 실행)
            - java -jar __내장 톰캣을 이용해서__ jar파일만 있으면 바로 웹 애플리케이션 실행가능
            - 2&1 : stderr도 stdout으로 이동
            - & 터미널이 종료되도 계속 백그라운도 실행
        - git에 없는 application-oauth.properties 파일을 __서버에 직접 설정들을 갖게한다__
            - nohup java -jar \ -Dspring.config.location=classpath:/application.properties, /절대경로 지정 \ 실행폴더/실행파일 2>&1 &
       

## 배포 자동화
- CI : 안정적인 배포 파일을 만드는 과정
- 과정
    1. git push [] master
    2. Travis CI 테스트 및 빌드
    3. jar파일 Travis CI -> AWS S3(파일서버)에 전달
    4. Travis CI -> AWS CodeDeploy(배포 서비스)에 배포 요청
    5. jar파일 AWS S3 -> AWS CodeDeploy에 전달
    6. AWS CodeDeploy -> AWS EC2 배포
- IAM & 역할
    - IAM : 외부 서비스가 AWS에 접근하는 방식과 권한 관리(Travis CI와 AWS S3연동시 사용)
    - 역할 : AWS 서비스에만 할당할 수 있는 권한 (AWS S3와 CodeDeploy 연동시 사용)