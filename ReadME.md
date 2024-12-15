```dockerfile 
# this is tied to  jvm prerequisite
# Copy the Maven pom.xml and src directory to the container
COPY pom.xml .
COPY src ./src

# Package the application and skip tests. Ensure it builds a fat JAR.
RUN mvn clean package -DskipTests

# Use OpenJDK 17 to run the built artifact
FROM openjdk:17-jdk-slim

# Set the working directory in the Docker container
WORKDIR /application

# Copy the JAR from the build stage
COPY --from=build /workspace/app/target/*.jar app.jar

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

```

```dockerfile
# graalvm route
# Copy the Maven pom.xml and src directory to the container
COPY pom.xml .
COPY src ./src

# Package the application and skip tests. Ensure it builds a fat JAR.
RUN mvn clean package -DskipTests

# Use OpenJDK 17 to run the built artifact
FROM openjdk:17-jdk-slim

# Set the working directory in the Docker container
WORKDIR /application

# Copy the JAR from the build stage
COPY --from=build /workspace/app/target/*.jar app.jar

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

```