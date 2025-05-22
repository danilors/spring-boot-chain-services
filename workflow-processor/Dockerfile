# Stage 1: Build the application with Maven
FROM docker.io/library/maven:3.9.9-eclipse-temurin-21-alpine AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -f pom.xml clean package -DskipTests

# Stage 2: Run the application
FROM ghcr.io/graalvm/jdk-community:21
WORKDIR /appmaven:
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 33504docker.io/library/

ENTRYPOINT ["java", "-jar", "app.jar"]
