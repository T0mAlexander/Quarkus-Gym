--Liquibase formatted SQL
--changeset tom:v20250106225354
--description: Criação da tabela de academias

CREATE TABLE gyms (
  latitude FLOAT(53),
  longitude FLOAT(53),
  id bigint NOT NULL,
  description VARCHAR(255),
  email VARCHAR(255) NOT NULL,
  name VARCHAR(255),
  phone VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);