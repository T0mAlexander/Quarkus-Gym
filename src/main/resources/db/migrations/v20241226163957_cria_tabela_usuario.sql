--Liquibase formatted SQL
--changeset tom:v20241226163957
--description: Criação da tabela de usuários

CREATE TABLE users (
  id bigint NOT NULL,
  email VARCHAR(255),
  name VARCHAR(255),
  password VARCHAR(255),
  PRIMARY KEY (id)
);

CREATE SEQUENCE users_SEQ START WITH 1 INCREMENT BY 50;