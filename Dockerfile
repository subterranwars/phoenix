FROM openjdk:8-jre-alpine

ARG JAR_FILE
COPY target/${JAR_FILE} /opt/app.jar

RUN apk add --no-cache bash
RUN apk add --no-cache curl

HEALTHCHECK --interval=5s --timeout=5s --retries=3 \
  CMD curl --silent --fail http://localhost:8081/health || exit 1

ENTRYPOINT ["java", "-jar", "/opt/app.jar"]