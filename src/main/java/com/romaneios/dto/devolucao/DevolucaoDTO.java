package com.romaneios.dto.devolucao;

import java.time.LocalDate;

import lombok.Data;

@Data
public class DevolucaoDTO {

	private Long id;
	private LocalDate data;
	private Integer qtd;
	private LocalDate dataPagamento;

	public DevolucaoDTO(Long id, LocalDate data, Integer qtd, LocalDate dataPagamento) {
		super();
		this.id = id;
		this.data = data;
		this.qtd = qtd;
		this.dataPagamento = dataPagamento;
	}
}
