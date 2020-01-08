package com.romaneios.dto.pedido;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.romaneios.dto.itemPedido.ItemPedidoNewDTO;
import com.romaneios.dto.itemPedido.PagItemPedNewDTO;
import com.romaneios.model.Cliente;

import lombok.Data;

@Data
public class PedidoNewDTO {

	private Long id;

	@NotNull
	private int numero;

	@NotNull
	private LocalDate dataPedido;

	@NotNull
	private Float valorTotal;
	
	@NotNull
	private String situacao;

	private Cliente cliente;

	private ArrayList<ItemPedidoNewDTO> itens;

	private List<PagItemPedNewDTO> pagamentos;
}
