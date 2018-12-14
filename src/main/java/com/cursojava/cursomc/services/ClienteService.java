package com.cursojava.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cursojava.cursomc.domain.Categoria;
import com.cursojava.cursomc.domain.Cliente;
import com.cursojava.cursomc.repositories.ClienteRepository;
import com.cursojava.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {
	
	//A classe tipo Service é uma ponte entre os dados e os endpoint. Nela, separamos a responsabilidade
	//de buscar um dados específico e disponibiliza-la ao resource.
	@Autowired
	private ClienteRepository clienteRepository;
	
	// Aqui teremos o método que acessará os dados enviando o resultado ao REST:
	
		public Cliente find(Integer id) {
			
			Optional<Cliente> obj = clienteRepository.findById(id);
			
			return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id " + id +
					", Tipo: " + Categoria.class.getName()));
		}
		
	
	

}
