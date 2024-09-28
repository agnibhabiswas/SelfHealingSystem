# Base image with OpenJDK
FROM openjdk:21-slim AS build

# Install Maven and curl in the build stage
RUN apt-get update && \
    apt-get install -y maven curl && \
    apt-get clean

WORKDIR /app
COPY . .

# Build the application
RUN mvn package

# Run Stage
FROM openjdk:21-slim

# Install curl in the runtime image for health checks
RUN apt-get update && \
    apt-get install -y curl && \
    apt-get clean

WORKDIR /app
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

# Healthcheck using curl to ensure the app is running
HEALTHCHECK --interval=30s --timeout=10s --retries=3 CMD curl -f http://localhost:8080/health || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
