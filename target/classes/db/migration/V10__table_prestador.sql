CREATE TABLE prestador (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	cpf VARCHAR(11) NOT NULL UNIQUE,
	nome VARCHAR(60) NOT NULL,
	telefone VARCHAR(60),
	cep VARCHAR(20) NOT NULL,
	uf VARCHAR(2) NOT NULL,
	cidade VARCHAR(30) NOT NULL,
    bairro VARCHAR(30) NOT NULL,
    logradouro VARCHAR(50) NOT NULL,
    numero VARCHAR(11)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO prestador (id, cpf, nome, telefone, cep, uf, cidade, bairro, logradouro, numero)
 VALUES (1, '75033623168', 'prestador Teste 1', '62984098147', '74550020', 'GO', 'Goiania', 'Sei la', 'Rua sem nome', null);