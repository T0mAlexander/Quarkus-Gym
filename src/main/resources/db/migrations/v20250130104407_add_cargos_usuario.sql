--Liquibase formatted SQL
--changeset tom:v20250130104407
--description: Adiciona coluna de cargos na tabela do usuário

ALTER TABLE users
  ADD COLUMN role VARCHAR(255) NOT NULL;

ALTER TABLE users
  ADD CONSTRAINT check_role CHECK (role IN ('ADMIN', 'USER'))