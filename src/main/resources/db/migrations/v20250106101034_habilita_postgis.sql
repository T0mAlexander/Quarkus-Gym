--Liquibase formatted SQL
--changeset tom:v20250106101034
--description: Habilita a extensão PostGIS para geolocalização

CREATE EXTENSION IF NOT EXISTS postgis;
CREATE EXTENSION IF NOT EXISTS postgis_topology;