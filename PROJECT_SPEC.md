# 프로젝트 명세서: vibeapp

본 문서는 최소 기능을 갖춘 스프링부트 애플리케이션 `vibeapp`의 설정 및 구조를 정의합니다.

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
- **Description**: 최소 기능 스프링부트 애플리케이션을 생성하는 프로젝트다.

## 3. 플러그인 및 의존성

- **플러그인**:
  - `org.springframework.boot` (버전: 4.0.1)
  - `id 'io.spring.dependency-management' version '1.1.7'` (또는 호환 버전)
  - `java`
- **의존성**:
  - `spring-boot-starter-web`
  - `spring-boot-starter-thymeleaf`

## 4. 프로젝트 구조 및 설정

- **설정 파일**: `src/main/resources/application.yml`
- **기본 패키지**: `com.example.vibeapp`
- **패키지 구조 (Feature-based)**:
    - `com.example.vibeapp.home`: 홈 및 공통 기능
    - `com.example.vibeapp.post`: 게시글 관련 모든 기능 (Entity, Controller, Service, Repository)
- **템플릿 구조**:
    - `src/main/resources/templates/home/`: 홈 관련 뷰
    - `src/main/resources/templates/post/`: 게시글 관련 뷰

## 5. 구현된 기능

### 5.1. 게시글 관리 (Post)
- **목록 조회**: 페이징 처리 (페이지당 5개), 페이지네이션 UI 제공
- **상세 조회**: 게시글 상세 내용 및 메타데이터 확인
- **등록/수정**: 제목 및 내용 입력/수정 기능
- **삭제**: 게시글 삭제 기능 제공

### 5.2. 공통 기능
- **다크 모드**: Tailwind CSS를 활용한 다크 모드 전환 기능
- **반응형 디자인**: 다양한 기기에 대응하는 반응형 UI

## 6. 검증 계획

- **자동화 테스트**: `./gradlew build` 명령을 통해 전체 빌드 및 테스트 통과 여부 확인
- **수동 검증**: `./gradlew bootRun` 실행 후 애플리케이션 정상 기동 확인 (8080 포트)
