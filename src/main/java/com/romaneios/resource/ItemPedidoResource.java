package com.romaneios.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.romaneios.dto.itemPedido.ItensEstoqueDTO;
import com.romaneios.dto.itemPedido.ItensPedidoDTO;
import com.romaneios.repository.ItemPedidoRepository;

@RestController
@RequestMapping("/itens")
public class ItemPedidoResource {

	@Autowired
	private ItemPedidoRepository repository;

	@GetMapping("/pedido/{id}")
	@PreAuthorize("hasAuthority('ROLE_FIND_PEDIDO') and #oauth2.hasScope('read')")
	public List<ItensPedidoDTO> getByPedidoId(@PathVariable Long id) {
		return repository.getByPedidoId(id);
	}

	@GetMapping("/estoque")
	@PreAuthorize("hasAuthority('ROLE_FIND_PEDIDO') and #oauth2.hasScope('read')")
	public List<ItensEstoqueDTO> getItensEstoque() {
		return repository.getItensEstoque();
	}
}
