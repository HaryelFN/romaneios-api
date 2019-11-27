package com.romaneios.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.romaneios.model.Cliente;
import com.romaneios.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;

	public Cliente update(Long id, Cliente obj) {
		Cliente objSave = isExists(id);
		BeanUtils.copyProperties(obj, objSave, "id");
		return repository.save(objSave);
	}

	public Cliente isExists(Long id) {
		Optional<Cliente> objSave = repository.findById(id);
		if (!objSave.isPresent()) {
			throw new IllegalArgumentException();
		}
		return objSave.get();
	}

}
