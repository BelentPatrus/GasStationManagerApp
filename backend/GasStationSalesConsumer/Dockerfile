# Use a lightweight JDK 21 runtime image
FROM eclipse-temurin:21-jre-jammy

# Create app directory inside the container
WORKDIR /app

# Copy the JAR from your local build into the container
COPY target/*.jar app.jar

# Use the environment variable for profile (default is prod in docker-compose)
ENTRYPOINT ["java", "-jar", "app.jar"]
