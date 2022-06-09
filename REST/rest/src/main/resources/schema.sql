DROP TABLE vetbl_facturas;

CREATE TABLE vetbl_facturas (
  numero INT NOT NULL,
  concepto VARCHAR(255) NULL,
  importe DOUBLE NULL,
  CONSTRAINT pk_vetbl_facturas PRIMARY KEY (numero)
);