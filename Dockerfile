FROM openjdk:18.0.2.1-slim-buster
LABEL authors="gorchanyuk"

ENV PROJECT_NAME=my-project
ENV PROJECT_VERSION=0.1.0

COPY . /usr/src/myapp
WORKDIR /usr/src/myapp

EXPOSE 8080

CMD  ["java", "-jar", "./management-system-app/target/management-system-exec.jar"]


