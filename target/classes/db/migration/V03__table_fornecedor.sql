CREATE TABLE fornecedor (
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

INSERT INTO fornecedor (id, cpf_cnpj, nome_razao, telefone, email, cep, uf, cidade, bairro, logradouro, numero) 
VALUES (1, '46587320000190', 'Fornecedor Teste 1', '62984098147', 'fornecedorteste1@gmail.com', "750460000", "GO", "Nerópolis", "Setor São Paulo", "Rua Tiradentes Q18 L25", null);

INSERT INTO fornecedor (id, cpf_cnpj, nome_razao, telefone, email, cep, uf, cidade, bairro, logradouro, numero) 
VALUES (2, '25860832000109', 'Fornecedor Teste 2', '62984098111', 'fornecedorteste2@gmail.com', "750460000", "GO", "Nerópolis", "Setor São Paulo", "Rua Tiradentes Q18 L25", null);