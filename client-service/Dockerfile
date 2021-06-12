# Build
#FROM maven:3.6.0-jdk-11-slim AS build
#WORKDIR /app/client-service
#ADD client-service ./
#RUN mvn clean install -DskipTests
#COPY ./target/client-service-0.0.1-SNAPSHOT.jar /app/client-service.jar

# Deploy
FROM openjdk:8-jdk-alpine
COPY client-service/target/client-service-0.0.1-SNAPSHOT.jar /client-service.jar
CMD ["java", "-jar", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005", "./client-service.jar"]