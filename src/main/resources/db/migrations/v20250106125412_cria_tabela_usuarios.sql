--Liquibase formatted SQL
--changeset tom:v20250106225355
--description: Criação da tabela de usuários

CREATE TABLE users (
  id UUID NOT NULL,
  email VARCHAR(255) NOT NULL,
  name VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);