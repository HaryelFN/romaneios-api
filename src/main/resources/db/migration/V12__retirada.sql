CREATE TABLE retirada (
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    data_retirada DATE NOT NULL,
    qtd_retirada INT(11) NOT NULL,
	valor_kg DECIMAL(10,2),
	id_prestador BIGINT(20) NOT NULL,
	id_limpa BIGINT(20) NOT NULL,
	FOREIGN KEY (id_prestador) REFERENCES prestador (id),
	FOREIGN KEY (id_limpa) REFERENCES limpa (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;