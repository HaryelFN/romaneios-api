CREATE TABLE romaneio (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	numero INT(11) NOT NULL UNIQUE,
	situacao VARCHAR(50) NOT NULL, 
	data_entrada DATE NOT NULL,
	valor_romaneio DECIMAL(10,2) NOT NULL,
	valor_transporte DECIMAL(10,2) DEFAULT 0,
	obs VARCHAR(50),
	id_fornecedor BIGINT(20) NOT NULL,
	FOREIGN KEY (id_fornecedor) REFERENCES fornecedor (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;