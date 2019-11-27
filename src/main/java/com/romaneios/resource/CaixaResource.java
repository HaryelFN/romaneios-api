package com.romaneios.resource;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.romaneios.dto.CaixaListChartDTO;
import com.romaneios.dto.cliente.caixa.CaixaListDTO;
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

	@GetMapping("/{inicio}/and/{fim}")
	@PreAuthorize("hasAuthority('ROLE_FIND_LIMPA') and #oauth2.hasScope('read')")
	public List<CaixaListDTO> getAll(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate inicio,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fim) {
		return repository.getBetweenData(inicio, fim);
	}

	@GetMapping("/saldo")
	@PreAuthorize("hasAuthority('ROLE_FIND_CAIXA') and #oauth2.hasScope('read')")
	public Float getSaldo() {
		return repository.getSaldo();
	}

	@GetMapping("/line-chart/{operacao}")
	@PreAuthorize("hasAuthority('ROLE_FIND_CAIXA') and #oauth2.hasScope('read')")
	public List<CaixaListChartDTO> TotalGanhoMes(@PathVariable String operacao) {
		return repository.getTotalGanhoMes(operacao);
	}
}
