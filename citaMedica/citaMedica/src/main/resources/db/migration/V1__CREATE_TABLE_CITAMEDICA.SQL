CREATE TABLE cita_medica (
    id BIGINT NOT NULL AUTO_INCREMENT,
    tipo VARCHAR(255),
    fecha DATETIME,
    ubicacion VARCHAR(255),
    nombre_doctor VARCHAR(255),
    PRIMARY KEY (id)
);