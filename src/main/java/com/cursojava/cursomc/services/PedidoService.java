package com.cursojava.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cursojava.cursomc.domain.Pedido;
import com.cursojava.cursomc.repositories.PedidoRepository;
import com.cursojava.cursomc.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {

	//Na classe Services teremos aplicação do modelo de negócio que não temos na camada de domínio
	// além disso, aqui acessamos os dados definidos na repository e enviamos para a controladora Rest
	// (resource) disponilizar à Aplicação do Cliente.
	
	@Autowired
	private PedidoRepository repo;
	
	
	// Aqui teremos o método que acessará os dados enviando o resultado ao REST:
	
	public Pedido find(Integer id) {
		
		Optional<Pedido> obj = repo.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id " + id +
				", Tipo: " + Pedido.class.getName()));
	}
	
}
