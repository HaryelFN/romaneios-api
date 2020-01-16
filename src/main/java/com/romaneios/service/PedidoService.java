package com.romaneios.service;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.romaneios.dto.itemPedido.ItensPedJasperDTO;
import com.romaneios.dto.pagitempedido.PagItemPedJasperDTO;
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
import com.romaneios.util.Convert;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class PedidoService {
	
	float aux = 0;

	private Pedido pedidoSave;

	@Autowired
	private PedidoRepository repository;

	@Autowired
	private ClienteService cliService;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private MovCaixaRepository movCaixaRepository;

	@Autowired
	private ItemMovRepository itemMovRepository;

	@Autowired
	private ProdutoRomaneioRepository prRepository;

	@Autowired
	private ProdutoRomaneioService prService;

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
	private ItemMovRepository imRepository;

	@Autowired
	private NotaPromissoriaRepository npRepository;

	public Pedido salvar(PedidoNewDTO dto) {

		pedidoSave = new Pedido();
		pedidoSave.setNumero(dto.getNumero());
		pedidoSave.setData(dto.getDataPedido());
		pedidoSave.setValor(dto.getValorTotal());
		pedidoSave.setSituacao(dto.getSituacao());

		Cliente cliente = cliService.isExists(dto.getCliente().getId());
		pedidoSave.setCliente(cliente);

		pedidoSave = repository.save(pedidoSave);

		if (dto.getSituacao().equals("Pendente")) {

			dto.getItens().forEach(iN -> {

				ItemPedido ip = new ItemPedido();
				ip.setQtd(iN.getQtd());
				ip.setValorUnitario(iN.getValorUnitario());
				ip.setValorTotal(iN.getValorTotal());
				ProdutoRomaneio pr = prService.isExists(iN.getIdProdutoRomaneio());
				ip.setProdutoRomaneio(pr);
				ip.setPedido(pedidoSave);
				ip = itemPedidoRepository.save(ip);

				// UPDATE QTD PEDENTE
				prRepository.updateAddQtPendente(iN.getQtd(), iN.getIdProdutoRomaneio());

				if (iN.getQtdLimpa() > 0) {
					Limpa limpa = new Limpa();
					limpa.setStatus("Aberto");
					limpa.setStatusPedido("Pendente");
					limpa.setQtd(iN.getQtdLimpa());
					limpa.setDataInicio(LocalDate.now());
					limpa.setItemPedido(ip);
					limpaRepository.save(limpa);
				}
			});

		} else if (dto.getSituacao().equals("Fechado")) {

			dto.getItens().forEach(iN -> {

				ItemPedido ip = new ItemPedido();
				ip.setQtd(iN.getQtd());
				ip.setValorUnitario(iN.getValorUnitario());
				ip.setValorTotal(iN.getValorTotal());
				ProdutoRomaneio pr = prService.isExists(iN.getIdProdutoRomaneio());
				ip.setProdutoRomaneio(pr);
				ip.setPedido(pedidoSave);
				ip = itemPedidoRepository.save(ip);

				// UPDATE QTD ESTOQUE
				prRepository.updateQtdAtual(iN.getQtd(), iN.getIdProdutoRomaneio());

				ip = itemPedidoRepository.save(ip);

				if (iN.getQtdLimpa() > 0) {
					Limpa limpa = new Limpa();
					limpa.setStatus("Aberto");
					limpa.setStatusPedido("Fechado");
					limpa.setQtd(iN.getQtdLimpa());
					limpa.setDataInicio(LocalDate.now());
					limpa.setItemPedido(ip);
					limpaRepository.save(limpa);
				}
			});

			// INSERT MOVIMENTAÇÃO CAIXA
//			this.aux = 0;
//			dto.getPagamentos().forEach(p -> {
//				if (p.getTipo().equals("À vista")) {
//					aux += p.getValor();
//				}
//			});
			
			MovCaixa mov = new MovCaixa();
			mov.setData(LocalDate.now());
			mov.setValor(dto.getValorTotal());
			mov.setDescricao("Venda pedido n° " + pedidoSave.getNumero());
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
					np.setEmitente(cliente.getNomeRazao());

					NotaPromissoria npSave = npRepository.save(np);

					item.setNp(npSave);
				}

				item.setMovCaixa(movSave);
				itemMovRepository.save(item);

			});

			dto.getItens().forEach(i -> {
				if (romaneioService.isRoamneio(i.getIdRomaneio()).getSituacao() != "Pago") {
					if (prRepository.isEstoque(i.getIdRomaneio()) == 0) {
						romaneioRepository.updateSituacao(i.getIdRomaneio(), "Fechado");
					}
				}
			});

		}

		return pedidoSave;
	}

	public Pedido update(Long id, PedidoNewDTO dto) {

		this.pedidoSave = isExists(id);

		pedidoSave.setData(dto.getDataPedido());
		pedidoSave.setValor(dto.getValorTotal());
		pedidoSave.setSituacao(dto.getSituacao());

		Cliente cliente = cliService.isExists(dto.getCliente().getId());
		pedidoSave.setCliente(cliente);

		pedidoSave = repository.saveAndFlush(pedidoSave);

		List<ItemPedido> itensSave = itemPedidoRepository.findByPedidoId(pedidoSave.getId());

		itensSave.forEach(iSave -> {

			// RETIRANDO PENDENCIA DOS ANTIGOS ITENS
			prRepository.updateRetQtPendente(iSave.getQtd(), iSave.getProdutoRomaneio().getId());

			// DELETE LIMPA
			if (iSave.getLimpa() != null) {
				limpaRepository.deleteById(iSave.getLimpa().getId());
			}

		});

		// DELETE ITEMS DO PEDIDO
		itemPedidoRepository.deleteByPedidoId(pedidoSave.getId());

		if (dto.getSituacao().equals("Pendente")) {

			dto.getItens().forEach(iN -> {

				ItemPedido ip = new ItemPedido();
				ip.setQtd(iN.getQtd());
				ip.setValorUnitario(iN.getValorUnitario());
				ip.setValorTotal(iN.getValorTotal());
				ProdutoRomaneio pr = prService.isExists(iN.getIdProdutoRomaneio());
				ip.setProdutoRomaneio(pr);
				ip.setPedido(pedidoSave);
				ip = itemPedidoRepository.save(ip);

				// UPDATE QTD PEDENTE
				prRepository.updateAddQtPendente(iN.getQtd(), iN.getIdProdutoRomaneio());

				if (iN.getQtdLimpa() > 0) {
					Limpa limpa = new Limpa();
					limpa.setStatus("Aberto");
					limpa.setStatusPedido("Pendente");
					limpa.setQtd(iN.getQtdLimpa());
					limpa.setDataInicio(LocalDate.now());
					limpa.setItemPedido(ip);
					limpaRepository.save(limpa);
				}

			});
		}

		else if (dto.getSituacao().equals("Fechado")) {

			dto.getItens().forEach(iN -> {

				ItemPedido ip = new ItemPedido();
				ip.setQtd(iN.getQtd());
				ip.setValorUnitario(iN.getValorUnitario());
				ip.setValorTotal(iN.getValorTotal());
				ProdutoRomaneio pr = prService.isExists(iN.getIdProdutoRomaneio());
				ip.setProdutoRomaneio(pr);
				ip.setPedido(pedidoSave);
				ip = itemPedidoRepository.save(ip);

				// UPDATE QTD ESTOQUE
				prRepository.updateQtdAtual(iN.getQtd(), iN.getIdProdutoRomaneio());

				if (iN.getQtdLimpa() > 0) {
					Limpa limpa = new Limpa();
					limpa.setStatus("Aberto");
					limpa.setStatusPedido("Fechado");
					limpa.setQtd(iN.getQtdLimpa());
					limpa.setDataInicio(LocalDate.now());
					limpa.setItemPedido(ip);
					limpaRepository.save(limpa);
				}
			});

			// INSERT MOVIMENTAÇÃO CAIXA
			MovCaixa mov = new MovCaixa();

			mov.setData(LocalDate.now());
			mov.setValor(pedidoSave.getValor());
			mov.setDescricao("Venda pedido n° " + pedidoSave.getNumero());
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
					np.setEmitente(cliente.getNomeRazao());

					NotaPromissoria npSave = npRepository.save(np);

					item.setNp(npSave);
				}

				item.setMovCaixa(movSave);
				itemMovRepository.save(item);

			});

			dto.getItens().forEach(i -> {
				if (romaneioService.isRoamneio(i.getIdRomaneio()).getSituacao() != "Pago") {
					if (prRepository.isEstoque(i.getIdRomaneio()) == 0) {
						romaneioRepository.updateSituacao(i.getIdRomaneio(), "Fechado");
					}
				}
			});

		}

		return pedidoSave;
	}

	public void remove(Long id) {
		Pedido objSave = isExists(id);

		if (objSave.getSituacao().equals("Pendente")) {
			repository.deleteById(objSave.getId());

			objSave.getItens().forEach(i -> {
				prRepository.updateRetQtPendente(i.getQtd(), i.getProdutoRomaneio().getId());
			});
		}
	}

	public byte[] pedidoVendasDoc(Long id) throws Exception {

		Pedido pedido = repository.findById(id).get();

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("PEDIDO", pedido);
		parameters.put("DATA_PEDIDO", pedido.getData() == null ? null : Convert.asDate(pedido.getData()));
		parameters.put("URL_LOGO", this.getClass().getResource("/docs/jdg-logo.png").toString());
		parameters.put("SUBLOCALE", this.getClass().getResource("/docs/sub-pagamentos.jasper").toString());
		parameters.put("REPORT_LOCALE", new Locale("pt", "BR"));

		List<ItensPedJasperDTO> list = new ArrayList<>();
		List<PagItemPedJasperDTO> pagamentos = new ArrayList<>();

		for (int i = 0; i < pedido.getItens().size(); i++) {

			ItensPedJasperDTO dto = new ItensPedJasperDTO();

			dto.setProduto(pedido.getItens().get(i).getProdutoRomaneio().getProduto());
			dto.setCategoria(pedido.getItens().get(i).getProdutoRomaneio().getCategoria());
			dto.setValorUnitario(pedido.getItens().get(i).getValorUnitario());
			dto.setQtd(pedido.getItens().get(i).getQtd());
			dto.setValorTotal((pedido.getItens().get(i).getValorUnitario() * pedido.getItens().get(i).getQtd()));
			dto.setNumeroRomaneio(pedido.getItens().get(i).getProdutoRomaneio().getRomaneio().getNumero());

			list.add(dto);
		}

		List<ItemMov> im = imRepository.getByPedidoId(pedido.getId());

		im.forEach(p -> {

			PagItemPedJasperDTO pag = new PagItemPedJasperDTO();
			pag.setTipoPagamento(p.getTipo());

			if (p.getTipo().equals("À vista")) {

				Caixa c = caixaRepository.getOne(p.getCaixa().getId());
				pag.setDataVencimento(Convert.asDate(c.getData()));
				pag.setDataPagamento(Convert.asDate(c.getData()));

			} else if (p.getTipo().equals("Cheque")) {

				Cheque ch = chRepository.getOne(p.getCheque().getId());
				pag.setDataVencimento(ch.getDtVen() == null ? null : Convert.asDate(ch.getDtVen()));
				pag.setDataPagamento(ch.getDtPag() == null ? null : Convert.asDate(ch.getDtPag()));

			} else if (p.getTipo().equals("Nota Promissória")) {

				NotaPromissoria np = npRepository.getOne(p.getNp().getId());

				pag.setDataVencimento(np.getDtVen() == null ? null : Convert.asDate(np.getDtVen()));
				pag.setDataPagamento(np.getDtPag() == null ? null : Convert.asDate(np.getDtPag()));
			}

			pag.setValor(p.getValor());
			pagamentos.add(pag);
		});

		list.get(list.size() - 1).setPagamentos(pagamentos);

		parameters.put("TOTAL", list.stream().map(e -> e.getValorTotal()).reduce((float) 0, (x, y) -> x + y));

		InputStream inputStream = this.getClass().getResourceAsStream("/docs/pedido-vendas.jasper");
		JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parameters,
				new JRBeanCollectionDataSource(list));
		return JasperExportManager.exportReportToPdf(jasperPrint);
	}

	public Pedido isExists(Long id) {
		Optional<Pedido> objSave = repository.findById(id);
		if (!objSave.isPresent()) {
			throw new IllegalArgumentException();
		}
		return objSave.get();
	}
}
