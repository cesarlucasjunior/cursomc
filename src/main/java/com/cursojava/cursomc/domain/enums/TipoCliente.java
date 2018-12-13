package com.cursojava.cursomc.domain.enums;

public enum TipoCliente {

	PESSOAFISICA(1, "Pessoa Física"), 
	PESSOAJURIDICA(2, "Pessoa Jurídica");

	private Integer id;
	private String descricao;

	private TipoCliente(Integer id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public Integer getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}
	
	//Criar um método que a partir do id retorna o TipoCliente
	
	public static TipoCliente toEnum(Integer cod) {
		if(cod==null) {
			return null;
		}
		
		for(TipoCliente x : TipoCliente.values()) {
			if(cod.equals(x.getId())){
//			if(cod == x.getId()) {
				return x;
			}
		}
			
			throw new IllegalArgumentException("Id inválido" + cod);
		}

}
