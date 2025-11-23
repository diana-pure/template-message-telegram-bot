ARG BASE_IMAGE=amazoncorretto:17-alpine-jdk

FROM ${BASE_IMAGE}

WORKDIR /app

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} app.jar

ARG IMAGE_VERSION=1.0.0

LABEL org.opencontainers.image.authors="diana-pure"

ENTRYPOINT ["java","-XX:RAMPcentage=75.0", "-jar", "app.jar"]