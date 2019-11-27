package com.romaneios.dto.pagitempedido;

import java.time.LocalDate;

import com.romaneios.model.ItemPedido;
import com.romaneios.model.Romaneio;

import lombok.Data;

@Data
public class PagItemPedPagarDTO {

	private Long id;

	private String tipoPagamento;
	
	private LocalDate dataVencimento;

	private LocalDate dataPagamento;

	private Float valor;

	private String banco;

	private String agencia;

	private String conta;

	private String numCheque;
	
	private String emitente;

	private ItemPedido itemPedido;

	private Romaneio romaneio;
	
	private Boolean checked;
}
