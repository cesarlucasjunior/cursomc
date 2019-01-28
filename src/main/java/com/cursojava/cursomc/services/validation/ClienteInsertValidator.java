package com.cursojava.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.cursojava.cursomc.domain.enums.TipoCliente;
import com.cursojava.cursomc.dto.ClienteInsertDTO;
import com.cursojava.cursomc.resource.exception.FieldMessage;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteInsertDTO> {

	//Essa classe é responsável por especificar a validação em si, ou seja, é o que tem "dentro" da anotação.
	
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

		for (FieldMessage e : lista) {
			contexto.disableDefaultConstraintViolation();
			contexto.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}

		return lista.isEmpty();
	}

}
