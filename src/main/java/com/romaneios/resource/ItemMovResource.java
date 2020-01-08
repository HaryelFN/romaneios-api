package com.romaneios.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.romaneios.model.ItemMov;
import com.romaneios.repository.ItemMovRepository;

@RestController
@RequestMapping("/itens-mov")
public class ItemMovResource {

	@Autowired
	private ItemMovRepository repository;

	@GetMapping("/mov-caixa/{id}")
	@PreAuthorize("hasAuthority('ROLE_FIND_CAIXA') and #oauth2.hasScope('read')")
	public List<ItemMov> getByMovCaixaId(@PathVariable Long id) {
		return repository.findByMovCaixaId(id);
	}

}
