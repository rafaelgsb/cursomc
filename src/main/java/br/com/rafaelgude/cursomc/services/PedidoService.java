package br.com.rafaelgude.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rafaelgude.cursomc.domain.PagamentoComBoleto;
import br.com.rafaelgude.cursomc.domain.Pedido;
import br.com.rafaelgude.cursomc.domain.enums.EstadoPagamento;
import br.com.rafaelgude.cursomc.exceptions.ObjectNotFoundException;
import br.com.rafaelgude.cursomc.repositories.ItemPedidoRepository;
import br.com.rafaelgude.cursomc.repositories.PagamentoRepository;
import br.com.rafaelgude.cursomc.repositories.PedidoRepository;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepo;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepo;
	
	@Autowired
	private ClienteService clienteService;
	
	public Pedido find(Integer id) {
		return repo.findById(id).orElseThrow(() -> 
			new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo:" + Pedido.class.getName()));
	}
	
	public Pedido insert(Pedido pedido) {
		pedido.setId(null);
		pedido.setCliente(clienteService.find(pedido.getCliente().getId()));
		pedido.setInstante(new Date());
		pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);
		
		if (pedido.getPagamento() instanceof PagamentoComBoleto) {
			var pagamento = (PagamentoComBoleto) pedido.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagamento, pedido.getInstante());
		}
		
		final var obj = repo.save(pedido);
		pagamentoRepo.save(obj.getPagamento());
		
		obj.getItens().forEach(x -> {
			x.setProduto(produtoService.find(x.getProduto().getId()));
			x.setDesconto(0.00);
			x.setPreco(x.getProduto().getPreco());
			x.setPedido(obj);
		});
		
		itemPedidoRepo.saveAll(obj.getItens());
		System.out.println(obj);
		return obj;
	}
	
}
