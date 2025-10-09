# --- STAGE 1: BUILD (Creates the JAR) ---
# Using the official, generic Maven 3 with OpenJDK 17 image
FROM maven:3-openjdk-17 AS build 
WORKDIR /app
COPY . .
# Run the Maven command to compile and create the JAR file in the 'target' folder
RUN mvn clean package -DskipTests 

# --- STAGE 2: RUN (Use lighter JRE image) ---
# Using the official OpenJDK JRE 17 image for runtime
FROM openjdk:17-jre
WORKDIR /app
# Copy the built JAR file from the 'build' stage memory
COPY --from=build /app/target/web-proj-0.0.1-SNAPSHOT.jar app.jar

# Expose the port 
EXPOSE 8080

# Command to run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
