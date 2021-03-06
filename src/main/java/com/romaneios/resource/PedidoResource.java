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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.fasterxml.jackson.annotation.JsonView;
import com.romaneios.dto.ViewsJson.ViewPedidoDetails;
import com.romaneios.dto.pedido.PedidoNewDTO;
import com.romaneios.dto.pedido.PedidoResumoDTO;
import com.romaneios.dto.pedido.PedidoRomaneioDTO;
import com.romaneios.event.RecursoCriadoEvent;
import com.romaneios.model.Pedido;
import com.romaneios.repository.PedidoRepository;
import com.romaneios.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoResource {

	@Autowired
	private PedidoRepository repository;

	@Autowired
	private PedidoService service;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_FIND_PEDIDO') and #oauth2.hasScope('read')")
	public List<PedidoResumoDTO> getAll() {
		return repository.getAll();
	}

	@GetMapping(params = "pageable")
	@PreAuthorize("hasAuthority('ROLE_FIND_PEDIDO') and #oauth2.hasScope('read')")
	public Page<PedidoResumoDTO> getAll(@RequestParam String nome, Pageable pageable) {
		nome = "%" + nome + "%";
		return repository.getAllResumo(nome,
				PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort()));
	}

	@JsonView(ViewPedidoDetails.class)
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_FIND_PEDIDO') and #oauth2.hasScope('read')")
	public ResponseEntity<Pedido> getById(@PathVariable Long id) {
		Optional<Pedido> obj = repository.findById(id);
		return obj.isPresent() ? ResponseEntity.ok(obj.get()) : ResponseEntity.notFound().build();
	}

	@GetMapping("/resumo/{id}")
	@PreAuthorize("hasAuthority('ROLE_FIND_PEDIDO') and #oauth2.hasScope('read')")
	public ResponseEntity<PedidoResumoDTO> pedResumoGetById(@PathVariable Long id) {
		PedidoResumoDTO obj = repository.getByID(id);
		return obj != null ? ResponseEntity.ok(obj) : ResponseEntity.notFound().build();
	}

	@GetMapping("/romaneio/{id}")
	@PreAuthorize("hasAuthority('ROLE_FIND_PEDIDO') and #oauth2.hasScope('read')")
	public List<PedidoRomaneioDTO> getbyIdRomaneio(@PathVariable Long id) {
		return repository.getPedResRomByRomId(id);
	}

	@GetMapping("/last")
	@PreAuthorize("hasAuthority('ROLE_FIND_PEDIDO') and #oauth2.hasScope('read')")
	public Long getLastNumber() {
		return repository.getLastNumberPed();
	}

	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_ADD_PEDIDO') and #oauth2.hasScope('write')")
	public ResponseEntity<Pedido> save(@Valid @RequestBody PedidoNewDTO obj, HttpServletResponse response) {
		Pedido objSave = service.salvar(obj);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, objSave.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(objSave);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADD_PEDIDO') and #oauth2.hasScope('write')")
	public ResponseEntity<Pedido> update(@PathVariable Long id, @Valid @RequestBody PedidoNewDTO obj) {
		Pedido objSave = service.update(id, obj);
		return ResponseEntity.ok(objSave);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_REMOVE_PEDIDO') and #oauth2.hasScope('write')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		service.remove(id);
	}

	@GetMapping("/{id}/situacao/{situacao}")
	@PreAuthorize("hasAuthority('ROLE_ADD_PEDIDO') and #oauth2.hasScope('write')")
	public void update(@PathVariable Long id, @PathVariable String situacao) {
		repository.updateSituacaoById(id, situacao);
	}

	@GetMapping("/doc")
	@PreAuthorize("hasAuthority('ROLE_FIND_PEDIDO') and #oauth2.hasScope('read')")
	public ResponseEntity<byte[]> getDocPedidoVendasById(@RequestParam Long id) throws Exception {
		byte[] relatorio = service.pedidoVendasDoc(id);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE).body(relatorio);
	}
}
