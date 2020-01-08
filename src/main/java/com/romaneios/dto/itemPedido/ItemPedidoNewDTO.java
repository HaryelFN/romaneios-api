package com.romaneios.dto.itemPedido;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.romaneios.dto.pedido.ProdutoNewDTO;

import lombok.Data;

@Data
public class ItemPedidoNewDTO {

	private Long id;
	@JsonIgnore
	private Integer index;
	private Long idRomaneio;
	private Long idProdutoRomaneio;
	private int qtd;
	private int qtdLimpa;
	private float valorUnitario;
	private float valorTotal;
	private ProdutoNewDTO produto;

	@JsonIgnore
	private int numeroRomaneio;
}
