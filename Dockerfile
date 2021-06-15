FROM openjdk:8-jdk-alpine
EXPOSE 8082
COPY target/soft-ib-help-center-0.0.1-SNAPSHOT.war soft-ib-help-center-0.0.1-SNAPSHOT.war
ENTRYPOINT ["java","-jar","/soft-ib-help-center-0.0.1-SNAPSHOT.war"]