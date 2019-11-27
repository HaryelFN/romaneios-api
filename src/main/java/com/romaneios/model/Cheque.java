package com.romaneios.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Cheque implements Serializable {

	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "dt_ven")
	private LocalDate dtVen;

	@Column(name = "dt_pag")
	private LocalDate dtPag;

	@NotNull
	private float valor;

	@NotNull
	private String agencia;

	@NotNull
	private String banco;

	@NotNull
	private String conta;

	@NotNull
	@Column(name = "num_cheque")
	private String numCheque;

	@NotNull
	private String emitente;
}
