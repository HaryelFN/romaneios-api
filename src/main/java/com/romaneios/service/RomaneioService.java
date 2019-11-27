package com.romaneios.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.romaneios.dto.pagitempedido.PagItemPedPagarDTO;
import com.romaneios.model.ProdutoRomaneio;
import com.romaneios.model.Romaneio;
import com.romaneios.repository.CaixaRepository;
import com.romaneios.repository.ProdutoRomaneioRepository;
import com.romaneios.repository.RomaneioRepository;
import com.romaneios.service.execption.FornecedorException;

@Service
public class RomaneioService {

	private float cont = 0;

	@Autowired
	private RomaneioRepository repository;

	@Autowired
	private FornecedorService FornecedorService;

	@Autowired
	private ProdutoRomaneioRepository produtoRomaneioRepository;

	@Autowired
	private CaixaRepository caixaRepository;

//	@Autowired
//	private PagamentoPedidoRepository pagPedRepository;

	@Autowired
	private MovCaixaService pagPedService;

	public Romaneio salvar(Romaneio obj) {

		obj.getProdutos().forEach(p -> {
			p.setQtdAtual(p.getQtdEntrada());
		});

		FornecedorService.isFornecedor(obj.getFornecedor().getId());

		Romaneio objSave = repository.save(obj);

		if (objSave != null) {
			for (ProdutoRomaneio p : obj.getProdutos()) {
				p.setRomaneio(objSave);
				produtoRomaneioRepository.save(p);
			}

		}
		return objSave;
	}

	public void deletar(Long id) {
		Romaneio objSalvo = null;

		if (id != null) {
			objSalvo = repository.getOne(id);

			if (!objSalvo.getProdutos().isEmpty()) {
				objSalvo.getProdutos().forEach(p -> {
					produtoRomaneioRepository.deleteById(p.getId());
				});
			}

			repository.deleteById(objSalvo.getId());
		}

		if (objSalvo == null) {
			throw new FornecedorException();
		}
	}

	public Romaneio pagarRomaneio(Long id, List<PagItemPedPagarDTO> list) {

//		Romaneio r = isRoamneio(id);
//
//		list.forEach(c -> {
//
//			if (!c.getTipoPagamento().equals("Cheque")) {
//
//				PagamentoPedido pag = pagPedService.isPagItemPed(c.getId());
//
//				if (c.getValor() < pag.getValor()) {
//
//					pag.setTipoPagamento("À vista");
//					pag.setDataPagamento(LocalDate.now());
//					pag.setValor((pag.getValor() - c.getValor()));
//					pag.setBanco(null);
//					pag.setAgencia(null);
//					pag.setConta(null);
//					pag.setEmitente(null);
//					pag.setNumCheque(null);
//
//					pagPedRepository.saveAndFlush(pag);
//				} else {
//					pagPedRepository.deleteById(c.getId());
//				}
//
//			}
//		});
//
//		// DESCONTRA NO CAIXA
//		Caixa c1 = new Caixa();
//		c1.setData(LocalDate.now());
//
//		list.forEach(l -> {
//			this.cont += l.getValor();
//		});
//
//		c1.setValor(this.cont);
//		c1.setOperacao("Despesa");
//		c1.setDescricao("Pagamento romaneio n° " + r.getNumero());
//		c1.setRomaneio(r);
//
//		caixaRepository.save(c1);
//
//		r.setSituacao("Pago");
//		return repository.saveAndFlush(r);
		
		return null;
	}

	public Romaneio isRoamneio(Long id) {
		Optional<Romaneio> objSave = repository.findById(id);
		if (!objSave.isPresent()) {
			throw new IllegalArgumentException();
		}
		return objSave.get();
	}
}
