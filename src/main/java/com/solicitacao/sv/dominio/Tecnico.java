package com.solicitacao.sv.dominio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Tecnico")
public class Tecnico extends AbstractEntity<Long> {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "O nome do técnico é obrigatório.")
	@Size(min = 3, max = 60, message = "O nome do técnico deve ter entre {min} e {max} caracteres.")
	@Column(name = "tec_nome", nullable = false, unique = true, length = 60)
	private String tecNome;
	@JoinColumn(name = "cargo", referencedColumnName = "id")
	@ManyToOne
	private Cargo cargo;
	@OneToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "usuario")
	private Usuario usuario;
	@OneToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "setor")
	private Setor setor;
	@JsonIgnore
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "tecnicos_tem_equipamentos", joinColumns = @JoinColumn(name = "tecnico", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "equipamento", referencedColumnName = "id"))
	private Set<Equipamento> equipamentos;
	@JsonIgnore
	@OneToMany(mappedBy = "tecnico")
	private List<Chamado> chamados;

	public Tecnico() {
		super();
	}

	public Tecnico(Long id) {
		super.setId(id);
	}

	public Tecnico(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getTecNome() {
		return tecNome;
	}

	public void setTecNome(String tecNome) {
		this.tecNome = tecNome;
	}

	public Set<Equipamento> getEquipamentos() {
		return equipamentos;
	}

	public void setEquipamentos(Set<Equipamento> equipamentos) {
		this.equipamentos = equipamentos;
	}

	public List<Chamado> getChamados() {
		return chamados;
	}

	public void setChamados(List<Chamado> chamados) {
		this.chamados = chamados;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}
	
	

}
