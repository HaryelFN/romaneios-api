package com.romaneios.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.romaneios.dto.pagitempedido.PagItemPedPagarDTO;
import com.romaneios.model.Caixa;
import com.romaneios.model.Cheque;
import com.romaneios.model.ItemMov;
import com.romaneios.model.MovCaixa;
import com.romaneios.model.ProdutoRomaneio;
import com.romaneios.model.Romaneio;
import com.romaneios.repository.CaixaRepository;
import com.romaneios.repository.ChequeRepository;
import com.romaneios.repository.ItemMovRepository;
import com.romaneios.repository.MovCaixaRepository;
import com.romaneios.repository.ProdutoRomaneioRepository;
import com.romaneios.repository.RomaneioRepository;
import com.romaneios.service.execption.FornecedorException;

@Service
public class RomaneioService {

	private MovCaixa mov;

	@Autowired
	private RomaneioRepository repository;

	@Autowired
	private FornecedorService FornecedorService;

	@Autowired
	private ProdutoRomaneioRepository produtoRomaneioRepository;

	@Autowired
	private CaixaRepository caixaRepository;

	@Autowired
	private MovCaixaRepository mcRepository;

	@Autowired
	private ItemMovRepository imRepository;

	@Autowired
	private ChequeService chService;

	@Autowired
	private ChequeRepository chRepository;

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

		Romaneio r = isRoamneio(id);

		this.mov = new MovCaixa();
		mov.setData(LocalDate.now());
		mov.setValor(-r.getValorRomaneio());
		mov.setDescricao("Pagamento romaneio n° " + r.getNumero());
		mov.setOrigem("Romaneio");
		mov.setRomaneio(r);

		mov = mcRepository.save(mov);

		list.forEach(c -> {
			if (c.getTipoPagamento().equals("Cheque")) {

				Cheque ch = chService.isExists(c.getId());

				// INSERT VALOR NO CAIXA
				Caixa c1 = new Caixa();
				c1.setData(LocalDate.now());
				c1.setValor(c.getValor());
				c1 = caixaRepository.save(c1);

				ch.setDtPag(LocalDate.now());
				ch = chRepository.saveAndFlush(ch);
			}
		});

		Caixa c1 = new Caixa();
		c1.setData(LocalDate.now());
		c1.setValor(-r.getValorRomaneio());
		c1 = caixaRepository.save(c1);

		ItemMov im = new ItemMov();
		im.setTipo("À vista");
		im.setMovCaixa(mov);
		im.setValor(-r.getValorRomaneio());
		im.setCaixa(c1);

		imRepository.save(im);

		r.setSituacao("Pago");
		return repository.saveAndFlush(r);
	}

	public Romaneio isRoamneio(Long id) {
		Optional<Romaneio> objSave = repository.findById(id);
		if (!objSave.isPresent()) {
			throw new IllegalArgumentException();
		}
		return objSave.get();
	}
}
