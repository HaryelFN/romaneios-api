package com.romaneios.dto.romaneio;

import java.time.LocalDate;
import java.util.List;

import com.romaneios.model.Fornecedor;
import com.romaneios.model.ProdutoRomaneio;

import lombok.Data;

@Data
public class RomaneioNewDTO {

	private Long id;
	private int numero;
	private String situacao;
	private LocalDate dataEntrada;
	private Float valorCompra;
	private String descricao;
	private Fornecedor fornecedor;
	private List<ProdutoRomaneio> produtos;

}
