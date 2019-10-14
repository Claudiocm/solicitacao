package com.solicitacao.sv.dominio;

public enum PerfilTipo {
	ADMIN(1, "ADMIN"), TECNICO(2, "TECNICO"), USUARIO(3, "USUARIO");

	private long cod;
	private String desc;

	private PerfilTipo(long cod, String desc) {
		this.cod = cod;
		this.desc = desc;
	}

	public long getCod() {
		return cod;
	}

	public String getDesc() {
		return desc;
	}
}
