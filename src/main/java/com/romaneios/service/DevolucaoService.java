package com.romaneios.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.romaneios.dto.devolucao.DevolucaoDTO;
import com.romaneios.dto.devolucao.DevolucaoNewDTO;
import com.romaneios.dto.retirada.RetiradaAccordionDTO;
import com.romaneios.model.Devolucao;
import com.romaneios.model.Retirada;
import com.romaneios.repository.DevolucaoRepository;
import com.romaneios.repository.projection.DevolucaoListProjecton;

@Service
public class DevolucaoService {

	@Autowired
	private DevolucaoRepository repository;

	@Autowired
	private RetiradaService retiradaService;

	public List<RetiradaAccordionDTO> getRetsAndDevsByprestadorId(Long id, LocalDate inicio, LocalDate fim) {

		List<DevolucaoListProjecton> listPro = repository.getByPrestadorId(id, inicio, fim);
		List<RetiradaAccordionDTO> lisDTO = new ArrayList<>();

		listPro.forEach(pro -> {
			if (lisDTO.isEmpty() || isElement(lisDTO, pro.getIdR()) <= 0) {

				RetiradaAccordionDTO accordion = new RetiradaAccordionDTO();
				accordion.setNumPed(pro.getNumPed());
				accordion.setCliente(pro.getCliente());
				accordion.setProduto(pro.getProduto());
				accordion.setNumRom(pro.getNumRom());
				accordion.setId(pro.getIdR());
				accordion.setData(pro.getDataR());
				accordion.setQtd(pro.getQtdR());
				accordion.setValor(pro.getValorR());

				List<DevolucaoDTO> devolucoes = new ArrayList<>();

				if (pro.getIdD() != null) {
					DevolucaoDTO dDTO = new DevolucaoDTO(pro.getIdD(), pro.getDataD(), pro.getQtdD(),
							pro.getDataPagD());
					devolucoes.add(dDTO);
				}

				accordion.setDevolucoes(devolucoes);

				lisDTO.add(accordion);
			} else {
				lisDTO.forEach(dto -> {
					if (pro.getIdR() == dto.getId()) {
						if (pro.getIdD() != null) {
							DevolucaoDTO dDTO = new DevolucaoDTO(pro.getIdD(), pro.getDataD(), pro.getQtdD(),
									pro.getDataPagD());
							dto.getDevolucoes().add(dDTO);
						}
					}
				});
			}
		});
		return lisDTO;
	}

	public int isElement(List<RetiradaAccordionDTO> list, Long id) {
		int count = 0;
		for (int i = 0; i < list.size(); i++)
			if (list.get(i).getId() == id) {
				count += 1;
			}
		return count;
	}

	public Devolucao save(DevolucaoNewDTO dto) {

		Retirada retiradaSave = retiradaService.isExists(dto.getIdRetirada());

		Devolucao obj = new Devolucao();
		obj.setDataDevolucao(LocalDate.now());
		obj.setQtdDevolucao(dto.getQtd());
		obj.setValor(dto.getValor());
		obj.setDataPag(dto.getDataPag());
		obj.setRetirada(retiradaSave);

		return repository.save(obj);
	}
}
