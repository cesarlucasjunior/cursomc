package com.cursojava.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cursojava.cursomc.domain.Categoria;
import com.cursojava.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	//Na classe Services teremos aplicação do modelo de negócio que não temos na camada de domínio
	// além disso, aqui acessamos os dados definidos na repository e enviamos para a controladora Rest
	// (resource) disponilizar à Aplicação do Cliente.
	
	@Autowired
	private CategoriaRepository repo;
	
	
	// Aqui teremos o método que acessará os dados enviando o resultado ao REST:
	
	public Categoria find(Integer id) {
		
		Optional<Categoria> obj = repo.findById(id);
		
		return obj.orElse(null);
	}
	
}
