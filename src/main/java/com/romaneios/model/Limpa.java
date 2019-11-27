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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Limpa implements Serializable {

	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private int qtd;

	private Float valor;

	@NotNull
	@Column(name = "data_inicio")
	private LocalDate dataInicio;

	@Column(name = "data_conclusao")
	private LocalDate dataConclusao;

	@OneToOne
	@JoinColumn(name = "id_item_pedido", nullable = true)
	private ItemPedido itemPedido;

	@JsonIgnore
	@OneToMany(mappedBy = "limpa")
	private List<Retirada> retiradas;

}
