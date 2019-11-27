package com.romaneios.model.enums;

import java.util.ArrayList;
import java.util.List;

public enum TipoPessoa {

	F("Física"), J("Jurídica");

	private String descricao;

	private TipoPessoa(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public static TipoPessoa toEnum(String descricao) {

		if (descricao.isEmpty()) {
			return null;
		}

		for (TipoPessoa x : TipoPessoa.values()) {
			if (descricao.equals(x.getDescricao())) {
				return x;
			}
		}

		throw new IllegalArgumentException("Descricão inválida!: " + descricao);
	}

	public static List<String> getAll() {

		List<String> list = new ArrayList<>();

		for (TipoPessoa x : TipoPessoa.values()) {
			list.add(x.getDescricao());
		}

		return list;
	}
}
