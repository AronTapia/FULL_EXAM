CREATE TABLE notificacion_paciente (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_paciente BIGINT NOT NULL,
    rut_paciente VARCHAR(100) NOT NULL,
    nombre_paciente VARCHAR(100) NOT NULL,
    medico_responsable VARCHAR(100) NOT NULL,
    fecha DATE,
    mensaje VARCHAR(250) NOT NULL
);