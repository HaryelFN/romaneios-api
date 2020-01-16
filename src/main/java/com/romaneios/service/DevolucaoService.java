package com.romaneios.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.romaneios.dto.devolucao.DevolucaoDTO;
import com.romaneios.dto.devolucao.DevolucaoNewDTO;
import com.romaneios.dto.pagitempedido.PagItemPedPagarDTO;
import com.romaneios.dto.retirada.RetiradaAccordionDTO;
import com.romaneios.model.Caixa;
import com.romaneios.model.Cheque;
import com.romaneios.model.Devolucao;
import com.romaneios.model.ItemMov;
import com.romaneios.model.MovCaixa;
import com.romaneios.model.Retirada;
import com.romaneios.repository.CaixaRepository;
import com.romaneios.repository.ChequeRepository;
import com.romaneios.repository.DevolucaoRepository;
import com.romaneios.repository.ItemMovRepository;
import com.romaneios.repository.MovCaixaRepository;
import com.romaneios.repository.projection.DevolucaoListProjecton;

@Service
public class DevolucaoService {

	private MovCaixa mov;

	@Autowired
	private DevolucaoRepository repository;

	@Autowired
	private RetiradaService retiradaService;

	@Autowired
	private MovCaixaRepository mcRepository;

	@Autowired
	private ChequeRepository chRepository;

	@Autowired
	private ChequeService chService;

	@Autowired
	private CaixaRepository cxRepository;

	@Autowired
	private ItemMovRepository imRepository;

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
					DevolucaoDTO dDTO = new DevolucaoDTO(pro.getIdD(), pro.getDataD(), pro.getQtdD(), pro.getValorD(),
							pro.getDataPagD());
					devolucoes.add(dDTO);
				}

				accordion.setDevolucoes(devolucoes);

				lisDTO.add(accordion);
			} else {
				lisDTO.forEach(dto -> {
					if (pro.getIdR() == dto.getId()) {
						if (pro.getIdD() != null) {
							DevolucaoDTO dDTO = new DevolucaoDTO(pro.getIdD(), pro.getDataD(), pro.getQtdD(), pro.getValorD(), pro.getDataPagD());
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
		obj.setRetirada(retiradaSave);

		// INSERT MOVIMENTAÇÃO
		if (dto.getDataPag() != null) {

			obj.setDataPag(dto.getDataPag());

			this.mov = new MovCaixa();
			mov.setData(LocalDate.now());
			mov.setValor(-dto.getValor());
			mov.setDescricao("Pagamento limpa de " + dto.getQtd() + " Kg, para o prestador " + retiradaSave.getPrestador().getNome());
			mov.setOrigem("Retirada");
			mov.setRetirada(retiradaSave);

			mov = mcRepository.save(mov);

			dto.getList().forEach(c -> {
				if (c.getTipoPagamento().equals("Cheque")) {

					Cheque ch = chService.isExists(c.getId());

					// INSERT VALOR NO CAIXA
					Caixa c1 = new Caixa();
					c1.setData(LocalDate.now());
					c1.setValor(c.getValor());
					c1 = cxRepository.save(c1);

					ch.setDtPag(LocalDate.now());
					ch = chRepository.saveAndFlush(ch);
				}
			});

			Caixa c1 = new Caixa();
			c1.setData(LocalDate.now());
			c1.setValor(-dto.getValor());
			c1 = cxRepository.save(c1);

			ItemMov im = new ItemMov();
			im.setTipo("À vista");
			im.setMovCaixa(mov);
			im.setValor(-dto.getValor());
			im.setCaixa(c1);

			imRepository.save(im);
		}

		return repository.save(obj);
	}

	public Devolucao pagarDevPrestador(Long id, List<PagItemPedPagarDTO> list) {

		Devolucao objSave = isExists(id);
		objSave.setDataPag(LocalDate.now());

		// INSERT MOVIMENTAÇÃO
		if (objSave.getDataPag() != null) {

			objSave.setDataPag(objSave.getDataPag());

			this.mov = new MovCaixa();
			mov.setData(LocalDate.now());
			mov.setValor(-objSave.getValor());
			mov.setDescricao("Pagamento limpa de " + objSave.getQtdDevolucao() + " Kg, para o prestador " + objSave.getRetirada().getPrestador().getNome());
			mov.setOrigem("Retirada");
			mov.setRetirada(objSave.getRetirada());

			mov = mcRepository.save(mov);

			list.forEach(c -> {
				if (c.getTipoPagamento().equals("Cheque")) {

					Cheque ch = chService.isExists(c.getId());

					// INSERT VALOR NO CAIXA
					Caixa c1 = new Caixa();
					c1.setData(LocalDate.now());
					c1.setValor(c.getValor());
					c1 = cxRepository.save(c1);

					ch.setDtPag(LocalDate.now());
					ch = chRepository.saveAndFlush(ch);
				}
			});

			Caixa c1 = new Caixa();
			c1.setData(LocalDate.now());
			c1.setValor(-objSave.getValor());
			c1 = cxRepository.save(c1);

			ItemMov im = new ItemMov();
			im.setTipo("À vista");
			im.setMovCaixa(mov);
			im.setValor(-objSave.getValor());
			im.setCaixa(c1);

			imRepository.save(im);
		}

		return repository.saveAndFlush(objSave);
	}

	public Devolucao isExists(Long id) {
		Optional<Devolucao> objSave = repository.findById(id);
		if (!objSave.isPresent()) {
			throw new IllegalArgumentException();
		}
		return objSave.get();
	}
}
