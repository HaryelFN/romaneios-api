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

INSERT INTO romaneio (id, numero, situacao, data_entrada, valor_romaneio, valor_transporte, obs, id_fornecedor) VALUES (1, 1, 'Aberto', '2019-12-26', 10000, 1000, 'Alho comprado na safra', 1);