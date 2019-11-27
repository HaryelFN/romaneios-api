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

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Devolucao implements Serializable {

	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "data_devolucao")
	private LocalDate dataDevolucao;

	@NotNull
	@Column(name = "qtd_devolucao")
	private int qtdDevolucao;

	@NotNull
	@Column(name = "valor")
	private Float valor;

	@Column(name = "data_pag")
	private LocalDate dataPag;

	@ManyToOne
	@JoinColumn(name = "id_retirada")
	private Retirada retirada;

}
