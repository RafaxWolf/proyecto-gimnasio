CREATE TABLE detalles_ejercicio
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    id_ejercicio      BIGINT NULL,
    id_rutina         BIGINT NULL,
    numero_ejercicios INT NULL,
    duracion_rutina   VARCHAR(255) NULL,
    tiempo_descanso   VARCHAR(255) NULL,
    CONSTRAINT pk_detalles_ejercicio PRIMARY KEY (id)
);

CREATE TABLE ejercicio
(
    id_ejercicio     BIGINT AUTO_INCREMENT NOT NULL,
    nombre_ejercicio VARCHAR(255) NULL,
    zona_ejercitada  VARCHAR(255) NULL,
    repeticiones     INT NULL,
    CONSTRAINT pk_ejercicio PRIMARY KEY (id_ejercicio)
);

CREATE TABLE rutina
(
    id_rutina          BIGINT AUTO_INCREMENT NOT NULL,
    nombre_rutina      VARCHAR(255) NULL,
    descripcion_rutina VARCHAR(255) NULL,
    CONSTRAINT pk_rutina PRIMARY KEY (id_rutina)
);

ALTER TABLE detalles_ejercicio
    ADD CONSTRAINT FK_DETALLES_EJERCICIO_ON_ID_EJERCICIO FOREIGN KEY (id_ejercicio) REFERENCES ejercicio (id_ejercicio);

ALTER TABLE detalles_ejercicio
    ADD CONSTRAINT FK_DETALLES_EJERCICIO_ON_ID_RUTINA FOREIGN KEY (id_rutina) REFERENCES rutina (id_rutina);