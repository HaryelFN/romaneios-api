package com.romaneios.resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mov-caixa")
public class MovCaixaResource {

//	@Autowired
//	private PagamentoPedidoRepository repository;
//
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
