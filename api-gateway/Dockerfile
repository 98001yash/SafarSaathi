FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline


COPY src ./src

CMD ["./mvnw", "spring-boot:run"]