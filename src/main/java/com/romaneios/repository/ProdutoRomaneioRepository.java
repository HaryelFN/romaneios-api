package com.romaneios.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.romaneios.model.ProdutoRomaneio;

public interface ProdutoRomaneioRepository extends JpaRepository<ProdutoRomaneio, Long> {

	public List<ProdutoRomaneio> findByRomaneioId(Long id);

	@Query(value = "SELECT COUNT(pr.qtd_atual) FROM produto_romaneio pr WHERE pr.id_romaneio = :id AND pr.qtd_atual > 0", nativeQuery = true)
	public Integer isEstoque(@Param("id") Long id);
}
