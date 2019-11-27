package com.romaneios.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.romaneios.dto.limpa.LimpaNewDTO;
import com.romaneios.model.Limpa;
import com.romaneios.model.Prestador;
import com.romaneios.model.Retirada;
import com.romaneios.repository.LimpaRepository;
import com.romaneios.repository.RetiradaRepository;

@Service
public class LimpaService {

	Limpa objSave;

	@Autowired
	private LimpaRepository repository;

	@Autowired
	private RetiradaRepository retiradaRepository;

	public Limpa save(LimpaNewDTO dto) {

		this.objSave = isExits(dto.getId());
		Limpa obj = objSave;
		obj.setValor(dto.getValor());
		BeanUtils.copyProperties(obj, objSave, "id");
		objSave = repository.save(objSave);

		dto.getRetiradas().forEach(r -> {
			Retirada retirada = new Retirada();
			retirada.setDataRetirada(r.getDataRetirada());
			retirada.setQtdRetirada(r.getQtdRetirada());
			retirada.setValorKg(r.getValorKg());

			Prestador prestador = new Prestador();
			prestador.setId(r.getId());

			retirada.setPrestador(prestador);
			retirada.setLimpa(objSave);

			retiradaRepository.save(retirada);
		});

		return objSave;
	}

	public Limpa isExits(Long id) {
		Optional<Limpa> objSave = repository.findById(id);
		if (!objSave.isPresent()) {
			throw new IllegalArgumentException();
		}
		return objSave.get();
	}
}
