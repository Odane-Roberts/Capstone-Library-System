# Use an official OpenJDK runtime as the base image
FROM openjdk:17

# Expose a port (if your application listens on a specific port)
EXPOSE 8093


# Set the working directory inside the container
ADD target/*.jar bag-service.jar

# Copy the application JAR file into the container


# Define the command to run your Java application
ENTRYPOINT ["java", "-jar", "/bag-service.jar"]