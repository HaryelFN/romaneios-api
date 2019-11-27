CREATE TABLE cheque (
    id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    dt_ven DATE NOT NULL,
    dt_pag DATE,
	valor DECIMAL(10,2),
    agencia VARCHAR(100),
	banco VARCHAR(100),
    conta VARCHAR(100),
    num_cheque VARCHAR(100),
    emitente VARCHAR(100)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;