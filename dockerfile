FROM eclipse-temurin:18-jdk
WORKDIR /app
RUN apt-get update && apt-get install -y maven
COPY . .
RUN mvn clean install -DskipTests
