package com.romaneios.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.romaneios.dto.limpa.LimpaDTO;
import com.romaneios.dto.limpa.limpaListDTO;
import com.romaneios.model.Limpa;

public interface LimpaRepository extends JpaRepository<Limpa, Long> {

	Limpa findByItemPedidoId(Long id);

	@Query(value = "SELECT l.* FROM limpa l INNER JOIN item_pedido ip ON ip.id = l.id_item_pedido WHERE ip.id_pedido = :id", nativeQuery = true)
	List<Limpa> getByPedidoId(@Param("id") Long id);

	@Query(value = "SELECT l.id, p.numero AS numPed, c.nome_razao AS nomeRazao, ro.numero AS numRom, CONCAT(pro.nome, ' ', cat.nome) AS produto, l.qtd, l.valor, l.`status`, l.status_pedido AS statusPedido FROM limpa l INNER JOIN item_pedido ip ON ip.id = l.id_item_pedido INNER JOIN pedido p ON p.id = ip.id_pedido INNER JOIN cliente c ON c.id = p.id_cliente INNER JOIN produto_romaneio pr ON pr.id = ip.id_produto_romaneio INNER JOIN produto pro ON pro.id = pr.id_produto INNER JOIN categoria cat ON cat.id = pr.id_categoria INNER JOIN romaneio ro ON ro.id = pr.id_romaneio LEFT JOIN retirada r ON r.id_limpa = l.id WHERE l.status_pedido = 'Fechado' AND LOWER(c.nome_razao) LIKE :nome GROUP BY l.id", countQuery = "SELECT COUNT(*) FROM limpa l INNER JOIN item_pedido ip ON ip.id = l.id_item_pedido INNER JOIN pedido p ON p.id = ip.id_pedido INNER JOIN cliente c ON c.id = p.id_cliente INNER JOIN produto_romaneio pr ON pr.id = ip.id_produto_romaneio INNER JOIN produto pro ON pro.id = pr.id_produto INNER JOIN categoria cat ON cat.id = pr.id_categoria INNER JOIN romaneio ro ON ro.id = pr.id_romaneio LEFT JOIN retirada r ON r.id_limpa = l.id WHERE l.status_pedido = 'Fechado' AND LOWER(c.nome_razao) LIKE :nome GROUP BY l.id", nativeQuery = true)
	Page<limpaListDTO> getPageableByClienteNome(@Param("nome") String nome, Pageable pageable);

	@Query(value = "SELECT l.id, p.numero AS numPed, c.nome_razao AS nomeRazao, r.numero AS numRom, CONCAT(pro.nome, ' ', cat.nome) AS produto, l.qtd, l.valor, l.data_inicio AS dataInicio, l.data_conclusao AS dataConclusao FROM limpa l INNER JOIN item_pedido ip ON ip.id = l.id_item_pedido INNER JOIN pedido p ON p.id = ip.id_pedido INNER JOIN cliente c ON c.id = p.id_cliente INNER JOIN produto_romaneio pr ON pr.id = ip.id_produto_romaneio INNER JOIN produto pro ON pro.id = pr.id_produto INNER JOIN categoria cat ON cat.id = pr.id_categoria INNER JOIN romaneio r ON r.id = pr.id_romaneio WHERE l.id = :id", countQuery = "SELECT COUNT(*) FROM limpa l INNER JOIN item_pedido ip ON ip.id = l.id_item_pedido INNER JOIN pedido p ON p.id = ip.id_pedido INNER JOIN cliente c ON c.id = p.id_cliente INNER JOIN produto_romaneio pr ON pr.id = ip.id_produto_romaneio INNER JOIN produto pro ON pro.id = pr.id_produto INNER JOIN categoria cat ON cat.id = pr.id_categoria INNER JOIN romaneio r ON r.id = pr.id_romaneio WHERE l.id = :id", nativeQuery = true)
	Optional<LimpaDTO> getById(@Param("id") Long id);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM limpa WHERE limpa.id_item_pedido = :id", nativeQuery = true)
	void deleteByItemPedidoId(@Param("id") Long id);
}
