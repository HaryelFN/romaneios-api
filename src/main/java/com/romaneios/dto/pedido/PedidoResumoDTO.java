package com.romaneios.dto.pedido;

import java.time.LocalDate;

public interface PedidoResumoDTO {

	public Long getId();

	public int getNumero();

	public String getNomeRazao();

	public LocalDate getDataPedido();

	public Float getValorTotal();

}
