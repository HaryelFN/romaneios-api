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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Pedido implements Serializable {

	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private int numero;

	@NotNull
	@Column(name = "data_pedido")
	private LocalDate data;

	@NotNull
	@Column(name = "valor_total")
	private Float valor;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_cliente")
	private Cliente cliente;

	@OneToMany(mappedBy = "pedido")
	private List<ItemPedido> itens;

//	@OneToMany(mappedBy = "pedido")
//	private List<PagamentoPedido> pagamentos;
}
