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

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.romaneios.dto.ViewsJson.ViewMovCaixaDetails;
import com.romaneios.dto.ViewsJson.ViewPagsEmCaixa;
import com.romaneios.dto.ViewsJson.ViewPedidoDetails;
import com.romaneios.dto.ViewsJson.ViewRomaneioDetails;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Romaneio implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonView({ ViewPagsEmCaixa.class, ViewRomaneioDetails.class, ViewPedidoDetails.class, ViewMovCaixaDetails.class })
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonView({ ViewRomaneioDetails.class, ViewPedidoDetails.class })
	@NotNull
	private int numero;

	@JsonView(ViewRomaneioDetails.class)
	@NotNull	
	private String situacao;

	@JsonView(ViewRomaneioDetails.class)
	@NotNull
	@Column(name = "data_entrada")
	private LocalDate dataEntrada;

	@JsonView(ViewRomaneioDetails.class)
	@NotNull
	@Column(name = "valor_transporte")
	private Float valorTransporte;

	@JsonView(ViewRomaneioDetails.class)
	@Column(name = "valor_romaneio")
	private Float valorRomaneio;

	@JsonView(ViewRomaneioDetails.class)
	private String obs;

	@JsonView(ViewRomaneioDetails.class)
	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_fornecedor")
	private Fornecedor fornecedor;

	@JsonManagedReference
	@JsonView({ ViewRomaneioDetails.class })
	@OneToMany(mappedBy = "romaneio")
	private List<ProdutoRomaneio> produtos;

}
