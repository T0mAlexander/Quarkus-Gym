FROM quay.io/quarkus/ubi-quarkus-mandrel-builder-image:jdk-21 AS build

USER root

RUN microdnf install -y findutils

COPY --chown=quarkus:quarkus gradlew /code/gradlew
COPY --chown=quarkus:quarkus gradle /code/gradle
COPY --chown=quarkus:quarkus build.gradle /code
COPY --chown=quarkus:quarkus settings.gradle /code
COPY --chown=quarkus:quarkus gradle.properties /code

USER quarkus
WORKDIR /code

COPY ./src /code/src
RUN ./gradlew build -Dquarkus.native.enabled=true

FROM quay.io/quarkus/quarkus-micro-image:3.0

WORKDIR /quarkus

COPY --from=build /code/build/*-runner /quarkus/app
COPY --from=build /code/src/main/resources/keys /quarkus/keys
RUN chmod 775 /quarkus

EXPOSE 8080

CMD ["./app", "-Dquarkus.http.host=0.0.0.0"]