package com.romaneios.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.romaneios.dto.ViewsJson.ViewRomaneioDetails;
import com.romaneios.util.CpfCnpj;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Fornecedor implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonView(ViewRomaneioDetails.class)
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonIgnore
	@Transient
	private String tipo;

	@NotNull
	@CpfCnpj
	@Column(name = "cpf_cnpj")
	private String cpfCnpj;

	@JsonView(ViewRomaneioDetails.class)
	@NotNull
	@Size(min = 3, max = 60)
	@Column(name = "nome_razao")
	private String nomeRazao;

	@Column(unique = true)
	private String telefone;

	@Column(unique = true)
	private String email;

	@NotNull
	@Embedded
	private Endereco endereco;

	@JsonIgnore
	@OneToMany(mappedBy = "fornecedor")
	private List<Romaneio> romaneios;
}
