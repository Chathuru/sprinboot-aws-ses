FROM eclipse-temurin:17.0.1_12-jdk-alpine

ARG VERSION
ARG DEPENDENCY=target/dependency

LABEL version=$VERSION

RUN mkdir /opt/app/

COPY target/aws-ses.jar /opt/app/
COPY ${DEPENDENCY}/BOOT-INF/classes /opt/app
COPY ${DEPENDENCY}/BOOT-INF/lib /opt/app/lib
COPY ${DEPENDENCY}/META-INF /opt/app/META-INF

WORKDIR "/opt/app/"

CMD ["java", "-jar", "aws-ses.jar"]
