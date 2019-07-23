package br.com.rafaelgude.cursomc.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rafaelgude.cursomc.domain.Categoria;
import br.com.rafaelgude.cursomc.domain.Cidade;
import br.com.rafaelgude.cursomc.domain.Cliente;
import br.com.rafaelgude.cursomc.domain.Endereco;
import br.com.rafaelgude.cursomc.domain.Estado;
import br.com.rafaelgude.cursomc.domain.ItemPedido;
import br.com.rafaelgude.cursomc.domain.Pagamento;
import br.com.rafaelgude.cursomc.domain.PagamentoComBoleto;
import br.com.rafaelgude.cursomc.domain.PagamentoComCartao;
import br.com.rafaelgude.cursomc.domain.Pedido;
import br.com.rafaelgude.cursomc.domain.Produto;
import br.com.rafaelgude.cursomc.domain.enums.EstadoPagamento;
import br.com.rafaelgude.cursomc.domain.enums.TipoCliente;
import br.com.rafaelgude.cursomc.repositories.CategoriaRepository;
import br.com.rafaelgude.cursomc.repositories.CidadeRepository;
import br.com.rafaelgude.cursomc.repositories.ClienteRepository;
import br.com.rafaelgude.cursomc.repositories.EnderecoRepository;
import br.com.rafaelgude.cursomc.repositories.EstadoRepository;
import br.com.rafaelgude.cursomc.repositories.ItemPedidoRepository;
import br.com.rafaelgude.cursomc.repositories.PagamentoRepository;
import br.com.rafaelgude.cursomc.repositories.PedidoRepository;
import br.com.rafaelgude.cursomc.repositories.ProdutoRepository;

@Service
public class DBService {
	
	@Autowired
	private CategoriaRepository categoriaRepo;
	
	@Autowired
	private ProdutoRepository produtoRepo;
	
	@Autowired
	private EstadoRepository estadoRepo;
	
	@Autowired
	private CidadeRepository cidadeRepo;
	
	@Autowired
	private ClienteRepository clienteRepo;
	
	@Autowired
	private EnderecoRepository enderecoRepo;
	
	@Autowired
	private PedidoRepository pedidoRepo;
	
	@Autowired
	private PagamentoRepository pagamentoRepo;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepo;

	public void instantiateTestDatabase() throws ParseException {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama mesa e banho");
		Categoria cat4 = new Categoria(null, "Eletrônicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoração");
		Categoria cat7 = new Categoria(null, "Perfumaria");
		Categoria cat8 = new Categoria(null, "Teste");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		Produto p4 = new Produto(null, "Mesa de escritório", 300.00);
		Produto p5 = new Produto(null, "Toalha", 50.00);
		Produto p6 = new Produto(null, "Colcha", 200.00);
		Produto p7 = new Produto(null, "TV true color", 1200.00);
		Produto p8 = new Produto(null, "Roçadeira", 800.00);
		Produto p9 = new Produto(null, "Abajour", 100.00);
		Produto p10 = new Produto(null, "Pendente", 180.00);
		Produto p11 = new Produto(null, "Shampoo", 90.00);
		
		cat1.getProdutos().addAll(List.of(p1, p2, p3));
		cat2.getProdutos().addAll(List.of(p2, p4));
		cat3.getProdutos().addAll(List.of(p5, p6));
		cat4.getProdutos().addAll(List.of(p1, p2, p3, p7));
		cat5.getProdutos().addAll(List.of(p8));
		cat6.getProdutos().addAll(List.of(p9, p10));
		cat7.getProdutos().addAll(List.of(p11));
		
		p1.getCategorias().addAll(List.of(cat1, cat4));
		p2.getCategorias().addAll(List.of(cat1, cat2, cat4));
		p3.getCategorias().addAll(List.of(cat1, cat4));
		p4.getCategorias().addAll(List.of(cat2));
		p5.getCategorias().addAll(List.of(cat3));
		p6.getCategorias().addAll(List.of(cat3));
		p7.getCategorias().addAll(List.of(cat4));
		p8.getCategorias().addAll(List.of(cat5));
		p9.getCategorias().addAll(List.of(cat6));
		p10.getCategorias().addAll(List.of(cat6));
		p11.getCategorias().addAll(List.of(cat7));
				
		categoriaRepo.saveAll(List.of(cat1, cat2, cat3, cat4, cat5, cat6, cat7, cat8));
		produtoRepo.saveAll(List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(List.of(c1));
		est2.getCidades().addAll(List.of(c2, c3));

		estadoRepo.saveAll(List.of(est1, est2));
		cidadeRepo.saveAll(List.of(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOA_FISICA);
		
		cli1.getTelefones().addAll(List.of("27363323", "93838393"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
		
		cli1.getEnderecos().addAll(List.of(e1, e2));
		
		clienteRepo.saveAll(List.of(cli1));
		enderecoRepo.saveAll(List.of(e1, e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(List.of(ped1, ped2));
		
		pedidoRepo.saveAll(List.of(ped1, ped2));
		pagamentoRepo.saveAll(List.of(pagto1, pagto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(List.of(ip1, ip2));
		ped2.getItens().addAll(List.of(ip3));
		
		p1.getItens().addAll(List.of(ip1));
		p2.getItens().addAll(List.of(ip3));
		p3.getItens().addAll(List.of(ip2));
		
		itemPedidoRepo.saveAll(List.of(ip1, ip2, ip3));		
	}

}
