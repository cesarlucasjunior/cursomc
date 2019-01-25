package com.cursojava.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.cursojava.cursomc.domain.Categoria;
import com.cursojava.cursomc.domain.Cliente;
import com.cursojava.cursomc.dto.ClienteDTO;
import com.cursojava.cursomc.repositories.ClienteRepository;
import com.cursojava.cursomc.services.exception.DataIntegrityException;
import com.cursojava.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public Cliente find(Integer id) {
		Optional<Cliente> obj = clienteRepository.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id " + id + ", Tipo: " + Categoria.class.getName()));
	}

	public Cliente insert(Cliente cliente) {
		cliente.setId(null);
		return clienteRepository.save(cliente);
	}

	public Cliente update(ClienteDTO clienteDTO) {
		// Só quero atualizar os atributos do DTO:
		Cliente novoCliente = find(clienteDTO.getId());
		novoCliente.setNome(clienteDTO.getNome());
		novoCliente.setEmail(clienteDTO.getEmail());
		
		return clienteRepository.save(novoCliente);
	}

	public void delete(Integer id) {
		find(id);
		try {
			clienteRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException(
					"Não é possível excluir esse cliente porque ela possui outra tabela associada!");
		}
	}

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return clienteRepository.findAll(pageRequest);
	}

	public Cliente fromDTO(ClienteDTO clienteDTO) {
		return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null);
	}

}
