# Use OpenJDK 17 as base
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the Spring Boot JAR into the container
COPY target/web-proj-0.0.1-SNAPSHOT.jar app.jar

# Expose the port (Render will assign PORT env variable)
EXPOSE 8080

# Start the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
