DROP TABLE vetbl_productos;

CREATE TABLE vetbl_productos (
  codigo INT NOT NULL,
  nombre VARCHAR(255) NULL,
  costo DOUBLE NULL,
  precio DOUBLE NULL,
  CONSTRAINT pk_vetbl_productos PRIMARY KEY (codigo)
);