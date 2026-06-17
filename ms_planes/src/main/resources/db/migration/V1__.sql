CREATE TABLE pagos
(
    pago_id               BIGINT AUTO_INCREMENT NOT NULL,
    tipo_pago             VARCHAR(255) NULL,
    num_tarjeta           VARCHAR(255) NULL,
    fecha_vencimiento     VARCHAR(255) NULL,
    cvc                   INT NULL,
    direccion_facturacion VARCHAR(255) NULL,
    codigo_postal         VARCHAR(255) NULL,
    id_cliente            BIGINT NULL,
    CONSTRAINT pk_pagos PRIMARY KEY (pago_id)
);

CREATE TABLE planes
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    nombre_plan      VARCHAR(255) NULL,
    precio_plan      DECIMAL NULL,
    descripcion_plan VARCHAR(255) NULL,
    beneficios       VARCHAR(255) NULL,
    CONSTRAINT pk_planes PRIMARY KEY (id)
);

CREATE TABLE suscripciones
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    id_cliente   BIGINT NULL,
    id_plan      BIGINT NULL,
    id_pago      BIGINT NULL,
    fecha_inicio date NULL,
    fecha_fin    date NULL,
    estado       VARCHAR(255) NULL,
    CONSTRAINT pk_suscripciones PRIMARY KEY (id)
);

ALTER TABLE suscripciones
    ADD CONSTRAINT uc_suscripciones_id_pago UNIQUE (id_pago);

ALTER TABLE suscripciones
    ADD CONSTRAINT FK_SUSCRIPCIONES_ON_ID_PAGO FOREIGN KEY (id_pago) REFERENCES pagos (pago_id);

ALTER TABLE suscripciones
    ADD CONSTRAINT FK_SUSCRIPCIONES_ON_ID_PLAN FOREIGN KEY (id_plan) REFERENCES planes (id);