# Lombok 주요 어노테이션 정리
- @Getter
- @Setter
- @NoArgsConstructor : 기본 생성자 만들어줌
- @AllArgsConstructor : 클래스의 모든 멤버변수를 받는 생성자를 만들어줌
- @RequiredArgsConstructor : final 혹은 @NonNull이 붙은 field 만을 받는 생성자 생성
- @Builder : builder pattern 방법으로 객체를 생성할 수 있다. 
- @ToString : toString 메서드 생성
- @EqualsAndHashCode : equals(equality 비교), hashCode(indentity 비교) 메서드 생성해줌
- @Data : 위에 언급한 모든 annotation을 포함하는 Annotation
- @SuperBuilder : parent instnace를 상속받는 child instance를 만들 때 부모 class의 field값도 지정할 수 있게 하기 위해서 사용(부모, 자식 객체 둘다 적용해야함)



### @SpringBootApplication
- 스프링 부트의 기본적인 설정 선언
- @ComponentScan : @component 어노테이션 및 @Service, @Repository, @Controller 등의 어노테이션을 스캔하여 Bean으로 등록해주는 어노테이션
- @EnableAutoConfiguration 사전에 정의한 라이브러리들을 Bean으로 등록해주는 어노테이션, 사전에 정의한 라이브러리가 특정 Condition을 만족하는 경우 Bean으로 등록함
    - 사전 정의 파일 위치 : Dependencies > spring-boot-autoconfigure > META-INF > spring.factories
    - 조건 : OnBeanCondition, OnClassCondition, OnWebApplicationCondition

### @EnableJpaAuditing
- Annotation to enable auditing in JPA via annotation configuration.
-  Audit은 감시하다, 감사하다라는 뜻
- Spring Data JPA에서 시간에 대해서 자동으로 값을 넣어주는 기능
- 도메인을 영속성 컨텍스트에 저장하거나 조회를 수행한 후에 update하는 경우 매번 시간 데이터를 입력해야하는데 audit을 이용하면 자동으로 시간을 매핑하여 데이터베이스의 테이블에 넣어주게 된다.

### SpringBootServletInitializer
- SpringBoot Web Application을 배포할 때는 주로 embedded tomcat이 내장된 jar파일을 이용. 하지만 어떤 경우에는 전통적인 배포 방식인 war 파일로 배포를 한다. 이 경우 SpringBootServletInitializer를 상속받아야 한다. 
- Spring Web Application 외부 tomcat에서 동작하게 만들려면 web.xml(deployment descriptor)에 application context를 등록해야 함 -> Apache Tomcat(Servlet Container)이 구동될 때 /WEB-INF 아래 있는 web.xml을 읽어 Web Application을 구성하기 때문이다.

### DTO와 entity
- Entity class는 실제 DB table과 매핑되는 클래스, Database의 table에 존재하는 column들을 field로 가지는 instance.
    - DB의 table과 1:1로 매핑되며, 테이블이 가지지 않는 column을 filed로 가져서는 안 된다.
    - DB persistence의 목적으로 사용되므로 Request나 Response의 값을 전달하는 역할로 사용되는 것은 좋지 않다.
    - setter method 사용을 지양한다. 변경되지 않는 instance에 대해서도 setter로 접근이 가능해지기 때문에 instance의 일관성, 안전성을 보장하기 힘들어지기 때문이다.(setter method == 불변이 아니다) -> 따라서 setter 대신 Constructor또는 Builder를 사용한다. Contructor는 불변 instance로 활용할 수 있어 데이터 전달 과정에서 데이터가 변조되지 않게 할 수 있으며 Builder의 경우 member variable이 많아도 어떤 값을 어떤 field에 넣는지 코드로 확인할 수 있으며 필요한 것만 넣는 것이 가능하다는 장점이 있다.

- DTO(Data Transfer Object)
    - Layer 간 데이터 교환이 이루어질 수 있도록 하는 객체로 JSON serialization과 같은 직렬화에도 사용되는 객체이다. DTO는 원래 DAO(Data Access Object) pattern에서 유래된 용어로 DAO에서 DB 처리 로직을 숨기고 DTO라는 결과값을 내보내는 용도로 활용했다. Controller와 같은 client와 직접 상호작용하는 layer에서는 entity 대신 DTO를 사용해서 데이터를 교환한다. Controller 이외에도 여러 layer 사이에서 DTO를 사용할 수 있지만 주로 View와 Controller 사이에서 데이터를 주고 받을 때 사용한다. DTO는 getter, setter method를 포함하며 이외의 business logic은 포함하지 않는다. 
    - setter method를 가진다는 것은 가변 객체(mutable object)로 활용된다는 이야기이다.

