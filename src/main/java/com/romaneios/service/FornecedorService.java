package com.romaneios.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.romaneios.model.Fornecedor;
import com.romaneios.repository.FornecedorRepository;

@Service
public class FornecedorService {

	@Autowired
	private FornecedorRepository repository;

	public Fornecedor update(Long id, Fornecedor obj) {
		Fornecedor objSave = isFornecedor(id);
		BeanUtils.copyProperties(obj, objSave, "id");
		return repository.save(objSave);
	}

	public Fornecedor isFornecedor(Long id) {
		Optional<Fornecedor> objSave = repository.findById(id);
		if (!objSave.isPresent()) {
			throw new IllegalArgumentException();
		}
		return objSave.get();
	}
}
