DELIMITER $

-- ITEM_PED
-- CREATE TRIGGER before_save_item_pedido BEFORE INSERT ON item_pedido
-- FOR EACH ROW
-- BEGIN
--     UPDATE produto_romaneio SET produto_romaneio.qtd_atual = (produto_romaneio.qtd_atual - NEW.qtd) WHERE produto_romaneio.id = NEW.id_produto_romaneio;
-- END$

-- PAGAMENTO PEDIDO
-- CREATE TRIGGER after_save_pag_ped AFTER INSERT ON pag_ped
-- FOR EACH ROW
-- BEGIN
--     IF(NEW.tipo_pagamento = 'À vista' || NEW.tipo_pagamento = 'Cheque') THEN
--         INSERT INTO caixa (data, valor, operacao, descricao, id_pedido, id_romaneio, id_retirada, id_pag_ped) 
--         VALUES ((SELECT CURDATE()), NEW.valor, 'Receita', (SELECT CONCAT('Recebimento pedido n° ', (SELECT CONVERT((SELECT p.numero FROM pedido p INNER JOIN pag_ped pp ON pp.id_pedido = p.id WHERE pp.id = NEW.id), CHAR)))), (SELECT p.id FROM pedido p INNER JOIN pag_ped pp ON pp.id_pedido = p.id WHERE pp.id = NEW.id), null, null, NEW.id);
--     END IF;
-- END$

-- CREATE TRIGGER after_update_pag_item_ped AFTER UPDATE ON pag_item_ped
-- FOR EACH ROW
-- BEGIN
--     IF((SELECT COUNT(c.id) FROM caixa c WHERE c.id_pag_item_ped = NEW.id) > 0) THEN
--         UPDATE caixa SET caixa.valor = NEW.valor WHERE caixa.id_pag_item_ped = NEW.id;
--     ELSE 
--         IF(NEW.tipo_pagamento = 'À vista' || NEW.tipo_pagamento = 'Cheque') THEN
--             INSERT INTO caixa (data, valor, operacao, descricao, id_pag_item_ped, id_pedido, id_romaneio, id_retirada) 
--             VALUES ((SELECT CURDATE()), NEW.valor, 'Receita', CONCAT('Recebimento pedido n° ', NEW.id_item_pedido), NEW.id, (SELECT ip.id_pedido FROM item_pedido ip WHERE ip.id = NEW.id_item_pedido), NEW.id_romaneio, null);
--         END IF;
--     END IF;
-- END$

-- LIMPA
CREATE TRIGGER after_save_limpa AFTER INSERT ON limpa
FOR EACH ROW
BEGIN
	IF(NEW.status_pedido = 'Fechado') THEN
        UPDATE pedido SET pedido.situacao = 'Aguardando limpa' WHERE pedido.id = (SELECT ip.id_pedido FROM limpa l INNER JOIN item_pedido ip ON ip.id = l.id_item_pedido WHERE l.id = NEW.id);
    END IF;
END$

-- DEVOLUCAO
CREATE TRIGGER after_save_devolucao AFTER INSERT ON devolucao
FOR EACH ROW
BEGIN
	IF(SELECT IF((SELECT SUM(d.qtd_devolucao) FROM devolucao d WHERE d.id_retirada = NEW.id_retirada) = (SELECT l.qtd FROM limpa l INNER JOIN retirada r ON r.id_limpa = l.id WHERE r.id = NEW.id_retirada), 1, 0) > 0) THEN
        UPDATE limpa SET limpa.status = 'Concluído' WHERE limpa.id = (SELECT l.id FROM limpa l INNER JOIN retirada r ON r.id_limpa = l.id WHERE r.id = NEW.id_retirada);
        UPDATE pedido SET pedido.situacao = 'Limpa concluída' WHERE pedido.id = (SELECT ip.id_pedido FROM retirada r INNER JOIN limpa l ON l.id = r.id_limpa INNER JOIN item_pedido ip ON ip.id = l.id_item_pedido WHERE r.id = NEW.id_retirada);
    ELSE 
        UPDATE limpa SET limpa.status = 'Processando' WHERE limpa.id = (SELECT l.id FROM limpa l INNER JOIN retirada r ON r.id_limpa = l.id WHERE r.id = NEW.id_retirada);
    END IF;
END$

-- CREATE TRIGGER after_update_devolucao AFTER UPDATE ON devolucao
-- FOR EACH ROW
-- BEGIN
--     IF(NEW.data_pag IS NOT NULL) THEN
--         INSERT INTO caixa (data, valor, operacao, descricao, id_pedido, id_romaneio, id_retirada, id_pag_ped) 
--         VALUES ((SELECT CURDATE()), NEW.valor, 'Despesa', (SELECT CONCAT('Pagamento limpa n° pedido ', (SELECT CONVERT((SELECT p.numero FROM item_pedido ip INNER JOIN pedido p ON p.id = ip.id_pedido WHERE ip.id = NEW.id), CHAR)))), null, null, (SELECT r.id FROM retirada r INNER JOIN devolucao d ON d.id_retirada = r.id WHERE d.id = NEW.id), null);
--     END IF;
-- END$

DELIMITER ;