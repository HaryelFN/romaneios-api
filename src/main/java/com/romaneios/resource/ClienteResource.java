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

import com.romaneios.dto.cliente.ClienteResDTO;
import com.romaneios.dto.cliente.ClienteResForListDTO;
import com.romaneios.event.RecursoCriadoEvent;
import com.romaneios.model.Cliente;
import com.romaneios.repository.ClienteRepository;
import com.romaneios.service.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteResource {

	@Autowired
	private ClienteRepository repository;

	@Autowired
	private ClienteService service;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_FIND_CLIENTE') and #oauth2.hasScope('read')")
	public List<ClienteResForListDTO> getAll() {
		return repository.getAllForList();
	}

	@GetMapping(params = "pageable")
	@PreAuthorize("hasAuthority('ROLE_FIND_CLIENTE') and #oauth2.hasScope('read')")
	public Page<ClienteResDTO> getAll(@RequestParam String nome, Pageable pageable) {
		nome = "%" + nome + "%";
		return repository.getAll(nome,
				PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()));
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_FIND_CLIENTE') and #oauth2.hasScope('read')")
	public ResponseEntity<Cliente> getById(@PathVariable Long id) {
		Optional<Cliente> obj = repository.findById(id);
		return obj.isPresent() ? ResponseEntity.ok(obj.get()) : ResponseEntity.notFound().build();
	}

	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_ADD_CLIENTE') and #oauth2.hasScope('write')")
	public ResponseEntity<Cliente> save(@Valid @RequestBody Cliente obj, HttpServletResponse response) {
		Cliente objSave = repository.save(obj);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, objSave.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(objSave);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADD_CLIENTE') and #oauth2.hasScope('write')")
	public ResponseEntity<Cliente> update(@PathVariable Long id, @Valid @RequestBody Cliente obj) {
		Cliente objSave = service.update(id, obj);
		return ResponseEntity.ok(objSave);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_REMOVE_CLIENTE') and #oauth2.hasScope('write')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		repository.deleteById(id);
	}

}
