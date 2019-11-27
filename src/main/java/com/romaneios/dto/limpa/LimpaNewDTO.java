package com.romaneios.dto.limpa;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class LimpaNewDTO {

	private Long id;

	@JsonIgnore
	private LocalDate dataInicio;

	@JsonIgnore
	private LocalDate dataConclusao;

	private String nomeRazao;

	@JsonIgnore
	private String numPed;

	@JsonIgnore
	private String numRom;

	@JsonIgnore
	private String produto;
	
	@JsonIgnore
	private int qtd;

	private Float valor;

	private List<RetiradaNewDTO> retiradas;

}
