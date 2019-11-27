package com.romaneios.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.romaneios.dto.pedido.PedidoNewDTO;
import com.romaneios.model.Caixa;
import com.romaneios.model.Cheque;
import com.romaneios.model.Cliente;
import com.romaneios.model.ItemMov;
import com.romaneios.model.ItemPedido;
import com.romaneios.model.Limpa;
import com.romaneios.model.MovCaixa;
import com.romaneios.model.NotaPromissoria;
import com.romaneios.model.Pedido;
import com.romaneios.model.ProdutoRomaneio;
import com.romaneios.repository.CaixaRepository;
import com.romaneios.repository.ChequeRepository;
import com.romaneios.repository.ItemMovRepository;
import com.romaneios.repository.ItemPedidoRepository;
import com.romaneios.repository.LimpaRepository;
import com.romaneios.repository.MovCaixaRepository;
import com.romaneios.repository.NotaPromissoriaRepository;
import com.romaneios.repository.PedidoRepository;
import com.romaneios.repository.ProdutoRomaneioRepository;
import com.romaneios.repository.RomaneioRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private MovCaixaRepository movCaixaRepository;
	
	@Autowired
	private ItemMovRepository itemMovRepository;

	@Autowired
	private ProdutoRomaneioRepository produtoRomaneioRepository;

	@Autowired
	private RomaneioService romaneioService;

	@Autowired
	private RomaneioRepository romaneioRepository;

	@Autowired
	private LimpaRepository limpaRepository;

	@Autowired
	private CaixaRepository caixaRepository;

	@Autowired
	private ChequeRepository chRepository;

	@Autowired
	private NotaPromissoriaRepository npRepository;

	public Pedido salvar(PedidoNewDTO dto) {
		Pedido pedido = null;

		pedido = new Pedido();
		pedido.setNumero(dto.getNumero());
		pedido.setData(dto.getDataPedido());
		pedido.setValor(dto.getValorTotal());

		Cliente cliente = new Cliente();
		cliente.setId(dto.getCliente().getId());
		pedido.setCliente(cliente);

		Pedido pedidoSave;

		pedidoSave = repository.save(pedido);

		if (pedidoSave != null) {
			for (int i = 0; i < dto.getItens().size(); i++) {

				ItemPedido itemPedido = new ItemPedido();
				itemPedido.setQtd(dto.getItens().get(i).getQtd());
				itemPedido.setValorUnitario(dto.getItens().get(i).getValorUnitario());
				itemPedido.setValorTotal(dto.getItens().get(i).getValorTotal());
				itemPedido.setPedido(pedidoSave);

				ProdutoRomaneio produtoRomaneio = new ProdutoRomaneio();
				produtoRomaneio.setId(dto.getItens().get(i).getProduto().getId());
				itemPedido.setProdutoRomaneio(produtoRomaneio);

				itemPedido = itemPedidoRepository.save(itemPedido);

				if (dto.getItens().get(i).getQtdLimpa() > 0) {
					Limpa limpa = new Limpa();
					limpa.setQtd(dto.getItens().get(i).getQtdLimpa());
					limpa.setDataInicio(LocalDate.now());
					limpa.setItemPedido(itemPedido);
					limpaRepository.save(limpa);
				}
			}
		}

		// INSERT MOVIMENTAÇÃO CAIXA
		MovCaixa mov = new MovCaixa();

		mov.setData(LocalDate.now());
		mov.setValor(pedidoSave.getValor());
		mov.setDescricao("Pagamento pedido n° " + pedido.getNumero());
		mov.setOrigem("Pedido");
		mov.setPedido(pedidoSave);
		mov.setRomaneio(null);
		mov.setRetirada(null);

		MovCaixa movSave = movCaixaRepository.save(mov);

		// INSERT ITEM MOVIMENTAÇÃO/PAGAMENTO
		dto.getPagamentos().forEach(p -> {

			ItemMov item = new ItemMov();
			item.setValor(p.getValor());
			item.setTipo(p.getTipo());

			if (p.getTipo().equals("À vista")) {

				Caixa c = new Caixa();
				c.setData(LocalDate.now());
				c.setValor(p.getValor());

				Caixa caixaSave = caixaRepository.save(c);

				item.setCaixa(caixaSave);
			}

			if (p.getTipo().equals("Cheque")) {

				Cheque ch = new Cheque();
				ch.setDtVen(p.getVencimento());
				ch.setDtPag(null);
				ch.setValor(p.getValor());
				ch.setAgencia(p.getAgencia());
				ch.setBanco(p.getBanco());
				ch.setConta(p.getConta());
				ch.setNumCheque(p.getNumCheque());
				ch.setEmitente(p.getEmitente());

				Cheque chSave = chRepository.save(ch);

				item.setCheque(chSave);
			}

			if (p.getTipo().equals("Nota Promissória")) {

				NotaPromissoria np = new NotaPromissoria();
				np.setDtVen(p.getVencimento());
				np.setDtPag(null);
				np.setValor(p.getValor());
				np.setEmitente(p.getEmitente());

				NotaPromissoria npSave = npRepository.save(np);

				item.setNp(npSave);
			}

			item.setMovCaixa(movSave);
			itemMovRepository.save(item);

		});

//		for (int j = 0; j < dto.getPagamentos().size(); j++) {
//
//			PagamentoPedido pag = new PagamentoPedido();
//			pag.setPedido(pedidoSave);
//			pag.setTipoPagamento(dto.getPagamentos().get(j).getTipo());
//
//			if (dto.getPagamentos().get(j).getTipo().equals("À vista")) {
//				pag.setDataVencimento(LocalDate.now());
//				pag.setDataPagamento(LocalDate.now());
//			} else {
//				pag.setDataVencimento(dto.getPagamentos().get(j).getVencimento());
//				pag.setDataPagamento(null);
//			}
//
//			pag.setValor(dto.getPagamentos().get(j).getValor());
//
//			pag.setBanco(dto.getPagamentos().get(j).getBanco());
//			pag.setAgencia(dto.getPagamentos().get(j).getAgencia());
//			pag.setConta(dto.getPagamentos().get(j).getConta());
//			pag.setNumCheque(dto.getPagamentos().get(j).getNumCheque());
//			pag.setEmitente(dto.getPagamentos().get(j).getEmitente());
//
//			pag = PagPedidoRepository.save(pag);
//
//			if (pag.getTipoPagamento().equals("Cheque") || pag.getTipoPagamento().equals("À vista")) {
//
//				Caixa c2 = new Caixa();
//
//				c2.setData(LocalDate.now());
//				c2.setValor(pag.getValor());
//				c2.setOperacao("Receita");
//
//				if (pag.getTipoPagamento().equals("Cheque")) {
//					c2.setDescricao("Recebimento cheque n° pedido " + pag.getPedido().getNumero());
//				}
//
//				if (pag.getTipoPagamento().equals("À vista")) {
//					c2.setDescricao("Recebimento n° pedido " + pag.getPedido().getNumero());
//				}
//
//				c2.setPedido(pag.getPedido());
//				c2.setRomaneio(null);
//				c2.setRetirada(null);
//				c2.setPagPed(pag);
//
//				caixaRepository.save(c2);
//			}
//		}

		dto.getItens().forEach(i -> {
			if (romaneioService.isRoamneio(i.getIdRomaneio()).getSituacao() != "Pago") {
				if (produtoRomaneioRepository.isEstoque(i.getIdRomaneio()) == 0) {
					romaneioRepository.updateSituacao(i.getIdRomaneio(), "Fechado");
				}
			}
		});

		return pedidoSave;
	}
