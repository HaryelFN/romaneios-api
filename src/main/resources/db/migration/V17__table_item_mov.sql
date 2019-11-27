CREATE TABLE item_mov (
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    tipo VARCHAR(20) NOT NULL,
    valor DECIMAL(10,2),
    id_caixa BIGINT(20),
    id_cheque BIGINT(20),
    id_np BIGINT(20),
    id_mov_caixa BIGINT(20) NOT NULL,
	FOREIGN KEY (id_caixa) REFERENCES caixa (id),
	FOREIGN KEY (id_cheque) REFERENCES cheque (id),
	FOREIGN KEY (id_np) REFERENCES np (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;