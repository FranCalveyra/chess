# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk-slim

# Install procps to get the kill command and necessary libraries for JavaFX
RUN apt-get update && apt-get install -y procps libfreetype6 libfontconfig1

# Set the working directory in the container
WORKDIR /app

# Copy the Gradle wrapper and project files into the container
COPY ./ /app

# Make the Gradle wrapper executable
RUN chmod +x gradlew

# Install necessary dependencies and apply spotless formatting
RUN ./gradlew spotlessApply

# Build the project
RUN ./gradlew build

# Expose the port that JPro will run on
EXPOSE 8080

# Run the jproRun task
CMD ["./gradlew", "jproRun"]