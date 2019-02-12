package com.solicitacao.sv.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Servico")
public class Servico extends AbstractEntity<Long> {
	private static final long serialVersionUID = 1L;
	@NotBlank(message = "O nome do serviço é obrigatório.")
	@Size(min = 3, max = 60, message = "O nome do serviço deve ter entre {min} e {max} caracteres.")
	@Column(name = "ser_nome", nullable = false, unique = true, length = 60)
	private String serNome;
	@JoinColumn(name = "id_chamado_fk", referencedColumnName = "id")
	@ManyToOne
	private Chamado chamado;
	
	public Servico() {
	}

	public String getSerNome() {
		return serNome;
	}

	public void setSerNome(String serNome) {
		this.serNome = serNome;
	}

	public Chamado getChamado() {
		return chamado;
	}

	public void setChamado(Chamado chamado) {
		this.chamado = chamado;
	}

	
}
