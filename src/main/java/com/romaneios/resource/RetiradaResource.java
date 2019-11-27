package com.romaneios.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.romaneios.dto.ViewsJson.ViewRetiradaLimpaDTO;
import com.romaneios.model.Retirada;
import com.romaneios.repository.RetiradaRepository;

@RestController
@RequestMapping("/retiradas")
public class RetiradaResource {

	@Autowired
	private RetiradaRepository repository;

	@JsonView(ViewRetiradaLimpaDTO.class)
	@GetMapping("/limpa/{id}")
	@PreAuthorize("hasAuthority('ROLE_FIND_LIMPA') and #oauth2.hasScope('read')")
	public List<Retirada> getById(@PathVariable Long id) {
		return repository.findByLimpaId(id);
	}
}
