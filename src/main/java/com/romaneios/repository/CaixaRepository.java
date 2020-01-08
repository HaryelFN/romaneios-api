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

	@Query(value = "SELECT c.`data` AS `data`, SUM(c.valor) AS valor FROM caixa c WHERE c.valor > 0 AND YEAR(c.`data`) = YEAR(CURDATE()) GROUP BY YEAR(c.`data`), MONTH(c.`data`)", nativeQuery = true)
	public List<CaixaListChartDTO> getTotalReceitaMes();
	
	@Query(value = "SELECT c.`data` AS `data`, SUM(c.valor) AS valor FROM caixa c WHERE c.valor < 0 AND YEAR(c.`data`) = YEAR(CURDATE()) GROUP BY YEAR(c.`data`), MONTH(c.`data`)", nativeQuery = true)
	public List<CaixaListChartDTO> getTotalDespesaMes();

	@Query(value = "SELECT c.id, c.data, c.valor, c.operacao, c.descricao, c.id_pedido AS idPed, c.id_romaneio AS idRom, c.id_retirada AS idRet FROM caixa c WHERE c.data BETWEEN :inicio AND :fim ORDER BY c.data", nativeQuery = true)
	public List<CaixaListDTO> getBetweenData(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);

	@Query(value = "SELECT IF(c.valor IS null, 0, SUM(c.valor)) AS saldo FROM caixa c", nativeQuery = true)
	public Float getSaldo();

	@Transactional
	@Modifying
	@Query(value = "UPDATE caixa SET caixa.valor = :valor, caixa.descricao = :descricao WHERE caixa.id_pag_item_ped = :idPagItemPed", nativeQuery = true)
	public void updateByPagItemPedId(@Param("valor") float valor, @Param("descricao") String descricao, @Param("idPagItemPed") Long idPagItemPed);
	
	@Query(value = "SELECT c.* FROM caixa c INNER JOIN item_mov im ON im.id_caixa = c.id INNER JOIN mov_caixa mc ON mc.id = im.id_mov_caixa INNER JOIN pedido p ON p.id = mc.id_pedido INNER JOIN item_pedido ip ON ip.id_pedido = p.id INNER JOIN produto_romaneio pr ON pr.id = ip.id_produto_romaneio WHERE pr.id_romaneio = :id", nativeQuery = true)
	public List<Caixa> getByRomaneioId(@Param("id") Long id);
}
