package com.romaneios.dto.pagitempedido;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PagItemPedUpdateDTO {

	private Long id;

	private String tipo;

	private LocalDate vencimento;

	private LocalDate DataPagamento;

	private Float Valor;

	private String Banco;

	private String Agencia;

	private String Conta;

	private String Emitente;

	private String NumCheque;

	private String NomeRazao;

	private Integer Numero;
}
