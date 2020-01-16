package com.solicitacao.sv.repository.projection;

import com.solicitacao.sv.dominio.Servico;
import com.solicitacao.sv.dominio.Solicitante;
import com.solicitacao.sv.dominio.Tecnico;

public interface HistoricoTecnico {

	Long getId();
	
	Solicitante getSolicitante();
	
	String getDataAbertura();
	
	Tecnico getTecnico();
	
	Servico getServico();
}
