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
import com.fasterxml.jackson.annotation.JsonView;
import com.romaneios.dto.ViewsJson.ViewLimpasList;
import com.romaneios.dto.ViewsJson.ViewPedidoDetails;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Limpa implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonView(ViewLimpasList.class)
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonView(ViewLimpasList.class)
	@NotNull
	private String status;
	
	@JsonView(ViewLimpasList.class)
	@NotNull
	@Column(name = "status_pedido")
	private String statusPedido;
	
	@JsonView({ViewLimpasList.class, ViewPedidoDetails.class})
	@NotNull
	private int qtd;

	@JsonView(ViewLimpasList.class)
	private Float valor;

	@JsonView(ViewLimpasList.class)
	@NotNull
	@Column(name = "data_inicio")
	private LocalDate dataInicio;

	@JsonView(ViewLimpasList.class)
	@Column(name = "data_conclusao")
	private LocalDate dataConclusao;

	@JsonView(ViewLimpasList.class)
	@OneToOne
	@JoinColumn(name = "id_item_pedido")
	private ItemPedido itemPedido;

	@JsonView(ViewLimpasList.class)
	@JsonIgnore
	@OneToMany(mappedBy = "limpa")
	private List<Retirada> retiradas;

}
