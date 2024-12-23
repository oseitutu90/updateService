# Use the official Maven image with JDK 17 to create a build artifact
FROM maven:3.8.2-openjdk-17-slim AS build

# Set the working directory in the Docker container
WORKDIR /workspace/app

# Copy the Maven pom.xml and src directory to the container
COPY pom.xml .
COPY src ./src

# Package the application and skip tests to build a fat JAR
RUN mvn clean package -DskipTests

# Use OpenJDK 17 for running the application
FROM eclipse-temurin:17-jdk-jammy

# Set the working directory in the Docker container
WORKDIR /application

# Copy the JAR file from the build stage
COPY --from=build /workspace/app/target/*.jar app.jar

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
