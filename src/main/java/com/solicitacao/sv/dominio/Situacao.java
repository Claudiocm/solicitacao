package com.solicitacao.sv.dominio;

public enum Situacao {
	ABERTO("ABERTO"), AGUARDANDO_PECA("AGUARDANDO PEÇA"), ANDAMENTO("EM ANDAMENTO"), ENTREGUE("ENTREGUE"), FECHADO(
			"FECHADO"), SUCATA("SUCATA");

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
