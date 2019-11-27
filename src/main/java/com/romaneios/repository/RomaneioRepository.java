package com.romaneios.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.romaneios.dto.romaneio.RomaneioResumoDTO;
import com.romaneios.model.Romaneio;

public interface RomaneioRepository extends JpaRepository<Romaneio, Long> {

	@Query(value = "SELECT r.id, r.situacao, r.numero, r.data_entrada AS dataEntrada, r.valor_compra AS valorCompra, f.nome_razao AS fornecedor FROM romaneio r INNER JOIN fornecedor f ON r.id_fornecedor = f.id;", nativeQuery = true)
	public List<RomaneioResumoDTO> getAll();

	@Query(value = "SELECT r.numero FROM romaneio r ORDER BY id DESC LIMIT 1;", nativeQuery = true)
	Long getLastNumber();

	@Query(value = "SELECT r.id, r.situacao, r.numero, r.data_entrada AS dataEntrada, r.valor_romaneio AS valorRomaneio, r.valor_transporte AS valorTransporte, f.nome_razao AS fornecedor FROM romaneio r INNER JOIN fornecedor f ON f.id = r.id_fornecedor WHERE f.nome_razao LIKE :nome", countQuery = "SELECT COUNT(*) FROM romaneio r INNER JOIN fornecedor f ON f.id = r.id_fornecedor WHERE f.nome_razao LIKE :nome", nativeQuery = true)
	public Page<RomaneioResumoDTO> getAllResumo(@Param("nome") String nome, Pageable pageable);
	
	@Query(value = "SELECT IF(SUM(pip.valor) IS NULL, 0, SUM(pip.valor)) FROM pag_item_ped pip WHERE pip.id_romaneio = :id AND pip.data_pagamento IS NOT NULL", nativeQuery = true)
	public Float getTotalEmCaixaById(@Param("id") Long id);
	
	@Query(value = "SELECT IF(SUM(pip.valor) IS NULL, 0, SUM(pip.valor)) FROM pag_item_ped pip WHERE pip.id_romaneio = :id AND pip.data_pagamento IS NULL", nativeQuery = true)
	public Float getTotalAreceberById(@Param("id") Long id);

	@Transactional
	@Modifying
	@Query(value = "UPDATE romaneio SET romaneio.situacao = :situacao WHERE romaneio.id = :id", nativeQuery = true)
	public void updateSituacao(@Param("id") Long id, @Param("situacao") String status);
}
