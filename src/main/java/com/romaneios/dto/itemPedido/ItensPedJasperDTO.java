package com.romaneios.dto.itemPedido;

import java.util.List;

import com.romaneios.dto.pagitempedido.PagItemPedJasperDTO;
import com.romaneios.model.Categoria;
import com.romaneios.model.Produto;

import lombok.Data;

@Data
public class ItensPedJasperDTO {

	public Produto produto;
	
	public Categoria categoria;

	public Float valorUnitario;

	public Integer qtd;

	public Float valorTotal;

	public Integer numeroRomaneio;

	public List<PagItemPedJasperDTO> pagamentos;
}