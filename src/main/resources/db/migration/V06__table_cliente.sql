CREATE TABLE cliente (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	cpf_cnpj VARCHAR(18) NOT NULL UNIQUE,
	nome_razao VARCHAR(60) NOT NULL,
	telefone VARCHAR(30) UNIQUE,
	email VARCHAR(60) UNIQUE,
	cep VARCHAR(20) NOT NULL,
	uf VARCHAR(2) NOT NULL,
	cidade VARCHAR(30) NOT NULL,
    bairro VARCHAR(30) NOT NULL,
    logradouro VARCHAR(50) NOT NULL,
    numero VARCHAR(11)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO cliente (id, cpf_cnpj, nome_razao, telefone, email, cep, uf, cidade, bairro, logradouro, numero)
 VALUES (1, '03215312050', 'Cliente Teste 1', '62984098147', 'cliente1@gmail.com', '74550020', 'GO', 'Goiania', 'Sei la', 'Rua sem nome', null);