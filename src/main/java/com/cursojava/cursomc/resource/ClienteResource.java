package com.cursojava.cursomc.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cursojava.cursomc.domain.Cliente;
import com.cursojava.cursomc.services.ClienteService;

@RestController
@RequestMapping(value="/cliente")
public class ClienteResource {
	
	@Autowired
	private ClienteService service;
	
	// Padrão resource - classes responsáveis por guardar controladores REST, ou seja, responsável por receber uma request
	// e realizar uma atividade específica aos parâmetros da requisição.
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		
		Cliente obj = service.find(id);
		
		//Já retorna o objeto em formato JSON
		return ResponseEntity.ok().body(obj);
		
	}

}
