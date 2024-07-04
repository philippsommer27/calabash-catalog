FROM openjdk:21-slim

WORKDIR /app

COPY target/jpa-experiment-1.0-SNAPSHOT.jar /app/app.jar
COPY target/lib /app/lib

CMD ["java", "-jar", "app.jar"]