### @Entity
- 테이블과의 매핑
- @Entity가 붙으면 JPA가 관리하게 된다.
- basic constructor 필수, final class, enum, interface, inner class 에 사용 불가
- 저장할 field에 final 사용 불가

### @Table
- Entity와 매핑할 table 지정
- 생략 시 매핑한 Entity의 Name을 Table name 으로 사용
- app 실행 시점에 db table을 자동으로 생성한다. JPA는 데이터베이스 스키마를 자동으로 생성한다. class의 매핑 정보와 DB Dialect를 사용하여 Schema를 생성함
    - DB Dialect : 각 DBMS만의 독자적인 SQL(MS-SQL의 T-SQL, ORACLE의 PL/SQL 등)

### @Id
- JPA Entity instance의 identifier로 사용할 field에 적용

### @Column
- field를 table의 column에 매핑

### @GeneratedValue
- PK 값에 대한 생성 전략 제공
- entity 또는 매핑된 super class의 기본 키 field에 적용
- strategy
    - GenerationType.IDENTITY : PK 생성을 DB에 위임(DB가 알아서 AUTO_INCREMENT 지정)
    - GenerationType.SEQUNCE : DB의 Sequence Object 사용, 테이블 마다 Sequence Object를 따로 관리하고 싶다면 @SequenceGenerator에 sequenceName 속성 추가하면 됨
     - GenerationType.TABLE : 키 생성 전용 table을 하나 만들어서 DB Sequence를 흉내내는 전략
     - GenerationType.AUTO : Dialect에 따라 세 가지 전략을 자동으로 지정

### @MappedSuperclass
- 객체의 입장에서 생성시간이나 수정시간 같은 공통 매핑 정보가 필요할 때 사용
- @MappedSuperclass가 선언된 클래스는 Entity가 아니며 직접 생성해서 사용할 일이 없어 대부분 abstract class로 만든다.
- @createdDate, @modifiedDate을 인식하게 만든다.

### @EntityListeners
- a callback method to receive notification of a particular entity life cycle event.
- JPA Entity에 Persist, Remove, Update, Load에 대한 event 전과 후에 대한 callback method 제공
    - @PrePersist : Persist(insert)메서드가 호출되기 전에 실행되는 메서드
    - @PreUpdate : merge메서드가 호출되기 전에 실행되는 메서드
    - @PreRemove : Delete메서드가 - 호출되기 전에 실행되는 메서드
    - @PostPersist : Persist(insert)메서드가 호출된 이후에 실행되는 메서드
    - @PostUpdate : merge메서드가 호출된 후에 실행되는 메서드
    - @PostRemove : Delete메서드가 호출된 후에 실행되는 메서드
    - @PostLoad : Select조회가 일어난 직후에 실행되는 메서드

### @Component
- class를 bean으로 등록하기 위한 Annotation
- @Controller, @Service, @Repository : @Component의 구체화된 형태

### @Repository 
- 해당 클래스가 DB에 접근하는 클래스임을 타나냄
- 스프링 데이터 접근 계층으로 인식하고 데이터 계층의 예외를 스프링 예외로 반환한다.

### @Service 
- Repository를 통해 DB에서 가져온 데이터를 컨트롤러에게 전달하는 클래스
- 비즈니스 로직 처리와 도메인 모델의 적합성 검증 등을 수행

### @Controller
- 클라이언트로부터 전달된 데이터를 가공하기 위한 Controller
- @RequestMapping으로 경로 설정

### @Log4j2
- 메시지 개체 로깅, Java 8 람다식 및 가비지 없는 로깅을 지원
- 로그레벨
    - OFF : 가능성이 가장 높은 순위. 로그를 끄기 위해 사용
    - FATAL : 이른 종료를 일으키는 심각한 오류
    - ERROR : 다른 런타임 오류 또는 예기치 못한 조건 등
    - WARN : 오류에 가까운 것, 경고
    - INFO : 런타임 이벤트의 시작/종료 등
    - DEBUG : 시스템 전반의 흐름에 관한 정보, 로그에만 기록될 것으로 예측
    - TRACE : 가장 세세한 정보

### @Autowired
- 필요한 의존 객체의 타입에 해당하는 bean을 찾아 주입한다.
- constructor, setter, field에 사용 가능

### @SpringBootTest 
- 어노테이션을 통해 스프링부트 어플리케이션 테스트에 필요한 거의 모든 의존성을 제공

### @Test
- JUnit5의 어노테이션, Test 메서드로 인식을 하고 테스트