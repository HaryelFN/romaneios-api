package com.romaneios.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.romaneios.model.ProdutoRomaneio;
import com.romaneios.repository.ProdutoRomaneioRepository;

@Service
public class ProdutoRomaneioService {

	@Autowired
	private ProdutoRomaneioRepository repository;

	public ProdutoRomaneio isExists(Long id) {
		Optional<ProdutoRomaneio> objSave = repository.findById(id);
		if (!objSave.isPresent()) {
			throw new IllegalArgumentException();
		}
		return objSave.get();
	}
}
