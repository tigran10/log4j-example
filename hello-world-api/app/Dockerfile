# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the build artifact from the Gradle build
COPY build/libs/app-0.1.0.jar /app/hello-world-api.jar

# Expose the port that the app runs on
EXPOSE 8080

# Add an environment variable for the Spring profile
ENV SPRING_PROFILES_ACTIVE=development

# Run the app with the appropriate profile
ENTRYPOINT ["java", "-jar", "/app/hello-world-api.jar", "--spring.profiles.active=${SPRING_PROFILES_ACTIVE}"]
