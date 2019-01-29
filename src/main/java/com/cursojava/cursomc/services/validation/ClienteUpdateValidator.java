package com.cursojava.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.cursojava.cursomc.domain.Cliente;
import com.cursojava.cursomc.dto.ClienteDTO;
import com.cursojava.cursomc.repositories.ClienteRepository;
import com.cursojava.cursomc.resource.exception.FieldMessage;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private HttpServletRequest request;

	public void initialize(ClienteInsert cliente) {
	}

	public boolean isValid(ClienteDTO clienteDTO, ConstraintValidatorContext contexto) {

		// Buscando id vinda da request:

		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request
				.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

		Integer id = Integer.parseInt(map.get("id"));

		List<FieldMessage> lista = new ArrayList<>();

		// Validação de e-mail com id cliente:

		Cliente cliente = clienteRepository.findByEmail(clienteDTO.getEmail());

		if (cliente != null && !cliente.getId().equals(id)) {
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
