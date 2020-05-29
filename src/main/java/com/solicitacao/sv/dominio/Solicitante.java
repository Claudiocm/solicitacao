package com.solicitacao.sv.dominio;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("serial")
@Entity
@Table(name = "solicitante")
public class Solicitante extends AbstractEntity<Long> {

	@Column(name = "nome", unique = true, nullable = false)
	private String nome;

	@Column(name = "dtCadastro", nullable = false)
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dtCadastro;

	@JsonIgnore
	@OneToMany(mappedBy = "solicitante")
	private List<Chamado> chamados;
	
	@OneToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "usuario")
	private Usuario usuario;

	@OneToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "setor")
	private Setor setor;
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getDtCadastro() {
		return dtCadastro;
	}

	public void setDtCadastro(LocalDate dtCadastro) {
		this.dtCadastro = dtCadastro;
	}

	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Chamado> getChamados() {
		return chamados;
	}

	public void setChamados(List<Chamado> chamados) {
		this.chamados = chamados;
	}

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

}