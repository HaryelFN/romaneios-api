package com.romaneios.service;

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

		// BeanUtils.copyProperties(obj, objSave, "id");
		
		objSave = isExits(dto.getId());
		objSave.setStatus("Processando");
		objSave.setValor(dto.getValor());
		objSave = repository.saveAndFlush(objSave);

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
		return repository.findById(id).orElseThrow(() -> new IllegalArgumentException());
	}
}
