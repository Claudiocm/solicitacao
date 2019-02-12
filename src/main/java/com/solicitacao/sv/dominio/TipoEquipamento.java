package com.solicitacao.sv.dominio;

public enum TipoEquipamento {
	COMPUTADOR_DESKTOP("Computador Desktop"), NOTEBOOK("Notebook"), IMPRESSORA("Impressora"), NOBREAK(
			"Nobreak"), ESTABILIZADOR(
					"Estabilizador"), MONITOR("Monitor"), PERIFÉRICO("Periférico"), CABOS("Cabos"), SWITCH("Switch");

	private String descricao;

	TipoEquipamento(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
