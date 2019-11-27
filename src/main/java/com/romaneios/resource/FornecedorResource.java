package com.romaneios.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.romaneios.dto.FornecedorResumoDTO;
import com.romaneios.event.RecursoCriadoEvent;
import com.romaneios.model.Fornecedor;
import com.romaneios.repository.FornecedorRepository;
import com.romaneios.service.FornecedorService;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorResource {

	@Autowired
	private FornecedorRepository repository;

	@Autowired
	private FornecedorService service;

	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_FIND_FORNECEDOR') and #oauth2.hasScope('read')")
	public List<Fornecedor> getAll() {
		return repository.findAll();
	}

	@GetMapping(params = "pageable")
	@PreAuthorize("hasAuthority('ROLE_FIND_FORNECEDOR') and #oauth2.hasScope('read')")
	public Page<FornecedorResumoDTO> getAll(@RequestParam String nome, Pageable pageable) {
		nome = "%" + nome + "%";
		return repository.getAll(nome, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()));
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_FIND_FORNECEDOR') and #oauth2.hasScope('read')")
	public ResponseEntity<Fornecedor> getById(@PathVariable Long id) {
		Optional<Fornecedor> obj = repository.findById(id);
		return obj.isPresent() ? ResponseEntity.ok(obj.get()) : ResponseEntity.notFound().build();
	}

	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_ADD_FORNECEDOR') and #oauth2.hasScope('write')")
	public ResponseEntity<Fornecedor> save(@Valid @RequestBody Fornecedor obj, HttpServletResponse response) {
		Fornecedor objSave = repository.save(obj);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, objSave.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(objSave);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADD_FORNECEDOR') and #oauth2.hasScope('write')")
	public ResponseEntity<Fornecedor> update(@PathVariable Long id, @Valid @RequestBody Fornecedor obj) {
		Fornecedor objSave = service.update(id, obj);
		return ResponseEntity.ok(objSave);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_REMOVE_FORNECEDOR') and #oauth2.hasScope('write')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		repository.deleteById(id);
	}

}
