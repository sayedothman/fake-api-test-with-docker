FROM maven:3.9.3-eclipse-temurin-18

WORKDIR /app

# Copy only pom.xml first to leverage Docker cache for dependencies
COPY pom.xml .

# Download dependencies only (preload for caching)
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Default command: run tests at container runtime
CMD ["mvn", "clean", "test"]
