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

import com.fasterxml.jackson.annotation.JsonView;
import com.romaneios.dto.ViewsJson.ViewMovCaixaDetails;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "mov_caixa")
public class MovCaixa implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonView(ViewMovCaixaDetails.class)
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonView(ViewMovCaixaDetails.class)
	@NotNull
	private LocalDate data;

	@JsonView(ViewMovCaixaDetails.class)
	@NotNull
	private float valor;

	@JsonView(ViewMovCaixaDetails.class)
	@NotNull
	private String descricao;

	@JsonView(ViewMovCaixaDetails.class)
	@NotNull
	private String origem;

	@JsonView(ViewMovCaixaDetails.class)
	@OneToOne
	@JoinColumn(name = "id_pedido", nullable = true)
	private Pedido pedido;

	@JsonView(ViewMovCaixaDetails.class)
	@OneToOne
	@JoinColumn(name = "id_romaneio", nullable = true)
	private Romaneio romaneio;

	@JsonView(ViewMovCaixaDetails.class)
	@OneToOne
	@JoinColumn(name = "id_retirada", nullable = true)
	private Retirada retirada;

	@JsonView(ViewMovCaixaDetails.class)
	@OneToOne
	@JoinColumn(name = "id_cheque", nullable = true)
	private Cheque cheque;
	
	@JsonView(ViewMovCaixaDetails.class)
	@OneToOne
	@JoinColumn(name = "id_np", nullable = true)
	private NotaPromissoria np;
}
