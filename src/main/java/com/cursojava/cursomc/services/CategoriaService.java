package com.cursojava.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.cursojava.cursomc.domain.Categoria;
import com.cursojava.cursomc.repositories.CategoriaRepository;
import com.cursojava.cursomc.services.exception.DataIntegrityException;
import com.cursojava.cursomc.services.exception.ObjectNotFoundException;

@Service
public class CategoriaService {

	// Na classe Services teremos aplicação do modelo de negócio que não temos na
	// camada de domínio
	// além disso, aqui acessamos os dados definidos na repository e enviamos para a
	// controladora Rest
	// (resource) disponilizar à Aplicação do Cliente.

	@Autowired
	private CategoriaRepository repo;

	// Aqui teremos o método que acessará os dados enviando o resultado ao REST:
	public Categoria find(Integer id) {

		Optional<Categoria> obj = repo.findById(id);

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id " + id + ", Tipo: " + Categoria.class.getName()));
	}

	// Método responsável por pegar uma Categoria e persisti-la no banco de dados.
	public Categoria insert(Categoria obj) {
		obj.setId(null); // Para a inserção dar certo - se tiver id a JPA atualizará o registro.
		return repo.save(obj);
	}

	// Atualização de categorias já persistidas
	public Categoria update(Categoria obj) {
		find(obj.getId()); // Verificação se existe o id, do contrário, lança uma exceção
		return repo.save(obj);
	}

	// Deleção de dados:
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException(
					"Não é possível excluir essa categoria porque ela possui um produto associado!");
		}
	}
	
	//Buscar todos as categorias:
	public List<Categoria> findAll(){
		return repo.findAll();
	}
}
