package com.cursojava.cursomc.resource.exception;

public class ValidationError extends StandartError  {

	public ValidationError(Integer status, String mensagem, Long timeStamp) {
		super(status, mensagem, timeStamp);
	}
	
	

}
