package com.solicitacao.sv.dominio;

public enum Situacao {
	ABERTO("Aberto"), AGUARDANDO_PECA("Aguardando Peça"), ANDAMENTO("Em andamento"), ENTREGUE("Entregue"), FECHADO(
			"Fechado"), SUCATA("Sucata");

	private String descricao;

	Situacao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
