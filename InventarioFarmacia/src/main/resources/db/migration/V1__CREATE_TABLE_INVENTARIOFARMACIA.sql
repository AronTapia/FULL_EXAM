CREATE TABLE inventario_farmacia (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_medicamento VARCHAR(100) NOT NULL,
    fecha_caducidad DATE,
    cantidad_medicamento INT NOT NULL,
    precio_medicamento INT NOT NULL
);