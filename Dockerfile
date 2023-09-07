FROM eclipse-temurin:17.0.1_12-jdk-alpine


RUN mkdir /opt/app/

COPY target/aws-ses.jar /opt/app/

WORKDIR "/opt/app/"

CMD ["java", "-jar", "aws-ses.jar"]
