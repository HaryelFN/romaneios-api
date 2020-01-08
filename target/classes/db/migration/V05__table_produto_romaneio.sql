CREATE TABLE produto_romaneio (
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	id_produto BIGINT(20) NOT NULL,
	id_categoria BIGINT(20) NOT NULL,
	valor DECIMAL(10,2) NOT NULL,
    qtd_entrada INT(11) NOT NULL,
	qtd_atual INT(11) NOT NULL,
	qtd_pendente INT(11) NOT NULL DEFAULT 0,
	id_romaneio BIGINT(20) NOT NULL,
	FOREIGN KEY (id_produto) REFERENCES produto (id),
	FOREIGN KEY (id_categoria) REFERENCES categoria (id),
	FOREIGN KEY (id_romaneio) REFERENCES romaneio (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;