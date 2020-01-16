package com.solicitacao.sv.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "TipoServico")
public class TipoServico extends AbstractEntity<Long> {
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "Tipo de descrição não deve ser nulo")
	@Size(min = 5, max = 80)
	@Column(name = "descricao")
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
