ARG BASE_BUILDER_IMAGE=amazoncorretto:21-alpine-jdk
ARG BASE_JRE_IMAGE=eclipse-temurin:21-jre-alpine

FROM ${BASE_BUILDER_IMAGE} AS builder

RUN apk add --no-cache gradle

WORKDIR /workspace

# Copy build scripts first so dependency downloads remain cached
COPY build.gradle* settings.gradle* gradle.properties* ./
COPY gradle ./gradle
COPY gradlew ./

# Warm Gradle caches (ignore failures so builds still proceed)
RUN gradle --no-daemon dependencies || true

# Copy application sources and build using the Gradle distribution baked into the base image
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
