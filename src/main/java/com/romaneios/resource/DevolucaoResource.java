package com.romaneios.resource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.romaneios.dto.devolucao.DevolucaoNewDTO;
import com.romaneios.dto.retirada.RetiradaAccordionDTO;
import com.romaneios.event.RecursoCriadoEvent;
import com.romaneios.model.Devolucao;
import com.romaneios.repository.DevolucaoRepository;
import com.romaneios.service.DevolucaoService;

@RestController
@RequestMapping("/devolucoes")
public class DevolucaoResource {

	@Autowired
	private DevolucaoRepository repository;
	
	@Autowired
	private DevolucaoService service;

	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_FIND_LIMPA') and #oauth2.hasScope('read')")
	public Long getById(@PathVariable Long id) {
		return repository.getRetIdByDevId(id);
	}

	@GetMapping("/prestador/{id}/inicio/{inicio}/fim/{fim}")
	@PreAuthorize("hasAuthority('ROLE_FIND_LIMPA') and #oauth2.hasScope('read')")
	public List<RetiradaAccordionDTO> getAll(@PathVariable Long id,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate inicio,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fim) {
		return service.getRetsAndDevsByprestadorId(id, inicio, fim);
	}
	
	@GetMapping("/limpa/{idLimpa}/prestdor/{idPrestador}")
	@PreAuthorize("hasAuthority('ROLE_ADD_LIMPA') and #oauth2.hasScope('write')")
	public Integer getQtdDevByLimpaId(@PathVariable Long idLimpa, @PathVariable Long idPrestador) {
		return repository.getQtdDevoByLimpaId(idLimpa, idPrestador);
	}

	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_ADD_LIMPA') and #oauth2.hasScope('write')")
	public ResponseEntity<Devolucao> save(@Valid @RequestBody DevolucaoNewDTO dto, HttpServletResponse response) {
		Devolucao objSave = service.save(dto);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, objSave.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(objSave);
	}

	@GetMapping("/pagar/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADD_LIMPA') and #oauth2.hasScope('write')")
	public void pagarPrestadorByDevolucaoId(@PathVariable Long id) {
		repository.updateDevolucao(id, LocalDateTime.now());
	}
}
