package com.romaneios.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "item_mov")
public class ItemMov implements Serializable {

	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private float valor;

	@NotNull
	private String tipo;

	@OneToOne
	@JoinColumn(name = "id_caixa", nullable = true)
	private Caixa caixa;

	@OneToOne
	@JoinColumn(name = "id_cheque", nullable = true)
	private Cheque cheque;

	@OneToOne
	@JoinColumn(name = "id_np", nullable = true)
	private NotaPromissoria np;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "id_mov_caixa")
	private MovCaixa movCaixa;
}
