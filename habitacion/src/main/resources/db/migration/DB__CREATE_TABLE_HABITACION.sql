CREATE TABLE habitacion (
    id BIGINT NOT NULL AUTO_INCREMENT,
    tipo VARCHAR(255),
    capacidad VARCHAR(255),
    disponibilidad VARCHAR(255),
    costoDia NUMERIC(255),
    PRIMARY KEY (id)
);