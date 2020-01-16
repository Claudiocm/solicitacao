package com.solicitacao.sv.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@SuppressWarnings("serial")
@Entity
@Table(name = "TipoEquipamento")
public class TipoEquipamento extends AbstractEntity<Long> {

	@NotEmpty(message = "A descrição é obrigatorio")
	@Column(name = "descricao")
	private String descricao;
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
