package com.romaneios.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.romaneios.model.MovCaixa;

public interface MovCaixaRepository extends JpaRepository<MovCaixa, Long> {

//	@Query(value = "SELECT pp.* FROM pag_ped pp INNER JOIN pedido p ON p.id = pp.id_pedido INNER JOIN item_pedido ip ON ip.id_pedido = p.id INNER JOIN produto_romaneio pr ON pr.id = ip.id_produto_romaneio WHERE pr.id_romaneio = :id GROUP BY pp.id", nativeQuery = true)
//	public List<PagamentoPedido> getByRomaneioId(@Param("id") Long id);
//
//	@Query(value = "SELECT pp.* FROM pag_ped pp WHERE pp.tipo_pagamento != 'Nota Promissória'", nativeQuery = true)
//	public List<PagamentoPedido> emCaixa();
//
//	@Query(value = "SELECT * FROM pag_ped pp WHERE pp.id_pedido = :id", nativeQuery = true)
//	public List<PagamentoPedido> getByPedidoId(@Param("id") Long id);
//
//	@Query(value = "SELECT pip.id, pip.tipo_pagamento AS tipoPagamento, pip.data_vencimento AS dataVencimento, pip.valor, pip.banco, pip.agencia, pip.conta, pip.emitente, pip.num_cheque AS numCheque, c.nome_razao AS nomeRazao, r.numero AS numero FROM pag_ped pip INNER JOIN pedido p ON p.id = pip.id_pedido INNER JOIN cliente c ON c.id = p.id_cliente INNER JOIN item_pedido ip ON ip.id_pedido = p.id INNER JOIN produto_romaneio pr ON pr.id = ip.id_produto_romaneio INNER JOIN romaneio r ON r.id = pr.id_romaneio WHERE ISNULL(pip.data_pagamento) GROUP BY pip.id ORDER BY pip.data_vencimento", nativeQuery = true)
//	public List<PagAVencerDTO> getAllPagItemAVencer();
//
//	@Query(value = "SELECT * FROM pag_ped pp WHERE pp.valor >= :valor GROUP BY pp.id", nativeQuery = true)
//	public Optional<PagamentoPedido> getByValor(@Param("valor") float valor);
//	
//	@Query(value = "SELECT pp.* FROM caixa c INNER JOIN pag_ped pp ON pp.id = c.id_pag_ped", nativeQuery = true)
//	public List<PagamentoPedido> valorCaixa();
	
	@Query(value = "SELECT * FROM mov_caixa mc WHERE mc.`data` BETWEEN :inicio AND :fim ORDER BY mc.`data`, mc.id", nativeQuery = true)
	public List<MovCaixa> getBetweenData(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);
}
