package com.romaneios.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonView;
import com.romaneios.dto.ViewsJson.ViewPagsEmCaixa;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Romaneio implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonView(ViewPagsEmCaixa.class)
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private int numero;

	@NotNull
	private String situacao;

	@NotNull
	@Column(name = "data_entrada")
	private LocalDate dataEntrada;

	@NotNull
	@Column(name = "valor_transporte")
	private Float valorTransporte;

	@Column(name = "valor_romaneio")
	private Float valorRomaneio;

	private String obs;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_fornecedor")
	private Fornecedor fornecedor;

	@OneToMany(mappedBy = "romaneio")
	private List<ProdutoRomaneio> produtos;

}
