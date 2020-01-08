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
import com.romaneios.dto.ViewsJson.ViewMovCaixaDetails;
import com.romaneios.dto.ViewsJson.ViewRetiradaLimpaDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Retirada implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonView({ ViewRetiradaLimpaDTO.class, ViewMovCaixaDetails.class })
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonView(ViewRetiradaLimpaDTO.class)
	@NotNull
	@Column(name = "data_retirada")
	private LocalDate dataRetirada;

	@JsonView(ViewRetiradaLimpaDTO.class)
	@NotNull
	@Column(name = "qtd_retirada")
	private int qtdRetirada;

	@JsonView(ViewRetiradaLimpaDTO.class)
	@NotNull
	@Column(name = "valor_kg")
	private Float valorKg;

	@JsonView(ViewRetiradaLimpaDTO.class)
	@ManyToOne
	@JoinColumn(name = "id_prestador")
	private Prestador prestador;

	@ManyToOne
	@JoinColumn(name = "id_limpa")
	private Limpa limpa;

}
