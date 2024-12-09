# Build stage
# eclipse-temurin:17-jdk 이미지를 기반으로 새 단계(빌드 단계)를 정의, JDK가 포함된 환경을 사용
FROM eclipse-temurin:17-jdk AS builder

# Build context에 전체 소스를 복사
COPY .. /app
WORKDIR /app

# Gradle로 build task 실행, -x test 옵션으로 테스트 생략 가능
RUN ./gradlew :web:bootJar -x test

# Runtime stage
# JDK 보다 가벼운 JRE 이미지를 사용해 최종 컨테이너 크기를 줄임
FROM eclipse-temurin:17-jre AS runtime

# 빌드된 JAR 파일을 runtime 이미지로 복사
COPY --from=builder /app/web/build/libs/web-0.0.1-SNAPSHOT.jar app.jar

# 애플리케이션 실행 커맨드
ENTRYPOINT ["java", "-jar", "app.jar"]