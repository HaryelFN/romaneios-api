package com.romaneios.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.romaneios.event.RecursoCriadoEvent;
import com.romaneios.model.Cheque;
import com.romaneios.repository.ChequeRepository;

@RestController
@RequestMapping("/cheques")
public class ChequeResource {

	@Autowired
	private ChequeRepository repository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_FIND_CAIXA') and #oauth2.hasScope('read')")
	public List<Cheque> getAll() {
		return repository.findAll();
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_FIND_CAIXA') and #oauth2.hasScope('read')")
	public ResponseEntity<Cheque> getById(@PathVariable Long id) {
		Optional<Cheque> obj = repository.findById(id);
		return obj.isPresent() ? ResponseEntity.ok(obj.get()) : ResponseEntity.notFound().build();
	}
	
	@GetMapping("/abertos")
	@PreAuthorize("hasAuthority('ROLE_FIND_CAIXA') and #oauth2.hasScope('read')")
	public List<Cheque> getByAbertos() {
		return repository.getByAbertos();
	}

	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_ADD_CAIXA') and #oauth2.hasScope('write')")
	public ResponseEntity<Cheque> save(@Valid @RequestBody Cheque obj, HttpServletResponse response) {
		Cheque objSave = repository.save(obj);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, objSave.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(objSave);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_REMOVE_CAIXA') and #oauth2.hasScope('write')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		repository.deleteById(id);
	}
}
