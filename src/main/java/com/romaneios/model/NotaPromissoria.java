package com.romaneios.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonView;
import com.romaneios.dto.ViewsJson.ViewMovCaixaDetails;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "np")
public class NotaPromissoria {

	@JsonView(ViewMovCaixaDetails.class)
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
	private String emitente;
}
