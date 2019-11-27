package com.romaneios.dto.pedido;

import java.time.LocalDate;

public interface PedidoRomaneioDTO {

	public Long getId();

	public int getNumero();

	public String getCliente();

	public String getProduto();

	public LocalDate getData();

	public Integer getQtd();

	public Float getValor();

	public Float getTotal();

	public Float getLiquido();
}
