package com.romaneios.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Entity(name = "item_pedido")
public class ItemPedido implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonView({ViewLimpasList.class, ViewPedidoDetails.class})
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonView({ViewLimpasList.class, ViewPedidoDetails.class})
	@NotNull
	private int qtd;

	@JsonView({ViewLimpasList.class, ViewPedidoDetails.class})
	@NotNull
	@Column(name = "valor_uni")
	private float valorUnitario;

	@JsonView({ViewLimpasList.class, ViewPedidoDetails.class})
	@NotNull
	@Column(name = "valor_total")
	private float valorTotal;

	@JsonIgnore
	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_pedido")
	private Pedido pedido;
	
	@JsonView(ViewPedidoDetails.class)
	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_produto_romaneio")
	private ProdutoRomaneio produtoRomaneio;
	
	@JsonIgnore
	// @JsonView(ViewPedidoDetails.class)
	@OneToOne(mappedBy = "itemPedido", cascade = CascadeType.REMOVE)
	private Limpa limpa;

}
