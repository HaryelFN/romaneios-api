CREATE TABLE item_pedido (
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    qtd INT(11) NOT NULL,
	valor_uni DECIMAL(10,2) NOT NULL,
	valor_total DECIMAL(10,2) NOT NULL,
	id_pedido BIGINT(20) NOT NULL,
	id_produto_romaneio BIGINT(20) NOT NULL,
	FOREIGN KEY (id_pedido) REFERENCES pedido (id),
	FOREIGN KEY (id_produto_romaneio) REFERENCES produto_romaneio (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;