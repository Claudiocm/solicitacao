package com.solicitacao.sv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.solicitacao.sv.dominio.Solicitante;
import com.solicitacao.sv.dominio.Usuario;
import com.solicitacao.sv.service.SolicitanteServiceImpl;
import com.solicitacao.sv.service.UsuarioService;
import com.solicitacao.sv.service.UsuarioServiceImpl;

@RequestMapping("/solicitantes")
@Controller
public class SolicitanteController {
	@Autowired
	private SolicitanteServiceImpl service;
	@Autowired
	private UsuarioServiceImpl usuarioService;

	// abrir pagina de dados pessoais do solicitante
	@GetMapping("/lista")
	public String cadastrar(Solicitante solicitante, ModelMap model, @AuthenticationPrincipal User user) {
		solicitante = service.buscarPorUsuarioEmail(user.getUsername());
		if (solicitante.hasNotId()) {
			solicitante.setUsuario(new Usuario(user.getUsername()));
		}
		model.addAttribute("solicitante", solicitante);
		return "solicitante/cadastro";
	}

	// salvar o form de dados pessoais do solicitante com verificacao de senha
	@PostMapping("/salvar")
	public String salvar(Solicitante solicitante, ModelMap model, @AuthenticationPrincipal User user) {
		Usuario u = usuarioService.buscarPorEmail(user.getUsername());
		if (UsuarioServiceImpl.isSenhaCorreta(solicitante.getUsuario().getUsuSenha(), u.getUsuSenha())) {
			solicitante.setUsuario(u);
			service.salvar(solicitante);
			model.addAttribute("sucesso", "Seus dados foram inseridos com sucesso.");
		} else {
			model.addAttribute("falha", "Sua senha não confere, tente novamente.");
		}
		return "solicitante/cadastro";
	}

	// editar o form de dados pessoais do paciente com verificacao de senha
	@PostMapping("/editar")
	public String editar(Solicitante solicitante, ModelMap model, @AuthenticationPrincipal User user) {
		Usuario u = usuarioService.buscarPorEmail(user.getUsername());
		if (UsuarioService.isSenhaCorreta(solicitante.getUsuario().getUsuSenha(), u.getUsuSenha())) {
			service.editar(solicitante);
			model.addAttribute("sucesso", "Seus dados foram editados com sucesso.");
		} else {
			model.addAttribute("falha", "Sua senha não confere, tente novamente.");
		}
		return "solicitante/cadastro";
	}
}
