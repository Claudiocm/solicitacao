package com.solicitacao.sv.dominio;

public enum Prioridade {
   ALTA("Alta"),
   MEDIA("Média"),
   BAIXA("Baixa"),
   URGENCIA("Urgência");
	
	private String descricao;

	Prioridade(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
