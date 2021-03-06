CREATE TABLE pedido (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	numero INT(11) NOT NULL UNIQUE,
	data_pedido DATE NOT NULL,
	valor_total DECIMAL(10,2) NOT NULL,
	id_cliente BIGINT(20) NOT NULL,
	situacao VARCHAR(50) NOT NULL,
	FOREIGN KEY (id_cliente) REFERENCES cliente (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;