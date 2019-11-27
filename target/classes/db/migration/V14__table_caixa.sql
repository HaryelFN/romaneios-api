CREATE TABLE caixa (
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    data DATE NOT NULL,
	valor DECIMAL(10,2)
    -- operacao VARCHAR(30) NOT NULL,
    -- descricao VARCHAR(50),
	-- id_pedido BIGINT(20),
	-- id_romaneio BIGINT(20),
	-- id_retirada BIGINT(20),
	-- id_pag_ped BIGINT(20),
	-- FOREIGN KEY (id_pedido) REFERENCES pedido (id),
	-- FOREIGN KEY (id_romaneio) REFERENCES romaneio (id),
	-- FOREIGN KEY (id_retirada) REFERENCES retirada (id),
	-- FOREIGN KEY (id_pag_ped) REFERENCES pag_ped (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;