# Stage 1: Build the application using Maven
FROM maven:3.8.1-openjdk-17 AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml file and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM openjdk:17-jdk

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build stage to the target directory
COPY --from=build /app/target/gymApp-0.0.1.jar app.jar

# Expose the port your application runs on
EXPOSE 8080

# Specify the entry point for the application
ENTRYPOINT ["java","-jar","/app/app.jar"]
