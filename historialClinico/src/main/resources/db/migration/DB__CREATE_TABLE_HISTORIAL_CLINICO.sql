CREATE TABLE cita_medica (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombres VARCHAR(255),
    apellidos VARCHAR(255),
    fechaNacimiento VARCHAR(255),
    direccion VARCHAR(255),
    telefono NUMERIC,
    PRIMARY KEY (id)
);