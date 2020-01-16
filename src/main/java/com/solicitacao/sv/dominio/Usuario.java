package com.solicitacao.sv.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "Usuario", indexes = { @Index(name = "idx_usuario_email", columnList = "email") })
@EntityListeners(AuditingEntityListener.class)
public class Usuario extends Auditable<String> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "email", unique = true, nullable = false)
	private String email;
	@NotBlank(message = "A senha do usuário não pode ser em branco.")
	@Size(min = 6, message = "A senha deve ter no mínimo {min} caracteres.")
	@Column(name = "usu_senha")
	private String usuSenha;
	@JoinColumn(name = "setor")
	@ManyToOne
	private Setor setor;
	@ManyToMany
	@JoinTable(name = "usuarios_tem_perfis", joinColumns = {
			@JoinColumn(name = "usuario", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "perfil", referencedColumnName = "id") })
	private List<Perfil> perfis;
	@Column(name = "ativo", nullable = false, columnDefinition = "TINYINT(1)")
	private boolean ativo;

	@Column(name = "codigo_verificador", length = 6)
	private String codigoVerificador;

	public Usuario() {
		super();
	}

	public Usuario(Long id) {
		setId(id);
	}

	// adiciona perfis a lista
	public void addPerfil(PerfilTipo tipo) {
		if (this.perfis == null) {
			this.perfis = new ArrayList<>();
		}
		this.perfis.add(new Perfil(tipo.getCod()));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario(String email) {
		this.email = email;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public String getCodigoVerificador() {
		return codigoVerificador;
	}

	public void setCodigoVerificador(String codigoVerificador) {
		this.codigoVerificador = codigoVerificador;
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

	public List<Perfil> getPerfis() {
		return perfis;
	}

	public void setPerfis(List<Perfil> perfis) {
		this.perfis = perfis;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean hasNotId() {
		return id == null;
	}

	public boolean hasId() {
		return id != null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractEntity<?> other = (AbstractEntity<?>) obj;
		if (id == null) {
			if (other.getId() != null)
				return false;
		} else if (!id.equals(other.getId()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "id = " + id;
	}
}
