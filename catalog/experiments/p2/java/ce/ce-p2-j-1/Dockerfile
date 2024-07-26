FROM amazoncorretto:21-alpine-jdk
VOLUME /tmp

WORKDIR /app

COPY android-json-0.0.20131108.vaadin1.jar .
COPY Main.class .

CMD ["java", "-cp", ".:android-json-0.0.20131108.vaadin1.jar", "Main"]