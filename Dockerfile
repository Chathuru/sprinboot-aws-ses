FROM eclipse-temurin:17.0.1_12-jdk-alpine

ARG VERSION
ARG DEPENDENCY=target/dependency

LABEL version=$VERSION

RUN mkdir /opt/app/

COPY target/aws-s3.jar /opt/app/

WORKDIR "/opt/app/"

CMD ["java", "-jar", "aws-s3.jar"]
