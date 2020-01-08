package com.romaneios.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.romaneios.dto.pedido.PedidoResumoDTO;
import com.romaneios.dto.pedido.PedidoRomaneioDTO;
import com.romaneios.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

	@Query(value = "SELECT p.id, p.numero, c.nome_razao AS nomeRazao, p.data_pedido AS dataPedido, p.valor_total AS valorTotal, p.situacao FROM pedido p INNER JOIN cliente c ON p.id_cliente = c.id ORDER BY p.numero DESC;", nativeQuery = true)
	public List<PedidoResumoDTO> getAll();

	@Query(value = "SELECT p.id, p.numero, c.nome_razao AS nomeRazao, p.data_pedido AS dataPedido, p.valor_total AS valorTotal, p.situacao FROM pedido p INNER JOIN cliente c ON p.id_cliente = c.id WHERE c.nome_razao LIKE :nome ORDER BY p.numero DESC", countQuery = "SELECT COUNT(*) FROM pedido p INNER JOIN cliente c ON c.id = p.id_cliente WHERE c.nome_razao LIKE :nome", nativeQuery = true)
	public Page<PedidoResumoDTO> getAllResumo(@Param("nome") String nome, Pageable pageable);

	@Query(value = "SELECT p.id, p.numero, c.nome_razao AS nomeRazao, p.data_pedido AS dataPedido, p.valor_total AS valorTotal, p.situacao FROM pedido p INNER JOIN cliente c ON p.id_cliente = c.id WHERE p.id = :id", nativeQuery = true)
	public PedidoResumoDTO getByID(@Param("id") Long id);

	@Query(value = "SELECT p.id, p.numero, c.nome_razao AS cliente, CONCAT(pro.nome, ' ', cat.nome) AS produto, p.data_pedido AS data, ip.qtd, ip.valor_uni AS valor, (ip.valor_uni * ip.qtd) AS total, ((ip.valor_uni * ip.qtd) - (pr.valor * ip.qtd)) AS liquido FROM item_pedido ip INNER JOIN pedido p ON p.id = ip.id_pedido INNER JOIN cliente c ON c.id = p.id_cliente INNER JOIN produto_romaneio pr ON pr.id = ip.id_produto_romaneio INNER JOIN produto pro ON pro.id = pr.id_produto INNER JOIN categoria cat ON cat.id = pr.id_categoria WHERE pr.id_romaneio = :id AND p.situacao != 'Pendente'", nativeQuery = true)
	public List<PedidoRomaneioDTO> getPedResRomByRomId(@Param("id") Long id);

	@Query(value = "SELECT p.numero FROM pedido p ORDER BY id DESC LIMIT 1;", nativeQuery = true)
	Long getLastNumberPed();

	@Transactional
	@Modifying
	@Query(value = "UPDATE pedido SET pedido.situacao = :situacao WHERE pedido.id = :id", nativeQuery = true)
	public void updateSituacaoById(@Param("id") Long id, @Param("situacao") String situacao);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE pedido SET pedido.situacao = :situacao WHERE pedido.id = :id", nativeQuery = true)
	public void updateById(@Param("id") Long id, @Param("situacao") String situacao);
	
//	@Transactional
//	@Modifying
//	@Query(value = "DELETE FROM item_pedido WHERE item_pedido.id_pedido = :id", nativeQuery = true)
//	void deleteByPedidoId(@Param("id") Long id);
}
