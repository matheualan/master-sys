CREATE TABLE tb_entities
(
    id          BIGSERIAL PRIMARY KEY,
    entity_name VARCHAR(255) NOT NULL,
    timestamp   TIMESTAMP WITH TIME ZONE
);