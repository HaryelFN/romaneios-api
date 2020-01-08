package com.romaneios.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.romaneios.dto.RecebimentoDTO;
import com.romaneios.model.Caixa;
import com.romaneios.model.Cheque;
import com.romaneios.model.ItemMov;
import com.romaneios.model.MovCaixa;
import com.romaneios.model.NotaPromissoria;
import com.romaneios.repository.CaixaRepository;
import com.romaneios.repository.ChequeRepository;
import com.romaneios.repository.ItemMovRepository;
import com.romaneios.repository.MovCaixaRepository;
import com.romaneios.repository.NotaPromissoriaRepository;

@Service
public class NotaPromissoriaService {

	@Autowired
	private NotaPromissoriaRepository repository;

	@Autowired
	private MovCaixaRepository mvRepository;

	@Autowired
	private ItemMovRepository imRepository;

	@Autowired
	private CaixaRepository cxRepository;

	@Autowired
	private ChequeRepository chRepository;

	@Autowired
	private NotaPromissoriaRepository npRepository;

	public NotaPromissoria receber(Long id, List<RecebimentoDTO> list) {

		NotaPromissoria objSave = isExists(id);

		MovCaixa mov = new MovCaixa();

		mov.setData(LocalDate.now());
		mov.setValor(objSave.getValor());
		mov.setDescricao("Recebimento NP de " + objSave.getEmitente());
		mov.setOrigem("NP");
		mov.setNp(objSave);

		mvRepository.save(mov);

		list.forEach(l -> {

			ItemMov im = new ItemMov();
			im.setValor(l.getValor());
			im.setTipo(l.getTipo());
			im.setMovCaixa(mov);

			if (im.getTipo().equals("À vista")) {

				Caixa c = new Caixa();
				c.setData(LocalDate.now());
				c.setValor(l.getValor());

				c = cxRepository.save(c);

				im.setCaixa(c);
			} else

			if (im.getTipo().equals("Cheque")) {

				Cheque ch = new Cheque();
				ch.setDtVen(l.getVencimento());
				ch.setDtPag(null);
				ch.setValor(l.getValor());
				ch.setAgencia(l.getAgencia());
				ch.setBanco(l.getBanco());
				ch.setConta(l.getConta());
				ch.setNumCheque(l.getNumCheque());
				ch.setEmitente(l.getEmitente());

				ch = chRepository.save(ch);

				im.setCheque(ch);

			} else

			if (im.getTipo().equals("Nota Promissória")) {

				NotaPromissoria np = new NotaPromissoria();
				np.setDtVen(l.getVencimento());
				np.setDtPag(null);
				np.setValor(l.getValor());
				np.setEmitente(l.getEmitente());

				np = npRepository.save(np);

				im.setNp(np);
			}

			imRepository.save(im);
		});

		objSave.setDtPag(LocalDate.now());
		return repository.saveAndFlush(objSave);
	}

	public NotaPromissoria isExists(Long id) {
		Optional<NotaPromissoria> objSave = repository.findById(id);
		if (!objSave.isPresent()) {
			throw new IllegalArgumentException();
		}
		return objSave.get();
	}
}
