package com.romaneios.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.romaneios.model.ProdutoRomaneio;

public interface ProdutoRomaneioRepository extends JpaRepository<ProdutoRomaneio, Long> {
	
	@Query(value = "SELECT pr.* FROM produto_romaneio pr INNER JOIN item_pedido ip ON ip.id_produto_romaneio = pr.id WHERE ip.id = :id", nativeQuery = true)
	ProdutoRomaneio getByItemPedId(@Param("id") Long id);

	public List<ProdutoRomaneio> findByRomaneioId(Long id);

	@Query(value = "SELECT COUNT(pr.qtd_atual) FROM produto_romaneio pr WHERE pr.id_romaneio = :id AND pr.qtd_atual > 0", nativeQuery = true)
	public Integer isEstoque(@Param("id") Long id);

	@Query(value = "SELECT SUM(pr.qtd_atual) FROM produto_romaneio pr", nativeQuery = true)
	public Integer getEstoqueTotal();

	@Transactional
	@Modifying
	@Query(value = "UPDATE produto_romaneio SET produto_romaneio.qtd_atual = (produto_romaneio.qtd_atual - :qtd) WHERE produto_romaneio.id = :id", nativeQuery = true)
	public void updateQtdAtual(@Param("qtd") int qtd, @Param("id") Long id);

	@Transactional
	@Modifying
	@Query(value = "UPDATE produto_romaneio SET produto_romaneio.qtd_pendente = (produto_romaneio.qtd_pendente + :qtd) WHERE produto_romaneio.id = :id", nativeQuery = true)
	public void updateAddQtPendente(@Param("qtd") int qtd, @Param("id") Long id);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE produto_romaneio SET produto_romaneio.qtd_pendente = (produto_romaneio.qtd_pendente - :qtd) WHERE produto_romaneio.id = :id", nativeQuery = true)
	public void updateRetQtPendente(@Param("qtd") int qtd, @Param("id") Long id);
}
