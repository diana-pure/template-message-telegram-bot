ARG BASE_BUILDER_IMAGE=eclipse-temurin:21-jdk-alpine
ARG BASE_JRE_IMAGE=eclipse-temurin:21-jre-alpine

FROM ${BASE_BUILDER_IMAGE} AS builder

RUN apk add --no-cache gradle

WORKDIR /workspace

COPY build.gradle* ./

RUN gradle --no-daemon dependencies || true

COPY src src/

RUN gradle --no-daemon bootJar


FROM ${BASE_JRE_IMAGE} AS runtime

WORKDIR /app

VOLUME ["/app/logs"]

ARG JAR_FILE=*.jar

COPY --from=builder /workspace/build/libs/${JAR_FILE} app.jar

ARG IMAGE_VERSION=1.0.0

LABEL org.opencontainers.image.authors="diana-pure"

ENTRYPOINT ["java", "-jar", "app.jar"]
