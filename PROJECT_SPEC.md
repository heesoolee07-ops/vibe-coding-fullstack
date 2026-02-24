# 프로젝트 명세서: vibeapp

본 문서는 스프링부트 웹 애플리케이션 `vibeapp`의 설정 및 구조를 정의합니다.

## 1. 프로젝트 설정

| 항목 | 상세 내용 |
| :--- | :--- |
| **JDK** | JDK 25 이상 |
| **언어 (Language)** | Java |
| **프레임워크 (Spring Boot)** | 4.0.1 이상 |
| **빌드 도구 (Build Tool)** | Gradle 9.3.0 이상 |
| **DSL** | Groovy DSL (`build.gradle`) |
| **구성 방식 (Configuration)** | YAML 파일 사용 (`application.yml`) |

## 2. 프로젝트 메타데이터

- **Group**: `com.example`
- **Artifact**: `vibeapp`
- **Version**: `0.0.1-SNAPSHOT`
- **Main Class Name**: `VibeApp`
- **Description**: 게시글 CRUD 기능을 제공하는 스프링부트 웹 애플리케이션

## 3. 플러그인 및 의존성

- **플러그인**:
  - `org.springframework.boot` (버전: 4.0.1)
  - `io.spring.dependency-management` (버전: 1.1.7)
  - `java`

- **의존성**:
  - `spring-boot-starter-web` — MVC 웹 레이어
  - `spring-boot-starter-thymeleaf` — 서버 사이드 템플릿 엔진
  - `spring-boot-starter-validation` — Bean Validation (JSR-380)
  - `spring-boot-starter-jdbc` — JDBC 데이터 접근
  - `mybatis-spring-boot-starter:3.0.4` — MyBatis ORM
  - `com.h2database:h2` — 파일 기반 H2 인메모리 DB
  - `spring-boot-starter-test` — 테스트 (JUnit 5)

## 4. 설정 파일 (`application.yml`)

```yaml
spring:
  datasource:
    driver-class-name: org.h2.Driver
    url: jdbc:h2:file:./data/testdb;AUTO_SERVER=TRUE
    username: sa
  h2:
    console:
      enabled: true
      path: /h2-console
  sql:
    init:
      mode: never   # 스키마 및 데이터 초기화 비활성화 (H2 파일 DB 영속성)

mybatis:
  mapper-locations: classpath:mapper/**/*.xml
  type-aliases-package: com.example.vibeapp.post
  configuration:
    map-underscore-to-camel-case: true
```

## 5. 프로젝트 구조

### 5.1. 패키지 구조 (Feature-based)

```
com.example.vibeapp
├── VibeApp.java                  # 애플리케이션 진입점
├── config/
│   ├── MyBatisConfig.java        # MyBatis SqlSessionFactory, MapperScan 설정
│   └── H2ConsoleConfig.java      # H2 Console 보안 설정
├── home/
│   └── HomeController.java       # 홈 페이지 컨트롤러
└── post/
    ├── Post.java                 # 게시글 엔티티
    ├── PostTag.java              # 게시글 태그 엔티티
    ├── PostRepository.java       # 게시글 MyBatis Mapper 인터페이스
    ├── PostTagRepository.java    # 게시글 태그 MyBatis Mapper 인터페이스
    ├── PostService.java          # 게시글/태그 비즈니스 로직
    ├── PostController.java       # 게시글 HTTP 요청 처리
    ├── PostCreateDto.java        # 게시글 등록 DTO (Record)
    ├── PostUpdateDto.java        # 게시글 수정 DTO (Record)
    ├── PostResponseDto.java      # 게시글 응답 DTO (Record)
    └── PostListDto.java          # 게시글 목록 DTO (Record)
```

### 5.2. 템플릿 구조

```
src/main/resources/templates/
├── home/
│   └── index.html               # 홈 화면
└── post/
    ├── posts.html               # 게시글 목록
    ├── post_detail.html         # 게시글 상세
    ├── post_new_form.html       # 게시글 등록 폼
    └── post_edit_form.html      # 게시글 수정 폼
```

### 5.3. DB 스키마 (`schema.sql`)

```sql
CREATE TABLE IF NOT EXISTS POSTS (
    NO BIGINT AUTO_INCREMENT PRIMARY KEY,
    TITLE VARCHAR(200) NOT NULL,
    CONTENT CLOB NOT NULL,
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP DEFAULT NULL,
    VIEWS INT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS POST_TAGS (
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    POST_NO BIGINT NOT NULL,
    TAG_NAME VARCHAR(50) NOT NULL,
    CONSTRAINT FK_POST_TAGS_POST_NO FOREIGN KEY (POST_NO) REFERENCES POSTS(NO)
);
```

### 5.4. MyBatis Mapper XML

```
src/main/resources/mapper/
└── post/
    ├── PostMapper.xml            # POSTS 테이블 CRUD SQL
    └── PostTagMapper.xml         # POST_TAGS 테이블 CRUD SQL
```

## 6. 구현된 기능

### 6.1. 게시글 관리 (Post)

| 기능 | URL | Method | 설명 |
| :--- | :--- | :--- | :--- |
| 목록 조회 | `/posts` | GET | 페이지당 5개 페이징 처리 |
| 상세 조회 | `/posts/{no}` | GET | 조회 시 조회수 자동 증가 |
| 등록 폼 | `/posts/new` | GET | 제목, 내용, 태그 입력 |
| 등록 처리 | `/posts/add` | POST | Bean Validation 적용 |
| 수정 폼 | `/posts/{no}/edit` | GET | 기존 데이터 바인딩 |
| 수정 처리 | `/posts/{no}/save` | POST | Bean Validation 적용 |
| 삭제 | `/posts/{no}/delete` | GET | 연관 태그 함께 삭제 |

### 6.2. 게시글 태그 기능

- 쉼표(`,`)로 구분된 태그 문자열 입력
- 등록/수정 시 `POST_TAGS` 테이블에 태그별로 저장
- 수정 시 기존 태그 전체 삭제 후 재저장
- 게시글 삭제 시 연관 태그 자동 삭제
- 게시글 상세 화면에서 제목 아래 `#태그명` 형태의 뱃지로 출력

### 6.3. 공통 기능

- **조회수**: 상세 페이지 접근 시 자동 증가
- **다크 모드**: Tailwind CSS 기반 다크/라이트 모드 전환
- **반응형 디자인**: 모바일/데스크탑 대응 UI
- **입력 유효성 검사**: 제목 필수 입력, 최대 100자 제한

## 7. 검증 방법

- **빌드**: `./gradlew build`
- **실행**: `./gradlew bootRun` → http://localhost:8080
- **H2 콘솔**: http://localhost:8080/h2-console (JDBC URL: `jdbc:h2:file:./data/testdb`)
