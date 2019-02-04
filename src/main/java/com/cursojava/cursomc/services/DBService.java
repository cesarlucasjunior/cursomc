package com.cursojava.cursomc.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cursojava.cursomc.domain.Categoria;
import com.cursojava.cursomc.domain.Cidade;
import com.cursojava.cursomc.domain.Cliente;
import com.cursojava.cursomc.domain.Endereco;
import com.cursojava.cursomc.domain.Estado;
import com.cursojava.cursomc.domain.ItemPedido;
import com.cursojava.cursomc.domain.Pagamento;
import com.cursojava.cursomc.domain.PagamentoComBoleto;
import com.cursojava.cursomc.domain.PagamentoComCartao;
import com.cursojava.cursomc.domain.Pedido;
import com.cursojava.cursomc.domain.Produto;
import com.cursojava.cursomc.domain.enums.EstadoPagamento;
import com.cursojava.cursomc.domain.enums.TipoCliente;
import com.cursojava.cursomc.repositories.CategoriaRepository;
import com.cursojava.cursomc.repositories.CidadeRepository;
import com.cursojava.cursomc.repositories.ClienteRepository;
import com.cursojava.cursomc.repositories.EnderecoRepository;
import com.cursojava.cursomc.repositories.EstadoRepository;
import com.cursojava.cursomc.repositories.ItemPedidoRepository;
import com.cursojava.cursomc.repositories.PagamentoRepository;
import com.cursojava.cursomc.repositories.PedidoRepository;
import com.cursojava.cursomc.repositories.ProdutoRepository;

@Service
public class DBService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;	
	
	
	public void instatiateTestDatabase() throws ParseException {
		
		Categoria c1 = new Categoria(null, "Informática");
		Categoria c2 = new Categoria(null, "Escritório");
		Categoria c3 = new Categoria(null, "Cama mesa e banho");
		Categoria c4 = new Categoria(null, "Papelaria");
		Categoria c5 = new Categoria(null, "Administrativo");
		Categoria c6 = new Categoria(null, "Comercial");
		Categoria c7 = new Categoria(null, "Finanças");
	
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
	
		c1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		c2.getProdutos().addAll(Arrays.asList(p2, p4));
		c3.getProdutos().addAll(Arrays.asList(p5, p6));
		c4.getProdutos().addAll(Arrays.asList(p1, p2, p3, p7));
		c5.getProdutos().addAll(Arrays.asList(p8));
		c6.getProdutos().addAll(Arrays.asList(p9, p10));
		c7.getProdutos().addAll(Arrays.asList(p11));
	
		p1.getCategorias().addAll(Arrays.asList(c1, c4));
		p2.getCategorias().addAll(Arrays.asList(c1, c2, c4));
		p3.getCategorias().addAll(Arrays.asList(c1, c4));
		p4.getCategorias().addAll(Arrays.asList(c2));
		p5.getCategorias().addAll(Arrays.asList(c3));
		p6.getCategorias().addAll(Arrays.asList(c3));
		p7.getCategorias().addAll(Arrays.asList(c4));
		p8.getCategorias().addAll(Arrays.asList(c5));
		p9.getCategorias().addAll(Arrays.asList(c6));
		p10.getCategorias().addAll(Arrays.asList(c6));
		p11.getCategorias().addAll(Arrays.asList(c7));	
	
		// Método saveAll() para instanciar os objetos no banco de dados.
		categoriaRepository.saveAll(Arrays.asList(c1, c2, c3, c4, c5, c6, c7));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
	
		// Intanciar Estado e Cidade assim como vincula-los uns aos outros.
	
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		Estado est3 = new Estado(null, "Distrito Federal");
	
		// Perceba que o Estado já está vinculado à Cidade instanciada.
		Cidade cd1 = new Cidade(null, "Uberlândia", est1);
		Cidade cd2 = new Cidade(null, "São Paulo", est2);
		Cidade cd3 = new Cidade(null, "Campinas", est2);
		Cidade cd4 = new Cidade(null, "Brasília", est3);
		
	
		// Falta agora vincular as cidades aos Estados:
	
		est1.setCidades(Arrays.asList(cd1));
		est2.getCidades().addAll(Arrays.asList(cd2, cd3));
		est3.getCidades().addAll(Arrays.asList(cd4));
		
		// Realizando a persistencia dos objetos criados:
	
		// Estado obrigatoriamente deve ser o primeiro já que a cidade precisa,
		// obrigatoriamente, ter um Estado
		estadoRepository.saveAll(Arrays.asList(est1, est2,est3));
	
		cidadeRepository.saveAll(Arrays.asList(cd1, cd2, cd3, cd4));
	
		// Instanciando Cliente e Endereço:
	
		Cliente cl1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "03456798212", TipoCliente.PESSOAFISICA);
		// cl1.setTelefones("");
		cl1.getTelefones().addAll(Arrays.asList("33991228", "34561233"));
	
		Endereco end1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "71908432", cl1, cd1);
		Endereco end2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "32190843", cl1, cd2);
	
		// Associando os enderecos aos clientes
	
		cl1.getEnderecos().addAll(Arrays.asList(end1, end2));
	
		clienteRepository.save(cl1);
		enderecoRepository.saveAll(Arrays.asList(end1, end2));
	
		// Instanciação de Pedidos e Pagamentos:
	
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cl1, end1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cl1, end2);
	
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
	
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"),
				null);
		ped2.setPagamento(pagto2);
	
		// cl1.setPedidos(Arrays.asList(ped1,ped2));
		cl1.getPedidos().addAll(Arrays.asList(ped1, ped2));
	
		pedidoRepository.save(ped1);
		pedidoRepository.save(ped2);
	
		pagamentoRepository.save(pagto1);
		pagamentoRepository.save(pagto2);
		
		//Instanciação da classe ItemPedido:
		
		ItemPedido ip1 = new ItemPedido(ped1,p1,0.0,1,2000.00);
		ItemPedido ip2 = new ItemPedido(ped2,p2,0.0,2,800.00);
		ItemPedido ip3 = new ItemPedido(ped1,p3,100.00,1,8000.00);
		
		//Associando as instancias de ItemPedido com o Pedido
		
		ped1.getItens().addAll(Arrays.asList(ip1,ip3));
		ped2.getItens().addAll(Arrays.asList(ip2));
		
		//Associando as instancias de ItemPedido com o Produto
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip2));
		p3.getItens().addAll(Arrays.asList(ip3));
		
		//Salvando a instancia no banco de dados:
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));

	}
}
