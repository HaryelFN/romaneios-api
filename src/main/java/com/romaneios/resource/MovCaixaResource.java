package com.romaneios.resource;

import java.time.LocalDate;
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

import com.fasterxml.jackson.annotation.JsonView;
import com.romaneios.dto.DespesaNewDTO;
import com.romaneios.dto.ReceitaNewDTO;
import com.romaneios.dto.ViewsJson.ViewMovCaixaDetails;
import com.romaneios.event.RecursoCriadoEvent;
import com.romaneios.model.MovCaixa;
import com.romaneios.repository.MovCaixaRepository;
import com.romaneios.service.MovCaixaService;

@RestController
@RequestMapping("/mov-caixa")
public class MovCaixaResource {

	@Autowired
	private MovCaixaRepository repository;

	@Autowired
	private MovCaixaService service;

	@Autowired
	private ApplicationEventPublisher publisher;

	@JsonView(ViewMovCaixaDetails.class)
	@GetMapping("/{inicio}/and/{fim}")
	@PreAuthorize("hasAuthority('ROLE_FIND_CAIXA') and #oauth2.hasScope('read')")
	public List<MovCaixa> getAll(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate inicio, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fim) {
		return repository.getBetweenData(inicio, fim);
	}

	@PostMapping("/receita")
	@PreAuthorize("hasAuthority('ROLE_ADD_CAIXA') and #oauth2.hasScope('write')")
	public ResponseEntity<MovCaixa> saveReceita(@Valid @RequestBody ReceitaNewDTO obj, HttpServletResponse response) {
		MovCaixa objSave = service.saveReceita(obj);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, objSave.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(objSave);
	}
	
	@PostMapping("/despesa")
	@PreAuthorize("hasAuthority('ROLE_ADD_CAIXA') and #oauth2.hasScope('write')")
	public ResponseEntity<MovCaixa> saveDespesa(@Valid @RequestBody DespesaNewDTO obj, HttpServletResponse response) {
		MovCaixa objSave = service.saveDespesa(obj);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, objSave.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(objSave);
	}

//	@Autowired
//	private PagamentoService service;
//
//	@JsonView(ViewPagsEmCaixa.class)
//	@GetMapping("/em-caixa")
//	@PreAuthorize("hasAuthority('ROLE_FIND_PEDIDO') and #oauth2.hasScope('read')")
//	public List<PagamentoPedido> emCaixa() {
//		return repository.emCaixa();
//	}
//
//	@GetMapping("/valores-caixa")
//	@PreAuthorize("hasAuthority('ROLE_FIND_PEDIDO') and #oauth2.hasScope('read')")
//	public List<PagamentoPedido> valoresCaixa() {
//		return repository.valorCaixa();
//	}
//
//	@GetMapping("/pedido/{id}")
//	@PreAuthorize("hasAuthority('ROLE_FIND_PEDIDO') and #oauth2.hasScope('read')")
//	public List<PagamentoPedido> getPagamentosByPedidoId(@PathVariable Long id) {
//		return repository.getByPedidoId(id);
//	}
//
//	@GetMapping("/a-vencer")
//	@PreAuthorize("hasAuthority('ROLE_FIND_PEDIDO') and #oauth2.hasScope('read')")
//	public List<PagAVencerDTO> getAllPagamentosAVencer() {
//		return repository.getAllPagItemAVencer();
//	}
//
//	@PutMapping("/{id}")
//	@PreAuthorize("hasAuthority('ROLE_ADD_PEDIDO') and #oauth2.hasScope('write')")
//	public void update(@PathVariable Long id, @Valid @RequestBody List<PagItemPedUpdateDTO> dto) {
//		service.update(id, dto);
//	}
//
//	@GetMapping("/romaneio/{id}")
//	@PreAuthorize("hasAuthority('ROLE_FIND_PEDIDO') and #oauth2.hasScope('read')")
//	public List<PagamentoPedido> getByRomaneioId(@PathVariable Long id) {
//		return repository.getByRomaneioId(id);
//	}
}
