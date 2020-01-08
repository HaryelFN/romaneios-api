package com.romaneios.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.romaneios.dto.ViewsJson.ViewPedidoDetails;
import com.romaneios.dto.ViewsJson.ViewRomaneioDetails;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "produto_romaneio")
public class ProdutoRomaneio implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonView({ ViewRomaneioDetails.class, ViewPedidoDetails.class })
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonView({ ViewRomaneioDetails.class, ViewPedidoDetails.class })
	@NotNull
	@OneToOne
	@JoinColumn(name = "id_produto")
	private Produto produto;

	@JsonView({ ViewRomaneioDetails.class, ViewPedidoDetails.class })
	@NotNull
	@OneToOne
	@JoinColumn(name = "id_categoria")
	private Categoria categoria;

	@JsonView({ ViewRomaneioDetails.class, ViewPedidoDetails.class })
	@NotNull
	private float valor;

	@JsonView({ ViewRomaneioDetails.class, ViewPedidoDetails.class })
	@NotNull
	@Column(name = "qtd_entrada")
	private int qtdEntrada;

	@JsonView({ ViewRomaneioDetails.class, ViewPedidoDetails.class })
	@NotNull
	@Column(name = "qtd_atual")
	private int qtdAtual;

	@JsonView({ ViewRomaneioDetails.class, ViewPedidoDetails.class })
	@NotNull
	@Column(name = "qtd_pendente")
	private int qtdPendente;

	@JsonBackReference
	@JsonView({ ViewPedidoDetails.class })
	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_romaneio")
	private Romaneio romaneio;

}
