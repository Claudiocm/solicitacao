package com.solicitacao.sv.dominio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Setor")
public class Setor extends AbstractEntity<Long> {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "O nome do setor não pode ser em branco.")
	@Size(min = 3, max = 60, message = "O nome do Setor deve ter entre {min} e {max} caracteres.")
	@Column(name = "set_nome", nullable = false, length = 60)
	private String setNome;
	@Digits(integer = 4, fraction = 0)
	@Column(name = "set_ramal")
	private Integer setRamal;
	@OneToMany(mappedBy="setor")
	private List<Usuario> usuarios;
	@JsonIgnore
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "equipamentos_tem_chamados", joinColumns = @JoinColumn(name = "equipamento", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "chamado", referencedColumnName = "id"))
	private List<Chamado> chamados;
	
	public Setor() {
		super();
	}

	public Setor(Long id) {
		super.setId(id);
	}

	// adiciona usuarios a lista
	public void addUsuario(Usuario usuario) {
		if (this.usuarios == null) {
			this.usuarios = new ArrayList<>();
		}
		this.usuarios.add(new Usuario(usuario.getId()));
	}

	public String getSetNome() {
		return setNome;
	}

	public void setSetNome(String setNome) {
		this.setNome = setNome;
	}

	public Integer getSetRamal() {
		return setRamal;
	}

	public void setSetRamal(Integer setRamal) {
		this.setRamal = setRamal;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Chamado> getChamados() {
		return chamados;
	}

	public void setChamados(List<Chamado> chamados) {
		this.chamados = chamados;
	}

	
}
