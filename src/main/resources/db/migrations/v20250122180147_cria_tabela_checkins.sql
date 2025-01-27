--Liquibase formatted SQL
--changeset tom:v20250122180147
--description: Criação da tabela de check-ins correlacionada com as tabelas de academia e usuários

CREATE TABLE check_ins (
  id UUID NOT NULL,
  creation_date TIMESTAMP(6),
  gym_id UUID,
  user_id UUID,
  validation_date TIMESTAMP(6),
  PRIMARY KEY (id)
);

ALTER TABLE check_ins
  ADD CONSTRAINT fk_check_ins_gym_id
  FOREIGN KEY (gym_id)
  REFERENCES gyms(id);

ALTER TABLE check_ins
  ADD CONSTRAINT fk_check_ins_user_id
  FOREIGN KEY (user_id)
  REFERENCES users(id);