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

import com.romaneios.dto.RecebimentoDTO;
import com.romaneios.event.RecursoCriadoEvent;
import com.romaneios.model.NotaPromissoria;
import com.romaneios.repository.NotaPromissoriaRepository;
import com.romaneios.service.NotaPromissoriaService;

@RestController
@RequestMapping("/nps")
public class NotaPromissoriaResource {
	
	private float aux = 0;

	@Autowired
	private NotaPromissoriaRepository repository;

	@Autowired
	private NotaPromissoriaService service;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_FIND_NP') and #oauth2.hasScope('read')")
	public List<NotaPromissoria> getAll() {
		return repository.findAll();
	}

	@GetMapping(params = "pageable")
	@PreAuthorize("hasAuthority('ROLE_FIND_NP') and #oauth2.hasScope('read')")
	public Page<NotaPromissoria> getAll(@RequestParam String emitente, Pageable pageable) {
		emitente = "%" + emitente + "%";
		return repository.getAll(emitente,
				PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()));
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_FIND_NP') and #oauth2.hasScope('read')")
	public ResponseEntity<NotaPromissoria> getById(@PathVariable Long id) {
		Optional<NotaPromissoria> obj = repository.findById(id);
		return obj.isPresent() ? ResponseEntity.ok(obj.get()) : ResponseEntity.notFound().build();
	}
	
	@GetMapping("/romaneio/{id}")
	@PreAuthorize("hasAuthority('ROLE_FIND_NP') and #oauth2.hasScope('read')")
	public List<NotaPromissoria> getByRomaneioId(@PathVariable Long id) {
		return repository.getByRomaneioId(id);
	}

	@GetMapping("/abertos")
	@PreAuthorize("hasAuthority('ROLE_FIND_NP') and #oauth2.hasScope('read')")
	public List<NotaPromissoria> getByAbertos() {
		return repository.getByAbertos();
	}
	
	@GetMapping("/avencer")
	@PreAuthorize("hasAuthority('ROLE_FIND_CHEQUE') and #oauth2.hasScope('read')")
	public Float getByAvencer() {
		this.aux = 0;
		repository.getByAbertos().forEach(c -> {
			aux += c.getValor();
		});
		return aux;
	}

	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_ADD_NP') and #oauth2.hasScope('write')")
	public ResponseEntity<NotaPromissoria> save(@Valid @RequestBody NotaPromissoria obj, HttpServletResponse response) {
		NotaPromissoria objSave = repository.save(obj);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, objSave.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(objSave);
	}

	@PutMapping("/receber/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADD_NP') and #oauth2.hasScope('write')")
	public ResponseEntity<NotaPromissoria> update(@PathVariable Long id,
			@Valid @RequestBody List<RecebimentoDTO> list) {
		NotaPromissoria objSave = service.receber(id, list);
		return ResponseEntity.ok(objSave);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_REMOVE_NP') and #oauth2.hasScope('write')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		repository.deleteById(id);
	}
}
