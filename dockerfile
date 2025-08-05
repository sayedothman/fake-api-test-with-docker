FROM eclipse-temurin:18-jdk AS build
WORKDIR /app
RUN apt-get update && apt-get install -y maven
COPY . .
RUN mvn clean install -DskipTests
