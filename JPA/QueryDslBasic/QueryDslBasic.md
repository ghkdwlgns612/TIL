## QueryDsl의 기본에 대하여

일단 [한국형 Document](http://querydsl.com/static/querydsl/3.6.3/reference/ko-KR/html_single/) 가 존재했다.
이것을 참고하여 작성하겠다. 근데 QueryDsl은 HQL을 코드로 작성할 수 있도록 해주는 프로젝트이다. Document를 보면 JDO, JDBC, MongoDB등 다양한 지원을 한다. 오늘은 JPA에서 QueryDsl을 다루는 방법을 한 번 보겠다.


일단 build.gradle을 설정해보자. 솔직하게 다양한 옵션들이 있는데 밑의 설정이 잘 이해가 가지 않는다. 다음 포스트는 build.gradle에 관해 써봐야겠다.


```
plugins {
    id 'org.springframework.boot' version '2.4.4'
    id 'io.spring.dependency-management' version '1.0.11.RELEASE'
    id "com.ewerk.gradle.plugins.querydsl" version "1.0.10"
    id 'java'
}

group = 'com.example'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = '11'

repositories {
    mavenCentral()
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-webflux'
    implementation 'org.projectlombok:lombok:1.18.20'
    annotationProcessor 'org.projectlombok:lombok:1.18.12'
    runtimeOnly 'com.h2database:h2'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'io.projectreactor:reactor-test'

    implementation 'com.querydsl:querydsl-jpa'
}

tasks.named('test') {
    useJUnitPlatform()
}

def querydslDir = "$buildDir/generated/xxx" // (3)

querydsl {
    jpa = true
    querydslSourcesDir = querydslDir
}

sourceSets { //main.java.srcDir 파일을 sources root파일에 클래스로드
    main.java.srcDir querydslDir
}

configurations {
    querydsl.extendsFrom compileClasspath
}

compileQuerydsl {
    options.annotationProcessorPath = configurations.querydsl
}
```

위의 파일을 설정하고 @Entity, @Embedded가 붙은 클래스를 개인적으로 만들어보자. 그러고 빌드를하면 generated/xxx가 source root파일로 되며 Q클래스들이 생성될 것이다.
그럼 Qclass는 무엇일까? 컴파일 시점에 클래스를 올려서 다양한 프로퍼티에 접근이 가능하도록 만들어주는 용도이다. QueryDsl의 큰 장점 중 하나가 컴파일 시점에 문법오류를 잡아 낼 수 있다는 것이다. 그래서 Qclass가 필요하다.
생성된 걸 쓸 수 있고 new Qmember로 따로 별칭을 해서 사용할 수 있다.

현재 내가 QueryDsl을 쓰기 위한 것은 다중 조건의 동적 쿼리 + 페이지내이션을 목적으로 사용하기에 예시도 그것으로 들겠다.
사용을 위해서는 EntityManager클래스로 JPAQueryFactory를 생성해줘야한다. JPAQueryFactory에는 select, update, insert등의 기본적인 CRUD메소드가 들어있다.


```
@GetMapping("/multi")
//http://localhost:8080/multi?pageNumber=2&size=1&name=jihun&department=soccer
//http://localhost:8080/multi?pageNumber=2&size=1&name=jihun
//http://localhost:8080/multi?pageNumber=2&size=1&department=soccer
//http://localhost:8080/multi?pageNumber=2&size=1
public void multiController(Integer pageNumber, Integer size, String name, String department) {
    List<Member> fetch = jpaQueryFactory
            .selectFrom(qMember)
            .where(eqMemberName(name), eqDepartment(department))
            .offset(pageNumber)
            .limit(size)
            .fetch();
}
private BooleanExpression eqDepartment(String department) {
    if (ObjectUtils.isEmpty(department))
        return null;
    return qMember.department.eq(department);
}
private BooleanExpression eqMemberName(String name) {
    if (ObjectUtils.isEmpty(name))
        return null;
    return qMember.memberName.eq(name);
}
```

위의 코드에 주석이 보일 것이다. 이렇게 다중 조건을 동적으로 하려면 4개의 SQL문을 작성해야하지만 QueryDsl은 이것을 손쉽게 해결해준다.
아주 멋진 기술이다. 심화 과정을 해봐야겠다.

다음 할 일 : Gradle(자동화 빌드), QueryDsl심화과정