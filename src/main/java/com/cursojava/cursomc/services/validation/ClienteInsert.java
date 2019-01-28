package com.cursojava.cursomc.services.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy=ClienteInsertValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ClienteInsert {
	
	//Essa classe é a classe da anotação em si.
	
	String message()default "Erro de Validação!";
	
	Class<?>[] groups() default{};
	
	Class<? extends Payload>[] payload() default{};
}
