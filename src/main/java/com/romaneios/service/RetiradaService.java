package com.romaneios.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.romaneios.model.Retirada;
import com.romaneios.repository.RetiradaRepository;

@Service
public class RetiradaService {

	@Autowired
	private RetiradaRepository repository;
	
	public Retirada isExists(Long id) {
		Optional<Retirada> objSave = repository.findById(id);
		if (!objSave.isPresent()) {
			throw new IllegalArgumentException();
		}
		return objSave.get();
	}
}
