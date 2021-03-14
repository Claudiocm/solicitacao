package com.solicitacao.sv.gerenciador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//	@Controller
//@RequestMapping("/helpdeskers")
public class HelpDesk {
	@Autowired
	private GerenciadorChamado gerente;

	public HelpDesk() {
		gerente = new GerenciadorChamado();
	}

	public GerenciadorChamado getGerenciador() {
		return gerente;
	}
}
