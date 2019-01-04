package com.cursojava.cursomc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Produto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private Double preço;

	@JsonBackReference
	// Um produto tem uma lista de Categorias
	@ManyToMany
	@JoinTable(name = "PRODUTO_CATEGORIA", 
			   joinColumns = @JoinColumn(name = "produto_id"), 
			   inverseJoinColumns = @JoinColumn(name = "categoria_id")
			  )
	private List<Categoria> categorias = new ArrayList<>();
	
	@OneToMany(mappedBy="id.produto")
	private Set<ItemPedido> itens = new HashSet<>();

	public Produto() {

	}

	// Esse construtor não deve ter as listas.
	public Produto(Integer id, String nome, Double preço) {
		super();
		this.id = id;
		this.nome = nome;
		this.preço = preço;
	}
	
	public List<Pedido> getPedidos(){
		List<Pedido> lista = new ArrayList<>();
		
		for(ItemPedido x : itens) {
			lista.add(x.getPedido());
		}
		return lista;
	}	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getPreço() {
		return preço;
	}

	public void setPreço(Double preço) {
		this.preço = preço;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public Set<ItemPedido> getLista() {
		return itens;
	}

	public void setLista(Set<ItemPedido> lista) {
		this.itens = lista;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
