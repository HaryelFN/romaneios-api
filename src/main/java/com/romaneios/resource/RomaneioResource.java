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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.romaneios.dto.ViewsJson.ViewRomaneioDetails;
import com.romaneios.dto.pagitempedido.PagItemPedPagarDTO;
import com.romaneios.dto.romaneio.RomaneioResumoDTO;
import com.romaneios.event.RecursoCriadoEvent;
import com.romaneios.model.Romaneio;
import com.romaneios.repository.RomaneioRepository;
import com.romaneios.service.RomaneioService;

@RestController
@RequestMapping("/romaneios")
public class RomaneioResource {

	@Autowired
	private RomaneioRepository repository;

	@Autowired
	private RomaneioService service;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_FIND_ROMANEIO') and #oauth2.hasScope('read')")
	public List<RomaneioResumoDTO> getAll() {
		return repository.getAll();
	}

	@GetMapping(params = "pageable")
	@PreAuthorize("hasAuthority('ROLE_FIND_ROMANEIO') and #oauth2.hasScope('read')")
	public Page<RomaneioResumoDTO> getAll(@RequestParam String nome, Pageable pageable) {
		nome = "%" + nome + "%";
		return repository.getAllResumo(nome,
				PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()));
	}

	@JsonView(ViewRomaneioDetails.class)
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_FIND_ROMANEIO') and #oauth2.hasScope('read')")
	public ResponseEntity<Romaneio> getById(@PathVariable Long id) {
		Optional<Romaneio> obj = repository.findById(id);
		return obj.isPresent() ? ResponseEntity.ok(obj.get()) : ResponseEntity.notFound().build();
	}

	@GetMapping("/last")
	@PreAuthorize("hasAuthority('ROLE_FIND_ROMANEIO') and #oauth2.hasScope('read')")
	public Long getLastNumber() {
		return repository.getLastNumber();
	}

	@GetMapping("/em-caixa/{id}")
	@PreAuthorize("hasAuthority('ROLE_FIND_ROMANEIO') and #oauth2.hasScope('read')")
	public Float getTotalEmCaixa(@PathVariable Long id) {
		return repository.getTotalEmCaixaById(id);
	}

	@GetMapping("/a-receber/{id}")
	@PreAuthorize("hasAuthority('ROLE_FIND_ROMANEIO') and #oauth2.hasScope('read')")
	public Float getTotalAReceber(@PathVariable Long id) {
		return repository.getTotalAreceberById(id);
	}

	@PostMapping("/pagar/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADD_ROMANEIO') and #oauth2.hasScope('read')")
	public Romaneio pagar(@PathVariable Long id, @RequestBody List<PagItemPedPagarDTO> list) {
		return service.pagarRomaneio(id, list);
	}

	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_ADD_ROMANEIO') and #oauth2.hasScope('write')")
	public ResponseEntity<Romaneio> save(@Valid @RequestBody Romaneio obj, HttpServletResponse response) {
		Romaneio objSave = service.salvar(obj);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, objSave.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(objSave);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_REMOVE_ROMANEIO') and #oauth2.hasScope('write')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		service.deletar(id);
	}
}
