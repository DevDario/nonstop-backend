FROM openjdk:21-jdk-slim AS build

WORKDIR /app

COPY target/nonstopplatformapi-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "app.jar"]