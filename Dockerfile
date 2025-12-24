# Use official OpenJDK image
FROM eclipse-temurin:17-jdk-alpine

# Add JAR file
COPY target/mechyam-backend.jar /app/mechyam-backend.jar

# Set working directory
WORKDIR /app

# Expose port
EXPOSE 8080

# Run the JAR
ENTRYPOINT ["java","-jar","mechyam-backend.jar"]
