package com.romaneios.dto.itemPedido;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PagItemPedNewDTO {

	private String tipo;
	private LocalDate vencimento;
	private Float valor;
	private String banco;
	private String agencia;
	private String conta;
	private String numCheque;
	private String emitente;

}
