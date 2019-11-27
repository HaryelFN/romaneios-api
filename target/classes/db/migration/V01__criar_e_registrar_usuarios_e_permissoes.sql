  CREATE TABLE usuario (
	id BIGINT(20) PRIMARY KEY,
	nome VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	senha VARCHAR(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE permissao (
	id BIGINT(20) PRIMARY KEY,
	descricao VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE usuario_permissao (
	id_usuario BIGINT(20) NOT NULL,
	id_permissao BIGINT(20) NOT NULL,
	PRIMARY KEY (id_usuario, id_permissao),
	FOREIGN KEY (id_usuario) REFERENCES usuario(id),
	FOREIGN KEY (id_permissao) REFERENCES permissao(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO usuario (id, nome, email, senha) values (1, 'Administrador', 'admin@gmail.com', '$2a$10$X607ZPhQ4EgGNaYKt3n4SONjIv9zc.VMWdEuhCuba7oLAL5IvcL5.');

INSERT INTO permissao (id, descricao) values (1, 'ROLE_FIND_CATEGORIA');
INSERT INTO permissao (id, descricao) values (2, 'ROLE_ADD_CATEGORIA');
INSERT INTO permissao (id, descricao) values (3, 'ROLE_REMOVE_CATEGORIA');

INSERT INTO permissao (id, descricao) values (4, 'ROLE_FIND_PRODUTO');
INSERT INTO permissao (id, descricao) values (5, 'ROLE_ADD_PRODUTO');
INSERT INTO permissao (id, descricao) values (6, 'ROLE_REMOVER_PRODUTO');

INSERT INTO permissao (id, descricao) values (7, 'ROLE_FIND_CLIENTE');
INSERT INTO permissao (id, descricao) values (8, 'ROLE_ADD_CLIENTE');
INSERT INTO permissao (id, descricao) values (9, 'ROLE_REMOVE_CLIENTE');

INSERT INTO permissao (id, descricao) values (10, 'ROLE_FIND_FORNECEDOR');
INSERT INTO permissao (id, descricao) values (11, 'ROLE_ADD_FORNECEDOR');
INSERT INTO permissao (id, descricao) values (12, 'ROLE_REMOVER_FORNECEDOR');

INSERT INTO permissao (id, descricao) values (13, 'ROLE_FIND_ROMANEIO');
INSERT INTO permissao (id, descricao) values (14, 'ROLE_ADD_ROMANEIO');
INSERT INTO permissao (id, descricao) values (15, 'ROLE_REMOVE_ROMANEIO');

INSERT INTO permissao (id, descricao) values (16, 'ROLE_FIND_PEDIDO');
INSERT INTO permissao (id, descricao) values (17, 'ROLE_ADD_PEDIDO');
INSERT INTO permissao (id, descricao) values (18, 'ROLE_REMOVE_PEDIDO');

INSERT INTO permissao (id, descricao) values (19, 'ROLE_FIND_PRESTADOR');
INSERT INTO permissao (id, descricao) values (20, 'ROLE_ADD_PRESTADOR');
INSERT INTO permissao (id, descricao) values (21, 'ROLE_REMOVE_PRESTADOR');

INSERT INTO permissao (id, descricao) values (22, 'ROLE_FIND_CAIXA');
INSERT INTO permissao (id, descricao) values (23, 'ROLE_ADD_CAIXA');
INSERT INTO permissao (id, descricao) values (24, 'ROLE_REMOVE_CAIXA');

INSERT INTO permissao (id, descricao) values (25, 'ROLE_FIND_LIMPA');
INSERT INTO permissao (id, descricao) values (26, 'ROLE_ADD_LIMPA');
INSERT INTO permissao (id, descricao) values (27, 'ROLE_REMOVE_LIMPA');

INSERT INTO permissao (id, descricao) values (28, 'ROLE_DASHBORD');

-- admin
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 1);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 2);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 3);

INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 4);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 5);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 6);

INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 7);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 8);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 9);

INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 10);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 11);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 12);

INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 13);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 14);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 15);

INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 16);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 17);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 18);

INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 19);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 20);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 21);

INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 22);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 23);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 24);

INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 25);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 26);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 27);

INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 28);