//
//	public byte[] pedidoVendasDoc(Long id) throws Exception {
//
//		Pedido pedido = repository.findById(id).get();
//
//		Map<String, Object> parameters = new HashMap<>();
//		parameters.put("PEDIDO", pedido);
//		parameters.put("DATA_PEDIDO", pedido.getData() == null ? null : Convert.asDate(pedido.getData()));
//		parameters.put("URL_LOGO", this.getClass().getResource("/docs/jdg-logo.png").toString());
//		parameters.put("SUBLOCALE", this.getClass().getResource("/docs/sub-pagamentos.jasper").toString());
//		parameters.put("REPORT_LOCALE", new Locale("pt", "BR"));
//
//		List<ItensPedJasperDTO> list = new ArrayList<>();
//		List<PagItemPedJasperDTO> pagamentos = new ArrayList<>();
//
//		for (int i = 0; i < pedido.getItens().size(); i++) {
//
//			ItensPedJasperDTO dto = new ItensPedJasperDTO();
//
//			dto.setProduto(pedido.getItens().get(i).getProdutoRomaneio().getProduto());
//			dto.setCategoria(pedido.getItens().get(i).getProdutoRomaneio().getCategoria());
//			dto.setValorUnitario(pedido.getItens().get(i).getValorUnitario());
//			dto.setQtd(pedido.getItens().get(i).getQtd());
//			dto.setValorTotal((pedido.getItens().get(i).getValorUnitario() * pedido.getItens().get(i).getQtd()));
//			dto.setNumeroRomaneio(pedido.getItens().get(i).getProdutoRomaneio().getRomaneio().getNumero());
//
//			list.add(dto);
//		}
//
//		pedido.getPagamentos().forEach(p -> {
//
//			PagItemPedJasperDTO pag = new PagItemPedJasperDTO();
//			pag.setTipoPagamento(p.getTipoPagamento());
//			pag.setDataVencimento(p.getDataVencimento() == null ? null : Convert.asDate(p.getDataVencimento()));
//			pag.setDataPagamento(p.getDataPagamento() == null ? null : Convert.asDate(p.getDataPagamento()));
//			pag.setValor(p.getValor());
//
//			pagamentos.add(pag);
//		});
//
//		list.get(list.size() - 1).setPagamentos(pagamentos);
//
//		parameters.put("TOTAL", list.stream().map(e -> e.getValorTotal()).reduce((float) 0, (x, y) -> x + y));
//
//		InputStream inputStream = this.getClass().getResourceAsStream("/docs/pedido-vendas.jasper");
//		JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parameters,
//				new JRBeanCollectionDataSource(list));
//		return JasperExportManager.exportReportToPdf(jasperPrint);
//	}
}
