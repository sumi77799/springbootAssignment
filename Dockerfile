# Stage 1: Build the application
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean install

# Stage 2: Create a lightweight image with only JRE and the application JAR
FROM openjdk:17-jdk-slim
WORKDIR /app
EXPOSE 8080
COPY --from=build /build/target/assignment.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

# Set metadata for the image
LABEL maintainer="Govind"
LABEL version="1.0"
LABEL description="Spring Boot Application"

