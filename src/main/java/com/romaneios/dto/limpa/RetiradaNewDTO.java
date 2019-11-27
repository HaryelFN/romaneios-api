package com.romaneios.dto.limpa;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class RetiradaNewDTO {

	private Long id;
	
	@JsonIgnore
	private String prestadorNome;

	private LocalDate dataRetirada;

	private int qtdRetirada;

	private Float valorKg;
	
	@JsonIgnore
	private int qtdDev;
}
