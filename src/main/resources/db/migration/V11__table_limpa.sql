CREATE TABLE limpa (
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    qtd INT(11) NOT NULL,
	valor DECIMAL(10,2),
    data_inicio DATE NOT NULL,
    data_conclusao DATE,
	id_item_pedido BIGINT(20) NOT NULL,
	FOREIGN KEY (id_item_pedido) REFERENCES item_pedido (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;