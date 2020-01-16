package com.romaneios.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.romaneios.model.Devolucao;
import com.romaneios.repository.projection.DevolucaoListProjecton;

public interface DevolucaoRepository extends JpaRepository<Devolucao, Long> {
	
	@Query(value  = "SELECT d.id_retirada AS retiradaId FROM devolucao d WHERE d.id = :id", nativeQuery = true)
	public Long getRetIdByDevId(@Param("id") Long id);

	@Query(value = "SELECT ped.numero AS numPed, c.nome_razao AS cliente, rom.numero AS numRom, CONCAT(pro.nome, ' ', cat.nome) AS produto, r.id AS idR, r.data_retirada AS dataR, r.qtd_retirada AS qtdR, r.valor_kg AS valorR, d.id AS idD, d.data_devolucao AS dataD, d.qtd_devolucao AS qtdD, d.valor AS valorD, d.data_pag AS dataPagD FROM prestador p INNER JOIN retirada r ON r.id_prestador = p.id INNER JOIN limpa l ON l.id = r.id_limpa INNER JOIN item_pedido ip ON ip.id = l.id_item_pedido INNER JOIN pedido ped ON ped.id = ip.id_pedido INNER JOIN cliente c ON c.id = ped.id_cliente INNER JOIN produto_romaneio pr ON pr.id = ip.id_produto_romaneio INNER JOIN produto pro ON pro.id = pr.id_produto INNER JOIN categoria cat ON cat.id = pr.id_categoria INNER JOIN romaneio rom ON rom.id = pr.id_romaneio LEFT JOIN devolucao d ON d.id_retirada = r.id WHERE p.id = :id AND (r.data_retirada BETWEEN :inicio AND :fim) ORDER BY r.data_retirada", nativeQuery = true)
	public List<DevolucaoListProjecton> getByPrestadorId(@Param("id") Long id, @Param("inicio") LocalDate inico,
			@Param("fim") LocalDate fim);

	@Transactional
	@Modifying
	@Query(value = "UPDATE devolucao SET devolucao.data_pag = :data WHERE devolucao.id = :id", nativeQuery = true)
	public void updateDevolucao(@Param("id") Long id, @Param("data") LocalDateTime data);

	@Query(value = "SELECT SUM(d.qtd_devolucao) FROM devolucao d INNER JOIN retirada r ON r.id = d.id_retirada INNER JOIN limpa l ON l.id = r.id_limpa INNER JOIN prestador p ON p.id = r.id_prestador WHERE l.id = :idLimpa AND p.id = :idPrestador", nativeQuery = true)
	public Integer getQtdDevoByLimpaId(@Param("idLimpa") Long idLimpa, @Param("idPrestador") Long idPrestador);
}
