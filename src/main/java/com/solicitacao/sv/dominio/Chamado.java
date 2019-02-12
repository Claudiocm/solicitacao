package com.solicitacao.sv.dominio;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table(name = "chamado")
public class Chamado extends AbstractEntity<Long>{
	private static final long serialVersionUID = 1L;
	@NotNull
	@PastOrPresent(message = "{PastOrPresent.chamado.chDataAbertura}")
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "data_abertura", nullable = false, columnDefinition = "DATE")
	private LocalDate chDataAbertura;
	
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "data_fechamento", columnDefinition = "DATE")
	private LocalDate chDataFechamento;

	@Column(name = "ch_problema")
	private String chProblema;
	
	@Column(name = "ch_observacao")
	private String chObservacao;
    
	@Column(name = "ch_ip")
	private String chIp;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Situacao chSituacao;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Prioridade chPrioridade;
	
	@JoinColumn(name="id_setor_fk", referencedColumnName = "id")
	@ManyToOne
	private Setor setor;
	@Valid
	@JoinColumn(name = "id_equipamento_fk", referencedColumnName = "id")
	@ManyToOne
	private Equipamento equipamento;
	
	@OneToMany(mappedBy = "chamado")
	private List<Servico> servicos;
	@Valid
	@JoinColumn(name = "id_tecnico_fk", referencedColumnName = "id")
	@ManyToOne
	private Tecnico tecnico;

	public LocalDate getSlDataAbertura() {
		return chDataAbertura;
	}

	public void setChDataAbertura(LocalDate chDataAbertura) {
		this.chDataAbertura = chDataAbertura;
	}

	public LocalDate getChDataFechamento() {
		return chDataFechamento;
	}

	public void setChDataFechamento(LocalDate chDataFechamento) {
		this.chDataFechamento = chDataFechamento;
	}

	public String getChProblema() {
		return chProblema;
	}

	public void setChProblema(String chProblema) {
		this.chProblema = chProblema;
	}

	public String getChObservacao() {
		return chObservacao;
	}

	public void setChObservacao(String chObservacao) {
		this.chObservacao = chObservacao;
	}

	public String getChIp() {
		return chIp;
	}

	public void setChIp(String chIp) {
		this.chIp = chIp;
	}

	public Situacao getChSituacao() {
		return chSituacao;
	}

	public void setchSituacao(Situacao chSituacao) {
		this.chSituacao = chSituacao;
	}

	public Prioridade getChPrioridade() {
		return chPrioridade;
	}

	public void setChPrioridade(Prioridade chPrioridade) {
		this.chPrioridade = chPrioridade;
	}

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

	public Equipamento getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}

	public List<Servico> getServicos() {
		return servicos;
	}

	public void setServicos(List<Servico> servicos) {
		this.servicos = servicos;
	}

	public Tecnico getTecnico() {
		return tecnico;
	}

	public void setTecnico(Tecnico tecnico) {
		this.tecnico = tecnico;
	}

	public LocalDate getChDataAbertura() {
		return chDataAbertura;
	}

	public void setChSituacao(Situacao chSituacao) {
		this.chSituacao = chSituacao;
	}

	
}
