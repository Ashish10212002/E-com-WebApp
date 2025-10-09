# --- STAGE 1: BUILD (Creates the JAR) ---
# Use a Maven image which contains Java and the build tools
FROM maven:3.9.6-openjdk-17 AS build
WORKDIR /app
# Copy your entire source code into the build stage
COPY . .
# Run the Maven command to compile, test, and create the JAR in the 'target' folder
RUN mvn clean package -DskipTests 

# --- STAGE 2: RUN (Runs the JAR) ---
# Use a lightweight JRE (Java Runtime Environment) image
FROM openjdk:17-jre-slim
WORKDIR /app
# Copy the built JAR file from the 'build' stage into the run stage
COPY --from=build /app/target/web-proj-0.0.1-SNAPSHOT.jar app.jar

# Expose the port 
EXPOSE 8080

# Command to run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
