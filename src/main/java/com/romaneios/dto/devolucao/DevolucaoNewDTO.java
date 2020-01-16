package com.romaneios.dto.devolucao;

import java.time.LocalDate;
import java.util.List;

import com.romaneios.dto.pagitempedido.PagItemPedPagarDTO;

import lombok.Data;

@Data
public class DevolucaoNewDTO {
	
	private Long id;

	private int qtd;
	
	private Float valor;
	
	private LocalDate dataPag;
	
	private Long idRetirada;
	
	private List<PagItemPedPagarDTO> list;
}
