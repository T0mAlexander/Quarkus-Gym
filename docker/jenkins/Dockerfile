FROM jenkins/jenkins:2.496-alpine-jdk21

USER root
ENV TZ=America/Sao_Paulo
ENV PATH="$PATH:/opt/liquibase:/opt/gradle/bin"

# Instalando Gradle e Liquibase CLI
RUN apk update && apk add --no-cache curl tzdata docker
