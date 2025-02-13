FROM quay.io/quarkus/ubi-quarkus-mandrel-builder-image:jdk-21 AS build

USER root

RUN microdnf install -y findutils

COPY gradlew /code/gradlew
COPY gradle /code/gradle
COPY build.gradle /code
COPY settings.gradle /code
COPY gradle.properties /code

WORKDIR /code

COPY ./src /code/src
COPY src/main/resources/application.properties /code/src/main/resources/application.properties
COPY src/main/resources /code/src/main/resources

RUN ./gradlew build -Dquarkus.native.enabled=true

FROM quay.io/quarkus/quarkus-micro-image:3.0

WORKDIR /quarkus

COPY --from=build /code/build/*-runner /quarkus/app
COPY --from=build /code/src/main/resources/keys /quarkus/keys
RUN chmod 775 /quarkus

EXPOSE 8080

CMD ["./app", "-Dquarkus.http.host=0.0.0.0"]
