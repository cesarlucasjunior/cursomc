package com.cursojava.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.cursojava.cursomc.domain.Cliente;
import com.cursojava.cursomc.domain.enums.TipoCliente;
import com.cursojava.cursomc.dto.ClienteInsertDTO;
import com.cursojava.cursomc.repositories.ClienteRepository;
import com.cursojava.cursomc.resource.exception.FieldMessage;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteInsertDTO> {

	//Essa classe é responsável por especificar a validação em si, ou seja, é o que tem "dentro" da anotação.
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public void initialize(ClienteInsert cliente) {
	}

	public boolean isValid(ClienteInsertDTO clienteDTO, ConstraintValidatorContext contexto) {

		List<FieldMessage> lista = new ArrayList<>();

		if (clienteDTO.getTipoCliente().equals(TipoCliente.PESSOAFISICA.getId())
				&& !CpfCnpjValidation.isValidCPF(clienteDTO.getCpfOuCnpj())) {
			lista.add(new FieldMessage("cpfOuCnpj", "CPF inválido!"));
		}

		if (clienteDTO.getTipoCliente().equals(TipoCliente.PESSOAJURIDICA.getId())
				&& !CpfCnpjValidation.isValidCNPJ(clienteDTO.getCpfOuCnpj())) {
			lista.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido!"));
		}
		
		//Validação de e-mail:
		
		Cliente cliente = clienteRepository.findByEmail(clienteDTO.getEmail());
		
		if(cliente != null) {
			lista.add(new FieldMessage("email", "Esse e-mail já está cadastrado!"));
		}

		for (FieldMessage e : lista) {
			contexto.disableDefaultConstraintViolation();
			contexto.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		
		return lista.isEmpty();
	}

}
