CREATE TABLE categoria (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(20) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO categoria (id, nome) VALUES (1, 'Indústria');
INSERT INTO categoria (id, nome) VALUES (2, '1');
INSERT INTO categoria (id, nome) VALUES (3, '2');
INSERT INTO categoria (id, nome) VALUES (4, '3');
INSERT INTO categoria (id, nome) VALUES (5, '4');
INSERT INTO categoria (id, nome) VALUES (6, '5');
INSERT INTO categoria (id, nome) VALUES (7, '6');

CREATE TABLE produto (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO produto (id, nome) VALUES (1, 'Alho');
INSERT INTO produto (id, nome) VALUES (2, 'Cebola');