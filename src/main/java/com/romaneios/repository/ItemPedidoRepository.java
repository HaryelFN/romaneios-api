package com.romaneios.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.romaneios.dto.itemPedido.ItensEstoqueDTO;
import com.romaneios.dto.itemPedido.ItensPedidoDTO;
import com.romaneios.model.ItemPedido;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {

	public List<ItemPedido> findByPedidoId(Long id);

	@Query(value = "SELECT ip.id, CONCAT(p.nome, ' ', c.nome) AS produto, ip.valor_uni AS valor, ip.qtd, ip.valor_total AS valorTotal, r.numero AS numeroRomaneio, r.id AS idRomaneio, pr.id AS idProdutoRomaneio, IF(l.qtd IS NULL, 0, l.qtd) AS qtdLimpa FROM item_pedido ip INNER JOIN produto_romaneio pr ON pr.id = ip.id_produto_romaneio INNER JOIN produto p ON p.id = pr.id_produto INNER JOIN categoria c ON c.id = pr.id_categoria INNER JOIN romaneio r ON r.id = pr.id_romaneio LEFT JOIN limpa l ON l.id_item_pedido = ip.id WHERE ip.id_pedido = :id", nativeQuery = true)
	List<ItensPedidoDTO> getByPedidoId(@Param("id") Long id);

	@Query(value = "SELECT pr.id, CONCAT(p.nome, ' ', c.nome) AS produto, pr.qtd_pendente AS pendente, pr.qtd_atual AS qtd, pr.valor, r.id AS idRomaneio, r.numero AS numeroRomaneio FROM produto_romaneio pr INNER JOIN produto p ON p.id = pr.id_produto INNER JOIN categoria c ON c.id = pr.id_categoria INNER JOIN romaneio r ON r.id = pr.id_romaneio WHERE pr.qtd_atual > 0", nativeQuery = true)
	List<ItensEstoqueDTO> getItensEstoque();

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM item_pedido WHERE item_pedido.id_pedido = :id", nativeQuery = true)
	void deleteByPedidoId(@Param("id") Long id);
}
