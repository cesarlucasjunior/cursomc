package com.cursojava.cursomc.services;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cursojava.cursomc.domain.ItemPedido;
import com.cursojava.cursomc.domain.PagamentoComBoleto;
import com.cursojava.cursomc.domain.Pedido;
import com.cursojava.cursomc.domain.enums.EstadoPagamento;
import com.cursojava.cursomc.repositories.ItemPedidoRepository;
import com.cursojava.cursomc.repositories.PagamentoRepository;
import com.cursojava.cursomc.repositories.PedidoRepository;
import com.cursojava.cursomc.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {

	//Na classe Services teremos aplicação do modelo de negócio que não temos na camada de domínio
	// além disso, aqui acessamos os dados definidos na repository e enviamos para a controladora Rest
	// (resource) disponilizar à Aplicação do Cliente.
	
	@Autowired
	private PedidoRepository repo;
	@Autowired
	private BoletoService boletoService;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ProdutoService produtoService;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	@Autowired
	private ClienteService clienteService;
	
	
	// Aqui teremos o método que acessará os dados enviando o resultado ao REST:
	
	public Pedido find(Integer id) {
		
		Optional<Pedido> obj = repo.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id " + id +
				", Tipo: " + Pedido.class.getName()));
	}
	
	@Transactional
	public Pedido insert(Pedido pedido) {
		// Nesse método precisamos pegar o pedido e instancia-lo assim como as suas associações.
		//Quando o objeto já estiver instanciado, só mantê-lo, quando não precisamos instanciá-lo.
		
		pedido.setId(null);
		pedido.setInstante(new Date());
		
		pedido.setCliente(clienteService.find(pedido.getCliente().getId()));
		
		pedido.getPagamento().setEstadoPagamento(EstadoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);
		
		if(pedido.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pgtoBoleto = (PagamentoComBoleto) pedido.getPagamento();
			boletoService.preencherPagamentoComBoleto(pgtoBoleto, pedido.getInstante());			
		}
		
		pedido = repo.save(pedido);
		pagamentoRepository.save(pedido.getPagamento());
		
		
		for(ItemPedido ip : pedido.getItens()) {
			
			System.out.println("--------" + ip.getProduto().getId());
			
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(produtoService.find(ip.getProduto().getId()).getPreco());
			ip.setPedido(pedido);
		}
		
		itemPedidoRepository.saveAll(pedido.getItens());
		
		System.out.println(pedido);
		
		return pedido;		
	}
	
}
