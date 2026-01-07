# ---------- BUILD STAGE ----------
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy parent build files first (better caching)
COPY pom.xml ./
COPY .mvn .mvn

# Copy module POMs first (better caching)
COPY identity/pom.xml identity/pom.xml
COPY gasbuddy-app/pom.xml gasbuddy-app/pom.xml

# Warm dependency cache (optional but helpful)
RUN mvn -B -q -DskipTests dependency:go-offline

# Copy full source
COPY identity identity
COPY gasbuddy-app gasbuddy-app

# Build ONLY runnable module + its required modules
RUN mvn -B -pl gasbuddy-app -am clean package -DskipTests

# ---------- RUNTIME STAGE ----------
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy runnable jar from the module
COPY --from=build /app/gasbuddy-app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]