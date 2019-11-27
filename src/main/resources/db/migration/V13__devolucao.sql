CREATE TABLE devolucao (
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    data_devolucao DATE NOT NULL,
    qtd_devolucao INT(11) NOT NULL,
	valor DECIMAL(10,2),
    data_pag DATE,
	id_retirada BIGINT(20) NOT NULL,
	FOREIGN KEY (id_retirada) REFERENCES retirada (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;