package com.romaneios.resource;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.romaneios.dto.ViewsJson.ViewLimpasList;
import com.romaneios.dto.limpa.LimpaDTO;
import com.romaneios.dto.limpa.LimpaNewDTO;
import com.romaneios.dto.limpa.limpaListDTO;
import com.romaneios.event.RecursoCriadoEvent;
import com.romaneios.model.Limpa;
import com.romaneios.repository.LimpaRepository;
import com.romaneios.service.LimpaService;

@RestController
@RequestMapping("/limpas")
public class LimpaResource {

	@Autowired
	private LimpaRepository repository;

	@Autowired
	private LimpaService service;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping(params = "pageable")
	@PreAuthorize("hasAuthority('ROLE_FIND_LIMPA') and #oauth2.hasScope('read')")
	public Page<limpaListDTO> getAll(@RequestParam String nome, Pageable pageable) {
		nome = "%" + nome + "%";
		return repository.getPageableByClienteNome(nome,
				PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()));
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_FIND_LIMPA') and #oauth2.hasScope('read')")
	public ResponseEntity<LimpaDTO> getById(@PathVariable Long id) {
		Optional<LimpaDTO> obj = repository.getById(id);
		return obj.isPresent() ? ResponseEntity.ok(obj.get()) : ResponseEntity.notFound().build();
	}

	@JsonView(ViewLimpasList.class)
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_ADD_LIMPA') and #oauth2.hasScope('write')")
	public ResponseEntity<Limpa> save(@Valid @RequestBody LimpaNewDTO dto, HttpServletResponse response) {
		Limpa objSave = service.save(dto);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, objSave.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(objSave);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_REMOVE_LIMPA') and #oauth2.hasScope('write')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		repository.deleteById(id);
	}

}
