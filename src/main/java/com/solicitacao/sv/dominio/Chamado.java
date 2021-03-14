package com.solicitacao.sv.dominio;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.PastOrPresent;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@SuppressWarnings("serial")
@Entity
@Table(name = "chamado")
public class Chamado extends AbstractEntity<Long> {
	/**
	 * 
	 */
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

	@JoinColumn(name = "setor", referencedColumnName = "id")
	@ManyToOne
	private Setor setor;

	@JoinColumn(name = "equipamento", referencedColumnName = "id")
	@ManyToOne
	private Equipamento equipamento;

	@Valid
	@JoinColumn(name = "tecnico", referencedColumnName = "id")
	@ManyToOne
	private Tecnico tecnico;
	@Valid
	@ManyToOne
	@JoinColumn(name = "servico", referencedColumnName = "id")
	private Servico servico;

	@ManyToOne
	@JoinColumn(name = "solicitante", referencedColumnName = "id")
	private Solicitante solicitante;

	public LocalDate getChDataAbertura() {
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

	public Tecnico getTecnico() {
		return tecnico;
	}

	public void setTecnico(Tecnico tecnico) {
		this.tecnico = tecnico;
	}

	public void setChSituacao(Situacao chSituacao) {
		this.chSituacao = chSituacao;
	}

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	public Solicitante getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(Solicitante solicitante) {
		this.solicitante = solicitante;
	}
	
	public Long getTempo() {
		DateTimeFormatter entrada = DateTimeFormatter.ofPattern("hh:mm");
		
		Instant hoje = Instant.now();
		
		Instant inicio = Instant.parse(entrada.format(chDataAbertura));
		
		Duration tempo = Duration.between(inicio, hoje);
	
		return tempo.toHours();
	}

}
