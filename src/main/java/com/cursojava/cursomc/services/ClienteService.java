package com.cursojava.cursomc.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.cursojava.cursomc.domain.Categoria;
import com.cursojava.cursomc.domain.Cidade;
import com.cursojava.cursomc.domain.Cliente;
import com.cursojava.cursomc.domain.Endereco;
import com.cursojava.cursomc.domain.enums.TipoCliente;
import com.cursojava.cursomc.dto.ClienteDTO;
import com.cursojava.cursomc.dto.ClienteInsertDTO;
import com.cursojava.cursomc.repositories.CidadeRepository;
import com.cursojava.cursomc.repositories.ClienteRepository;
import com.cursojava.cursomc.repositories.EnderecoRepository;
import com.cursojava.cursomc.services.exception.DataIntegrityException;
import com.cursojava.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;

	public Cliente find(Integer id) {
		Optional<Cliente> obj = clienteRepository.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id " + id + ", Tipo: " + Categoria.class.getName()));
	}

	@Transactional
	public Cliente insert(Cliente cliente) {
		cliente.setId(null);
		enderecoRepository.saveAll(cliente.getEnderecos());
		cliente = clienteRepository.save(cliente);
		
		return cliente;
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
					"Não é possível excluir esse cliente porque ele possui um pedido associado!");
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

	public Cliente fromDTO(ClienteInsertDTO clienteInsertDTO) {
		Cliente cliente = new Cliente(null, clienteInsertDTO.getNome(), clienteInsertDTO.getEmail(),
				clienteInsertDTO.getCpfOuCnpj(), TipoCliente.toEnum(clienteInsertDTO.getTipoCliente()));
		
		//Buscando cidade para incluí-la no endereço e, consequentemente, no cliente.
		Optional<Cidade> cidade = cidadeRepository.findById(clienteInsertDTO.getCidadeId());
		
		Endereco endereco = new Endereco(null, clienteInsertDTO.getLogradouro(), clienteInsertDTO.getNumero(), clienteInsertDTO.getComplemento(),
				clienteInsertDTO.getBairro(), clienteInsertDTO.getCep(), cliente, cidade.get());
		
		cliente.getEnderecos().add(endereco);
		
		//Adicionando telefones:
		cliente.getTelefones().add(clienteInsertDTO.getTelefone1());
		
		if(clienteInsertDTO.getTelefone2() != null) {
			cliente.getTelefones().add(clienteInsertDTO.getTelefone2());
		}
		
		if(clienteInsertDTO.getTelefone3() != null) {
			cliente.getTelefones().add(clienteInsertDTO.getTelefone3());
		}
		
		return cliente;
	}
}
