CREATE TABLE cliente
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    nombres   VARCHAR(255) NULL,
    apellidos VARCHAR(255) NULL,
    run       VARCHAR(255) NULL,
    correo    VARCHAR(255) NULL,
    fecha_nac date NULL,
    id_plan   BIGINT NULL,
    CONSTRAINT pk_cliente PRIMARY KEY (id)
);