package com.romaneios.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.romaneios.model.Prestador;
import com.romaneios.repository.PrestadorRepository;

@Service
public class PrestadorService {

	@Autowired
	private PrestadorRepository repository;

	public Prestador update(Long id, Prestador obj) {
		Prestador objSave = isExists(id);
		BeanUtils.copyProperties(obj, objSave, "id");
		return repository.save(objSave);
	}

	public Prestador isExists(Long id) {
		Optional<Prestador> objSave = repository.findById(id);
		if (!objSave.isPresent()) {
			throw new IllegalArgumentException();
		}
		return objSave.get();
	}
}
