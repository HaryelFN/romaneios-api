package com.romaneios.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ReceitaNewDTO {

	@NotNull
	private String tipoPagamento;
	private LocalDate dataVencimento;

	@NotNull
	private float valor;
	private String banco;
	private String agencia;
	private String conta;
	private String emitente;
	private String numCheque;
	
	@NotNull
	private String descricao;
}
