CREATE TABLE mov_caixa (
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    data DATE NOT NULL,
    valor DECIMAL(10,2),
    descricao VARCHAR(200) NOT NULL,
    origem VARCHAR(20) NOT NULL,
    id_pedido BIGINT(20),
    id_romaneio BIGINT(20),
    id_retirada BIGINT(20),
	FOREIGN KEY (id_pedido) REFERENCES pedido (id),
	FOREIGN KEY (id_romaneio) REFERENCES romaneio (id),
	FOREIGN KEY (id_retirada) REFERENCES retirada (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE item_mov ADD CONSTRAINT id_mov_caixa FOREIGN KEY (id_mov_caixa) REFERENCES mov_caixa(id);