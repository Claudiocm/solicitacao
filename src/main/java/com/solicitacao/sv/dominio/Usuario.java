package com.solicitacao.sv.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Usuario")
public class Usuario extends AbstractEntity<Long>{

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "O nome do usuário é obrigatório.")
	@Size(min = 3, max = 60, message = "O nome do usuário deve ter entre {min} e {max} caracteres.")
	@Column(name = "usu_nome", nullable = false, unique = true, length = 60)
	private String usuNome;
	@NotBlank(message = "O login do usuário é obrigatório.")
	@Size(max = 50, min = 3, message = "O login deve ter entre {min} e {max} caracteres.")
	@Column(name = "usu_login",  nullable = false, unique = true, length = 60)
	private String usuLogin;
	@NotBlank(message = "A senha do usuário não pode ser em branco.")
	@Size(min = 6, message = "A senha deve ter no mínimo {min} caracteres.")
	@Column(name = "usu_senha")
	private String usuSenha;
	@JoinColumn(name="id_setor_fk")
	@ManyToOne
	private Setor setor;

	public String getUsuNome() {
		return usuNome;
	}

	public void setUsuNome(String usuNome) {
		this.usuNome = usuNome;
	}

	public String getUsuLogin() {
		return usuLogin;
	}

	public void setUsuLogin(String usuLogin) {
		this.usuLogin = usuLogin;
	}

	public String getUsuSenha() {
		return usuSenha;
	}

	public void setUsuSenha(String usuSenha) {
		this.usuSenha = usuSenha;
	}

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}
	
	

}
