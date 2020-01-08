package com.romaneios.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.romaneios.model.ProdutoRomaneio;
import com.romaneios.repository.ProdutoRomaneioRepository;

@RestController
@RequestMapping("/produtos-romaneios")
public class ProdutoRomaneioResource {
	
	@Autowired
	private ProdutoRomaneioRepository repository;

	@GetMapping("/romaneio/{id}")
	@PreAuthorize("hasAuthority('ROLE_FIND_PEDIDO') and #oauth2.hasScope('read')")
	public List<ProdutoRomaneio> getPedResRomByIdRom(@PathVariable Long id) {
		return repository.findByRomaneioId(id);
	}
	
	@GetMapping("/estoque-geral")
	@PreAuthorize("hasAuthority('ROLE_DASHBORD') and #oauth2.hasScope('read')")
	public Integer getEstoqueTotal() {
		return repository.getEstoqueTotal();
	}
}
