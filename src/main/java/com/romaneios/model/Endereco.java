package com.romaneios.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Embeddable
public class Endereco {

	@NotEmpty
	private String cep;

	@NotNull
	@NotEmpty
	private String uf;

	@NotNull
	@NotEmpty
	private String cidade;

	@NotNull
	@NotEmpty
	private String bairro;

	@NotNull
	@NotEmpty
	private String logradouro;

	private String numero;
}
