package com.romaneios.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "mov_caixa")
public class MovCaixa implements Serializable {

	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private LocalDate data;

	@NotNull
	private float valor;

	@NotNull
	private String descricao;

	@NotNull
	private String origem;

	@OneToOne
	@JoinColumn(name = "id_pedido", nullable = true)
	private Pedido pedido;

	@OneToOne
	@JoinColumn(name = "id_romaneio", nullable = true)
	private Romaneio romaneio;

	@OneToOne
	@JoinColumn(name = "id_retirada", nullable = true)
	private Retirada retirada;
}
