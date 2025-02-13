FROM jenkins/jenkins:2.496-alpine-jdk21

USER root
ENV TZ=America/Sao_Paulo
ENV PATH="/opt/liquibase:$PATH"

RUN apk add --no-cache curl tzdata docker && \
    wget -q https://github.com/liquibase/liquibase/releases/download/v4.29.1/liquibase-4.29.1.tar.gz && \
    mkdir -p /opt/liquibase && \
    tar -xzf liquibase-4.29.1.tar.gz -C /opt/liquibase && \
    rm liquibase-4.29.1.tar.gz && \
    chmod +x /opt/liquibase/liquibase
