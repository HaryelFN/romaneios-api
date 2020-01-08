package com.romaneios.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonView;
import com.romaneios.dto.ViewsJson.ViewPedidoDetails;
import com.romaneios.dto.ViewsJson.ViewRomaneioDetails;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Categoria implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonView({ViewRomaneioDetails.class, ViewPedidoDetails.class})
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonView({ViewRomaneioDetails.class, ViewPedidoDetails.class})
	@NotNull
	private String nome;
}
