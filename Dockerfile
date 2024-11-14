FROM maven:3.9.9-eclipse-temurin-23 as builder

WORKDIR /app

COPY . .

RUN mvn clean install -DskipTests

FROM openjdk:23

WORKDIR /app

COPY --from=builder /app/target/autorizador-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]