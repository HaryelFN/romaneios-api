package com.romaneios.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.romaneios.dto.CaixaListChartDTO;
import com.romaneios.model.Caixa;
import com.romaneios.repository.CaixaRepository;

@RestController
@RequestMapping("/caixas")
public class CaixaResource {

	@Autowired
	private CaixaRepository repository;

	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_FIND_CAIXA') and #oauth2.hasScope('read')")
	public List<Caixa> getAll() {
		return repository.findAll();
	}

	@GetMapping("/romaneio/{id}")
	@PreAuthorize("hasAuthority('ROLE_FIND_NP') and #oauth2.hasScope('read')")
	public List<Caixa> getByRomaneioId(@PathVariable Long id) {
		return repository.getByRomaneioId(id);
	}

	@GetMapping("/saldo")
	@PreAuthorize("hasAuthority('ROLE_FIND_CAIXA') and #oauth2.hasScope('read')")
	public Float getSaldo() {
		return repository.getSaldo();
	}

	@GetMapping("/line-chart/receita")
	@PreAuthorize("hasAuthority('ROLE_FIND_CAIXA') and #oauth2.hasScope('read')")
	public List<CaixaListChartDTO> TotalReceitaMes() {
		return repository.getTotalReceitaMes();
	}

	@GetMapping("/line-chart/despesa")
	@PreAuthorize("hasAuthority('ROLE_FIND_CAIXA') and #oauth2.hasScope('read')")
	public List<CaixaListChartDTO> TotalDespesaMes() {
		return repository.getTotalDespesaMes();
	}
}
