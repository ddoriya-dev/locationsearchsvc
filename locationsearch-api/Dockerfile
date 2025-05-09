# 빌드 단계
FROM gradle:8.10-jdk17 AS builder
WORKDIR /app
# Gradle Wrapper와 설정 파일 복사
COPY gradlew build.gradle.kts settings.gradle.kts /app/
COPY gradle /app/gradle
# 모듈별 build.gradle.kts와 소스 코드 복사
COPY locationsearch-api/build.gradle.kts /app/locationsearch-api/
COPY locationsearch-api/src /app/locationsearch-api/src
# 의존성 다운로드
RUN ./gradlew :locationsearch-api:dependencies --no-daemon
# 특정 모듈 빌드
RUN ./gradlew :locationsearch-api:clean :locationsearch-api:bootJar --no-daemon

# 실행 단계
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/locationsearch-api/build/libs/*.jar locationsearch-api.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "locationsearch-api.jar"]
