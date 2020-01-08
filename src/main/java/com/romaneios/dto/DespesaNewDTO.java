package com.romaneios.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.romaneios.dto.pagitempedido.PagItemPedPagarDTO;

import lombok.Data;

@Data
public class DespesaNewDTO {

	@NotNull
	private float valor;
	
	@NotNull
	private String descricao;
	
	private List<PagItemPedPagarDTO> list;
}
