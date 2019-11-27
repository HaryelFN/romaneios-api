package com.romaneios.repository;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.romaneios.dto.CaixaListChartDTO;
import com.romaneios.dto.cliente.caixa.CaixaListDTO;
import com.romaneios.model.Caixa;

public interface CaixaRepository extends JpaRepository<Caixa, Long> {

	@Query(value = "SELECT c.`data`, SUM(c.valor) AS valor FROM caixa c WHERE c.`data` BETWEEN DATE_ADD(CURRENT_DATE(), INTERVAL -12 MONTH) AND CURRENT_DATE() AND c.operacao = :operacao GROUP BY YEAR(c.`data`), MONTH(c.`data`)", nativeQuery = true)
	public List<CaixaListChartDTO> getTotalGanhoMes(@Param("operacao") String operacao);

	@Query(value = "SELECT c.id, c.data, c.valor, c.operacao, c.descricao, c.id_pedido AS idPed, c.id_romaneio AS idRom, c.id_retirada AS idRet FROM caixa c WHERE c.data BETWEEN :inicio AND :fim ORDER BY c.data", nativeQuery = true)
	public List<CaixaListDTO> getBetweenData(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);

	@Query(value = "SELECT(SELECT IF(SUM(cr.valor) IS NULL, 0 , SUM(cr.valor)) AS saldo FROM caixa AS cr WHERE cr.operacao = 'Receita') - (SELECT IF(SUM(cd.valor) IS NULL, 0 , SUM(cd.valor)) AS saldo FROM caixa AS cd WHERE cd.operacao = 'Despesa') AS saldo", nativeQuery = true)
	public Float getSaldo();

	@Transactional
	@Modifying
	@Query(value = "UPDATE caixa SET caixa.valor = :valor, caixa.descricao = :descricao WHERE caixa.id_pag_item_ped = :idPagItemPed", nativeQuery = true)
	public void updateByPagItemPedId(@Param("valor") float valor, @Param("descricao") String descricao,
			@Param("idPagItemPed") Long idPagItemPed);
}
