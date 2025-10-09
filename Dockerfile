# --- STAGE 1: BUILD (Use standard Maven base image) ---
FROM maven:3-jdk-17 AS build # Simplified tag: Maven 3 with any JDK 17
WORKDIR /app
COPY . .
# Run the Maven command to compile and create the JAR file in the 'target' folder
RUN mvn clean package -DskipTests 

# --- STAGE 2: RUN (Use lighter JRE image) ---
FROM openjdk:17-jre-slim
WORKDIR /app
# Copy the built JAR file from the 'build' stage memory
COPY --from=build /app/target/web-proj-0.0.1-SNAPSHOT.jar app.jar

# Expose the port 
EXPOSE 8080

# Command to run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
