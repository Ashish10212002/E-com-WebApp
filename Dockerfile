# --- STAGE 1: BUILD (Creates the JAR) ---
# Using Eclipse Temurin JDK 17 Alpine image for a reliable build environment
FROM eclipse-temurin:17-jdk-alpine AS build 
WORKDIR /app
# Copy the source code into the build container
COPY . .
# Run the Maven command to compile and package the application
RUN mvn clean package -DskipTests 

# --- STAGE 2: RUN (Use lighter JRE image) ---
# Using the Eclipse Temurin JRE 17 Alpine image for a small, fast runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Copy the built JAR file from the 'build' stage memory
COPY --from=build /app/target/web-proj-0.0.1-SNAPSHOT.jar app.jar

# Expose the port 
EXPOSE 8080

# Command to run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
