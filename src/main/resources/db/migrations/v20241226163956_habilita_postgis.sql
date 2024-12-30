--Liquibase formatted SQL
--changeset tom:v20241226163956
--description: Criação da extensão de geolocalização PostGIS

CREATE EXTENSION IF NOT EXISTS postgis;
CREATE EXTENSION IF NOT EXISTS postgis_topology;