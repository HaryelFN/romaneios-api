package com.romaneios.dto.pagitempedido;

import java.util.Date;

import lombok.Data;

@Data
public class PagItemPedJasperDTO {

	public Long id;

	public String TipoPagamento;

	public Date DataVencimento;

	public Date DataPagamento;

	public Float Valor;

}