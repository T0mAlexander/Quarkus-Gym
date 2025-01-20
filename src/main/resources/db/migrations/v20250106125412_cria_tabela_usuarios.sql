--Liquibase formatted SQL
--changeset tom:v20250106125412
--description: Criação da tabela de usuários

CREATE TABLE USERS (
    ID UUID NOT NULL,
    EMAIL VARCHAR(255) NOT NULL,
    NAME VARCHAR(255) NOT NULL,
    PASSWORD VARCHAR(255) NOT NULL,
    PRIMARY KEY (ID)
);
