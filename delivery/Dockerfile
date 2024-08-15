#### Maven build stage ####
FROM maven:3.9-eclipse-temurin-21-alpine as maven_build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# build the app and download dependencies only when these are new (thanks to the cache)
RUN --mount=type=cache,target=/root/.m2  mvn clean package -Dmaven.test.skip

### Run stage ###
FROM amazoncorretto:21.0.4-al2023
ARG JAR_FILE=/app/target/*.jar
COPY --from=maven_build ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]