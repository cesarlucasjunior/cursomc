package com.cursojava.cursomc.resource.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cursojava.cursomc.services.exception.ObjectNotFoundException;

//Ele pega os erros definidos categorizando e padronizado-os como standart.

@ControllerAdvice
public class ResourceExceptionHandler {
	
	//método responsável por capturar a exception, padroniza-lo e categoriza-lo.
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandartError> objectNotFoundException(ObjectNotFoundException erro, HttpServletRequest req){
		
		StandartError error = new StandartError(HttpStatus.NOT_FOUND.value(), erro.getMessage(), System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	

}
