package com.romaneios.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonView;
import com.romaneios.dto.ViewsJson.ViewDevolucaoNew;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Devolucao implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonView(ViewDevolucaoNew.class)
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonView(ViewDevolucaoNew.class)
	@NotNull
	@Column(name = "data_devolucao")
	private LocalDate dataDevolucao;

	@JsonView(ViewDevolucaoNew.class)
	@NotNull
	@Column(name = "qtd_devolucao")
	private int qtdDevolucao;

	@JsonView(ViewDevolucaoNew.class)
	@NotNull
	@Column(name = "valor")
	private Float valor;

	@JsonView(ViewDevolucaoNew.class)
	@Column(name = "data_pag")
	private LocalDate dataPag;

	@ManyToOne
	@JoinColumn(name = "id_retirada")
	private Retirada retirada;

}
