package com.romaneios.dto.retirada;

import java.time.LocalDate;
import java.util.List;

import com.romaneios.dto.devolucao.DevolucaoDTO;

import lombok.Data;

@Data
public class RetiradaAccordionDTO {

	private String numPed;
	private String cliente;
	private String produto;
	private String numRom;
	private Long id;
	private LocalDate data;
	private Integer qtd;
	private Float valor;
	public List<DevolucaoDTO> devolucoes;
}
