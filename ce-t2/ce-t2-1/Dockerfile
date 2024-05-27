FROM amazoncorretto:21-alpine-jdk
VOLUME /tmp

RUN apk add --no-cache maven
RUN apk add --no-cache bash

COPY . .
COPY target/*.jar app.jar
RUN chmod +x /run.sh
CMD ["./run.sh"]