FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
## Copying external resources for Thymeleaf templates (fixes ERR_TOO_MANY_REDIRECTS)
COPY resources ./resources
#Note: The app works locally with the default application.yaml (localhost).
#When running via Docker, you must override the database URL using an environment variable.
#Example: docker run -p 8080:8080 -e spring.datasource.url=jdbc:postgresql://host.docker.internal:5432/jira jira-server
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]


