CREATE TABLE produto_romaneio (
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	id_produto BIGINT(20) NOT NULL,
	id_categoria BIGINT(20) NOT NULL,
	valor DECIMAL(10,2) NOT NULL,
    qtd_entrada INT(11) NOT NULL,
	qtd_atual INT(11) NOT NULL,
	id_romaneio BIGINT(20) NOT NULL,
	FOREIGN KEY (id_produto) REFERENCES produto (id),
	FOREIGN KEY (id_categoria) REFERENCES categoria (id),
	FOREIGN KEY (id_romaneio) REFERENCES romaneio (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO produto_romaneio (id, id_produto, id_categoria, valor, qtd_entrada, qtd_atual, id_romaneio) VALUES (1, 1, 1, 5.5, 1000, 1000, 1);
INSERT INTO produto_romaneio (id, id_produto, id_categoria, valor, qtd_entrada, qtd_atual, id_romaneio) VALUES (2, 2, 7, 4.5, 1000, 1000, 1);