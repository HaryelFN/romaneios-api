package com.romaneios.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonView;
import com.romaneios.dto.ViewsJson.ViewMovCaixaDetails;
import com.romaneios.dto.ViewsJson.ViewPedidoDetails;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Pedido implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonView({ ViewPedidoDetails.class, ViewMovCaixaDetails.class })
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonView(ViewPedidoDetails.class)
	@NotNull
	private int numero;

	@JsonView(ViewPedidoDetails.class)
	@NotNull
	@Column(name = "data_pedido")
	private LocalDate data;

	@JsonView(ViewPedidoDetails.class)
	@NotNull
	@Column(name = "valor_total")
	private Float valor;

	@JsonView(ViewPedidoDetails.class)
	@NotNull
	private String situacao;

	@JsonView(ViewPedidoDetails.class)
	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_cliente")
	private Cliente cliente;

	@JsonView(ViewPedidoDetails.class)
	@OneToMany(mappedBy = "pedido",cascade = CascadeType.REMOVE)
	private List<ItemPedido> itens;
}
