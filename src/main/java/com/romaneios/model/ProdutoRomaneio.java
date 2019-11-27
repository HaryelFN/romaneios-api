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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "produto_romaneio")
public class ProdutoRomaneio implements Serializable {

	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@OneToOne
	@JoinColumn(name = "id_produto")
	private Produto produto;

	@NotNull
	@OneToOne
	@JoinColumn(name = "id_categoria")
	private Categoria categoria;

	@NotNull
	private float valor;

	@NotNull
	@Column(name = "qtd_entrada")
	private int qtdEntrada;

	@NotNull
	@Column(name = "qtd_atual")
	private int qtdAtual;

	@JsonIgnore
	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_romaneio")
	private Romaneio romaneio;

}
