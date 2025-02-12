FROM jenkins/jenkins:2.496-alpine-jdk21

USER root
ENV TZ=America/Sao_Paulo
ENV PATH="$PATH:/opt/liquibase"

RUN apk update && apk add --no-cache curl tzdata docker && \
    wget https://github.com/liquibase/liquibase/releases/download/v4.29.1/liquibase-4.29.1.tar.gz && mkdir -p /opt/liquibase && \
    tar -xvf liquibase-4.29.1.tar.gz -C /opt/liquibase && rm liquibase-4.29.1.tar.gz