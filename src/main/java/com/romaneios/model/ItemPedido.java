package com.romaneios.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "item_pedido")
public class ItemPedido implements Serializable {

	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private int qtd;

	@NotNull
	@Column(name = "valor_uni")
	private float valorUnitario;

	@NotNull
	@Column(name = "valor_total")
	private float valorTotal;

	@JsonIgnore
	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_pedido")
	private Pedido pedido;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_produto_romaneio")
	private ProdutoRomaneio ProdutoRomaneio;

//	@OneToMany(mappedBy = "itemPedido")
//	private List<PagamentoPedido> pagamentos;

}
