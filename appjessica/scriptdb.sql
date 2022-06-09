CREATE DATABASE appjessicadb;

USE appjessicadb;

#DROP DATABASE appjessicadb;

CREATE TABLE patbl_ECiviles (
	ecivil_id  		INT AUTO_INCREMENT,
    ecivil_desc 	VARCHAR(50) NOT NULL,
    estado_fl		SMALLINT NOT NULL,
    
    PRIMARY KEY (ecivil_id)
);

INSERT INTO patbl_ECiviles(ecivil_desc, estado_fl)
VALUES
	("Soltero/a", 0),
    ("Casado/a", 1)
	;

SELECT * FROM patbl_ECiviles;



CREATE TABLE petbl_Clientes (
	id 				INT AUTO_INCREMENT,
	ecivil_id 		INT NOT NULL,
    cliente_nm		VARCHAR(50) NOT NULL,
    cliente_app		VARCHAR(50) NOT NULL,
	cliente_apm 	VARCHAR(60) NOT NULL,
    direccion_desc	VARCHAR(120),
    telefono_desc  	VARCHAR(50),
    fch_nacimiento	DATE,
    cliente_email    VARCHAR(320) NOT NULL,
    estado_fl		INT NOT NULL,
    
    PRIMARY KEY (id),
    FOREIGN KEY (ecivil_id) REFERENCES patbl_ECiviles(ecivil_id)
);
#DROP TABLE petbl_clientes;

INSERT INTO petbl_clientes (ecivil_id, cliente_nm, cliente_app, cliente_apm, direccion_desc, telefono_desc, fch_nacimiento, cliente_email, estado_fl)
VALUES
    (1, "Jessica", "Aquino", "Torrez", "C. Chaco #12", "77306454", "1999-12-18", "jessica.aquino.torrez@gmail.com", 1),
    (2, "Mariana", "Arnez", "Andia", "Calle #26", "75049821", "1999-02-05", "mariana.anez@gmail.com" , 1)
    ;

SELECT * FROM petbl_Clientes;

CREATE TABLE patbl_Categorias(
	categoria_id INT AUTO_INCREMENT, 
	categoria_nm VARCHAR (60),
	estado_fl INT,
    
    PRIMARY KEY(categoria_id)
);

INSERT INTO patbl_Categorias (categoria_nm, estado_fl)
VALUES
	("Categoria 1", 0),
	("Categoria 2", 0);
    
SELECT * FROM patbl_Categorias;

CREATE TABLE ivtbl_Productos(
	producto_id INT AUTO_INCREMENT,
	producto_nm VARCHAR(60) NOT NULL,
	categoria_id INT NOT NULL,
	unidad_medida INT NOT NULL,
	stock_actual INT NOT NULL,
	precio_vta  INT NOT NULL,
	estado_fl INT NOT NULL,
    
    PRIMARY KEY(producto_id), 
    FOREIGN KEY (categoria_id) REFERENCES patbl_Categorias(categoria_id)
);

INSERT INTO ivtbl_Productovetbl_facturass(producto_nm, categoria_id, unidad_medida, stock_actual, precio_vta, estado_fl)
VALUES
	("Producto 1", 1, 1, 15, 20, 0),
    ("Producto 2", 2, 1, 25, 35, 0);

SELECT * FROM ivtbl_Productos;