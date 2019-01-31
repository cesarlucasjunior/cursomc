package com.cursojava.cursomc.resource.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

//Classe responsável por decodificar um tipo, especificado no parâmetro do método, em outro necessário.

public class Decoder {
	
	public static List<Integer> decodeStringInListInt(String string){
		//Pega a String e separa em elementos entre vírgulas e adiciona num vetor.
		String[] vetor = string.split(",");
		List<Integer> lista = new ArrayList<Integer>();
		
		for(int i=0; i<vetor.length; i++) {
			lista.add(Integer.parseInt(vetor[i]));
		}
		
		return lista;
	}
	
	public static String decodeParamString(String string) {
		try {
			return URLDecoder.decode(string, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";		
			}
	}

}
