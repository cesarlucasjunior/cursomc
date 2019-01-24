package com.cursojava.cursomc.resource.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandartError {
	private static final long serialVersionUID = 1L;

	public ValidationError(Integer status, String mensagem, Long timeStamp) {
		super(status, mensagem, timeStamp);
	}

	private List<FieldMessage> listaCampo = new ArrayList<>();

	public List<FieldMessage> getListaCampo() {
		return listaCampo;
	}

	public void addError(String fieldName, String mensagem) {
		listaCampo.add(new FieldMessage(fieldName, mensagem));
	}

}
