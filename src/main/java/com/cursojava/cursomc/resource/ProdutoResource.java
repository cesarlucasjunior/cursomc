package com.cursojava.cursomc.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cursojava.cursomc.domain.Produto;
import com.cursojava.cursomc.dto.ProdutoDTO;
import com.cursojava.cursomc.resource.util.Decoder;
import com.cursojava.cursomc.services.ProdutoService;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

	@Autowired
	private ProdutoService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Produto> find(@PathVariable Integer id) {
		Produto obj = service.find(id);

		return ResponseEntity.ok().body(obj);

	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value="nome", defaultValue="") String nome, //Se esse nome vier com espaço temos que decodifica-lo
			@RequestParam(value="categorias", defaultValue="0") String categorias,
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy,
			@RequestParam(value="direction", defaultValue="ASC") String direction){
		
		String nomeDecoded = Decoder.decodeParamString(nome);
		List<Integer> ids = Decoder.decodeStringInListInt(categorias);
	
		Page<Produto> list = service.search(nomeDecoded, ids , page, linesPerPage, orderBy, direction);
		//A list está vindo vazia!
		Page<ProdutoDTO> listDTO = list.map(obj -> new ProdutoDTO(obj));	
		System.out.println(listDTO.getNumberOfElements());	
		return ResponseEntity.ok().body(listDTO);
		
	}

}
