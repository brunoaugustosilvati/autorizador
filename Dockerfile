FROM openjdk:23

COPY target/autorizador-0.0.1-SNAPSHOT.jar /app/app.jar

CMD ["java", "-jar", "/app/app.jar"]