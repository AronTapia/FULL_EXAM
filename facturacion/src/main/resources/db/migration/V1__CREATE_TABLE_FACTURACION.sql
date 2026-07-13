CREATE TABLE facturacion (
    id BIGINT NOT NULL AUTO_INCREMENT,
    pri_nombre VARCHAR(15) NOT NULL,
    ap_paterno VARCHAR(15) NOT NULL,
    tipo_consulta VARCHAR(25) NOT NULL,
    precio_consulta VARCHAR(25) NOT NULL,
    fecha VARCHAR(10) NOT NULL,
    PRIMARY KEY (id)
);
