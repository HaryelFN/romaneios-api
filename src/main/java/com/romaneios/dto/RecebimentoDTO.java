package com.romaneios.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RecebimentoDTO {

	@NotNull
	private String tipo;

	private LocalDate vencimento;

	@NotNull
	private float valor;

	private String agencia;

	private String banco;

	private String conta;

	private String numCheque;

	@NotNull
	private String emitente;
}
