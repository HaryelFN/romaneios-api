package com.romaneios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.romaneios.model.MovCaixa;
import com.romaneios.repository.MovCaixaRepository;

@Service
public class MovCaixaService {

	@Autowired
	private MovCaixaRepository repository;

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
