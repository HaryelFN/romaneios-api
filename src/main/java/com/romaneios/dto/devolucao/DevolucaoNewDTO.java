package com.romaneios.dto.devolucao;

import java.time.LocalDate;
import java.util.List;

import com.romaneios.dto.pagitempedido.PagItemPedPagarDTO;

import lombok.Data;

@Data
public class DevolucaoNewDTO {

	public int qtd;
	
	public Float valor;
	
	public LocalDate dataPag;
	
	public Long idRetirada;
	
	private List<PagItemPedPagarDTO> list;
}
