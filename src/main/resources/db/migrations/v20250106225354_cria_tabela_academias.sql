--Liquibase formatted SQL
--changeset tom:v20250106225354
--description: Criação da tabela de academias

CREATE TABLE gyms (
  id UUID NOT NULL,
  name VARCHAR(255),
  email VARCHAR(255) NOT NULL,
  phone VARCHAR(255) NOT NULL,
  location GEOMETRY(POINT, 4326),
  description VARCHAR(255),
  PRIMARY KEY (id)
);
