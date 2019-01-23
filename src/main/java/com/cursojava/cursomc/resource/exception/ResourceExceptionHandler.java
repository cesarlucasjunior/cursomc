package com.cursojava.cursomc.resource.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cursojava.cursomc.services.exception.DataIntegrityException;
import com.cursojava.cursomc.services.exception.ObjectNotFoundException;

//Ele pega os erros definidos categorizando e padronizado-os como standart.

@ControllerAdvice
public class ResourceExceptionHandler {

	// método responsável por capturar a exception, padroniza-lo e categoriza-lo.

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandartError> objectNotFoundException(ObjectNotFoundException erro, HttpServletRequest req) {

		// ResponseEntity assim como na resource visto que precisamos devolver em JSON e
		// nas especificações Http.

		StandartError error = new StandartError(HttpStatus.NOT_FOUND.value(), erro.getMessage(),
				System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandartError> dataIntegrity(DataIntegrityException erro, HttpServletRequest req) {

		// ResponseEntity assim como na resource visto que precisamos devolver em JSON e
		// nas especificações Http.

		StandartError error = new StandartError(HttpStatus.BAD_REQUEST.value(), erro.getMessage(),
				System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandartError> erroValidacao(MethodArgumentNotValidException erro, HttpServletRequest req) {

		// ResponseEntity assim como na resource visto que precisamos devolver em JSON e
		// nas especificações Http.

		StandartError error = new StandartError(HttpStatus.BAD_REQUEST.value(), "Erro durante a validação!",
				System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
}
