set foreign_key_checks = 0;

delete from caixa;
delete from categoria;
delete from cheque;
delete from cliente;
delete from devolucao;
delete from fornecedor;
delete from item_mov;
delete from item_pedido;
delete from limpa;
delete from mov_caixa;
delete from np;
delete from pedido;
delete from permissao;
delete from prestador;
delete from produto;
delete from produto_romaneio;
delete from retirada;
delete from romaneio;
delete from usuario;
delete from usuario_permissao;

set foreign_key_checks = 1;

alter table caixa auto_increment = 1;
alter table categoria auto_increment = 1;
alter table cheque auto_increment = 1;
alter table cliente auto_increment = 1;
alter table devolucao auto_increment = 1;
alter table fornecedor auto_increment = 1;
alter table item_mov auto_increment = 1;
alter table item_pedido auto_increment = 1;
alter table limpa auto_increment = 1;
alter table mov_caixa auto_increment = 1;
alter table np auto_increment = 1;
alter table pedido auto_increment = 1;
alter table permissao auto_increment = 1;
alter table prestador auto_increment = 1;
alter table produto auto_increment = 1;
alter table produto_romaneio auto_increment = 1;
alter table retirada auto_increment = 1;
alter table romaneio auto_increment = 1;
alter table usuario auto_increment = 1;
alter table usuario_permissao auto_increment = 1;

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

INSERT INTO permissao (id, descricao) values (29, 'ROLE_FIND_CHEQUE');
INSERT INTO permissao (id, descricao) values (30, 'ROLE_ADD_CHEQUE');
INSERT INTO permissao (id, descricao) values (31, 'ROLE_REMOVE_CHEQUE');

INSERT INTO permissao (id, descricao) values (32, 'ROLE_FIND_NP');
INSERT INTO permissao (id, descricao) values (33, 'ROLE_ADD_NP');
INSERT INTO permissao (id, descricao) values (34, 'ROLE_REMOVE_NP');

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

INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 29);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 30);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 31);

INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 32);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 33);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 34);

INSERT INTO categoria (id, nome) VALUES (1, 'Indústria');
INSERT INTO categoria (id, nome) VALUES (2, '1');
INSERT INTO categoria (id, nome) VALUES (3, '2');
INSERT INTO categoria (id, nome) VALUES (4, '3');
INSERT INTO categoria (id, nome) VALUES (5, '4');
INSERT INTO categoria (id, nome) VALUES (6, '5');
INSERT INTO categoria (id, nome) VALUES (7, '6');

INSERT INTO produto (id, nome) VALUES (1, 'Alho');
INSERT INTO produto (id, nome) VALUES (2, 'Cebola');

INSERT INTO fornecedor (id, cpf_cnpj, nome_razao, telefone, email, cep, uf, cidade, bairro, logradouro, numero) 
VALUES (1, '46587320000190', 'Fornecedor Teste 1', '62984098147', 'fornecedorteste1@gmail.com', "750460000", "GO", "Nerópolis", "Setor São Paulo", "Rua Tiradentes Q18 L25", null);

INSERT INTO cliente (id, cpf_cnpj, nome_razao, telefone, email, cep, uf, cidade, bairro, logradouro, numero)
VALUES (1, '03215312050', 'Cliente Teste 1', '62984098147', 'cliente1@gmail.com', '74550020', 'GO', 'Goiania', 'Sei la', 'Rua sem nome', null);

INSERT INTO prestador (id, cpf, nome, telefone, cep, uf, cidade, bairro, logradouro, numero)
VALUES (1, '75033623168', 'prestador Teste 1', '62984098147', '74550020', 'GO', 'Goiania', 'Sei la', 'Rua sem nome', null);

INSERT INTO romaneio (id, numero, situacao, data_entrada, valor_romaneio, valor_transporte, obs, id_fornecedor) VALUES (1, 1000, 'Aberto', '2019-12-26', 100000, 1000, 'Alho comprado na safra', 1);

INSERT INTO produto_romaneio (id, id_produto, id_categoria, valor, qtd_entrada, qtd_atual, id_romaneio) VALUES (1, 1, 1, 5.5, 10000, 10000, 1);
INSERT INTO produto_romaneio (id, id_produto, id_categoria, valor, qtd_entrada, qtd_atual, id_romaneio) VALUES (2, 2, 7, 4.5, 10000, 10000, 1);