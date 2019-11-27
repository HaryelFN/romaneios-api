package com.romaneios.dto.devolucao;

import java.time.LocalDate;

import lombok.Data;

@Data
public class DevolucaoNewDTO {

	public int qtd;
	
	public Float valor;
	
	public LocalDate dataPag;
	
	public Long idRetirada;
}
