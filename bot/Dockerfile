FROM eclipse-temurin:23-jdk AS builder

WORKDIR /app

COPY gradlew .
COPY gradle/ gradle/
COPY build.gradle .
COPY settings.gradle .
COPY src/ src/

CMD ["./gradlew", "run"]