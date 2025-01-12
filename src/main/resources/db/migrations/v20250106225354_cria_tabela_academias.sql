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

CREATE SEQUENCE gyms_seq
  START WITH 1
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE
  CACHE 1;

ALTER TABLE gyms ALTER COLUMN id SET DEFAULT nextval('gyms_seq');