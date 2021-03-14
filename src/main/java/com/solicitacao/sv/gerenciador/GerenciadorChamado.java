package com.solicitacao.sv.gerenciador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.solicitacao.sv.dominio.Chamado;
import com.solicitacao.sv.dominio.Solicitante;
import com.solicitacao.sv.dominio.Tecnico;

public class GerenciadorChamado implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Solicitante> solicitanteEspera = Collections.synchronizedList(new ArrayList<Solicitante>());
	private List<Tecnico> tecnicos = Collections.synchronizedList(new ArrayList<Tecnico>());
	private List<Chamado> chamados = Collections.synchronizedList(new ArrayList<Chamado>());

	public int getTecnicosDisponiveis() {
		return tecnicos.size();
	}

	public int getSolicitantesEsperando() {
		return solicitanteEspera.size();
	}

	public void solicitanteRegistrado(Solicitante solicitante) {
		if (!solicitanteEspera.contains(solicitante)) {
			solicitanteEspera.add(solicitante);
		}
	}

	public void solicitanteNaoRegistrado(Solicitante solicitante) {
		solicitanteEspera.remove(solicitante);
	}

	public void tecnicoRegistrado(Tecnico tecnico) {
		if (!tecnicos.contains(tecnico)) {
			tecnicos.add(tecnico);
		}
	}

	public void tecnicoNaoRegistrado(Tecnico tecnico) {
		tecnicos.remove(tecnico);
	}

	public Chamado getProximoChamado() {
		return chamados.remove(0);
	}

	public boolean temChamado(Chamado id) {
		return chamados.contains(id);
	}

	public Chamado getChamado(Chamado c) {
		return chamados.get(c.hashCode());
	}

	public void criaChamado(Chamado c) {
		chamados.add(new Chamado());
	}

	public int getPosicaoChamado(Chamado chamado) {
		return chamados.indexOf(chamado);
	}

	public void closeChamado(Chamado c) {
		chamados.remove(c);
	}

	public List<Solicitante> getSolicitanteEspera() {
		return solicitanteEspera;
	}

	public void setSolicitanteEspera(List<Solicitante> solicitanteEspera) {
		this.solicitanteEspera = solicitanteEspera;
	}

	public List<Tecnico> getTecnicos() {
		return tecnicos;
	}

	public void setTecnicos(List<Tecnico> tecnicos) {
		this.tecnicos = tecnicos;
	}

	public List<Chamado> getChamados() {
		return chamados;
	}

	public void setChamados(List<Chamado> chamados) {
		this.chamados = chamados;
	}

}
