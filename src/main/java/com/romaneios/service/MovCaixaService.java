package com.romaneios.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.romaneios.dto.DespesaNewDTO;
import com.romaneios.dto.ReceitaNewDTO;
import com.romaneios.model.Caixa;
import com.romaneios.model.Cheque;
import com.romaneios.model.ItemMov;
import com.romaneios.model.MovCaixa;
import com.romaneios.repository.CaixaRepository;
import com.romaneios.repository.ChequeRepository;
import com.romaneios.repository.ItemMovRepository;
import com.romaneios.repository.MovCaixaRepository;

@Service
public class MovCaixaService {

	private MovCaixa mov;

	@Autowired
	private MovCaixaRepository repository;

	@Autowired
	private CaixaRepository cxRepository;

	@Autowired
	private ChequeService chService;

	@Autowired
	private ChequeRepository chRepository;

	@Autowired
	private MovCaixaRepository mcRepository;

	@Autowired
	private ItemMovRepository imRepository;

	public MovCaixa saveReceita(ReceitaNewDTO obj) {

		MovCaixa mov = new MovCaixa();
		mov.setData(LocalDate.now());
		mov.setValor(obj.getValor());
		mov.setDescricao(obj.getDescricao());
		mov.setOrigem("Receita");

		mov = repository.save(mov);

		ItemMov im = new ItemMov();
		im.setValor(obj.getValor());
		im.setTipo(obj.getTipoPagamento());
		im.setMovCaixa(mov);

		if (im.getTipo().equals("À vista")) {

			Caixa c = new Caixa();
			c.setData(LocalDate.now());
			c.setValor(obj.getValor());

			c = cxRepository.save(c);

			im.setCaixa(c);
		} else

		if (im.getTipo().equals("Cheque")) {

			Cheque ch = new Cheque();
			ch.setDtVen(obj.getDataVencimento());
			ch.setDtPag(null);
			ch.setValor(obj.getValor());
			ch.setAgencia(obj.getAgencia());
			ch.setBanco(obj.getBanco());
			ch.setConta(obj.getConta());
			ch.setNumCheque(obj.getNumCheque());
			ch.setEmitente(obj.getEmitente());

			ch = chRepository.save(ch);

			im.setCheque(ch);

		}

		imRepository.save(im);

		return mov;
	}

	public MovCaixa saveDespesa(DespesaNewDTO dto) {

		this.mov = new MovCaixa();
		mov.setData(LocalDate.now());
		mov.setValor(-dto.getValor());
		mov.setDescricao(dto.getDescricao());
		mov.setOrigem("Despesa");

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

		return mov;
	}

//
//	@Autowired
//	private CaixaRepository caixaRepository;
//
//	public void update(Long id, List<PagItemPedUpdateDTO> dto) {
//
//		PagamentoPedido objSave = isPagItemPed(id);
//
//		repository.deleteById(id);
//
//		dto.forEach(p -> {
//			PagamentoPedido pag = new PagamentoPedido();
//			pag.setTipoPagamento(p.getTipo());
//			pag.setDataVencimento(p.getVencimento());
//
//			if (p.getTipo().equals("À vista")) {
//				pag.setDataPagamento(LocalDate.now());
//			} else {
//				pag.setDataPagamento(null);
//			}
//
//			pag.setValor(p.getValor());
//
//			pag.setBanco(p.getBanco());
//			pag.setAgencia(p.getAgencia());
//			pag.setConta(p.getConta());
//			pag.setNumCheque(p.getNumCheque());
//			pag.setEmitente(p.getEmitente());
//			pag.setPedido(objSave.getPedido());
//
//			pag = repository.save(pag);
//
//			// INSERT RECEITA
//
//			if (p.getTipo().equals("À vista") || p.getTipo().equals("Cheque")) {
//
//				Caixa c2 = new Caixa();
//
//				c2.setData(LocalDate.now());
//				c2.setValor(p.getValor());
//				c2.setOperacao("Receita");
//
//				if (objSave.getTipoPagamento().equals("À vista")) {
//					c2.setDescricao("Recebimento n° pedido " + objSave.getPedido().getNumero());
//				}
//
//				if (objSave.getTipoPagamento().equals("Cheque")) {
//					c2.setDescricao("Recebimento cheque n° pedido " + objSave.getPedido().getNumero());
//				}
//
//				if (objSave.getTipoPagamento().equals("Nota Promissória")) {
//					c2.setDescricao("Recebimento NP n° pedido " + objSave.getPedido().getNumero());
//				}
//
//				c2.setPedido(objSave.getPedido());
//				c2.setRomaneio(null);
//				c2.setRetirada(null);
//				c2.setPagPed(pag);
//
//				caixaRepository.save(c2);
//			}
//		});
//	}
//
//	public PagamentoPedido isPagItemPed(Long id) {
//		Optional<PagamentoPedido> objSave = repository.findById(id);
//		if (!objSave.isPresent()) {
//			throw new IllegalArgumentException();
//		}
//		return objSave.get();
//	}
}
