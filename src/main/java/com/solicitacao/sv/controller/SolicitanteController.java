package com.solicitacao.sv.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.solicitacao.sv.dominio.Chamado;
import com.solicitacao.sv.dominio.Equipamento;
import com.solicitacao.sv.dominio.Setor;
import com.solicitacao.sv.dominio.Situacao;
import com.solicitacao.sv.dominio.Solicitante;
import com.solicitacao.sv.dominio.Usuario;
import com.solicitacao.sv.service.ChamadoImplements;
import com.solicitacao.sv.service.EquipamentoImplements;
import com.solicitacao.sv.service.SetorImplements;
import com.solicitacao.sv.service.SolicitanteServiceImpl;
import com.solicitacao.sv.service.UsuarioService;
import com.solicitacao.sv.service.UsuarioServiceImpl;

@RequestMapping("/solicitantes")
@Controller
public class SolicitanteController {
	@Autowired
	private SolicitanteServiceImpl service;
	@Autowired
	private ChamadoImplements chamadoService;
	@Autowired
	private EquipamentoImplements equipamentoService;
	@Autowired
	private SetorImplements setorService;
	@Autowired
	private UsuarioServiceImpl usuarioService;

	// abrir pagina de dados pessoais do solicitante
	@GetMapping("/dados")
	public String cadastrar(Solicitante solicitante, ModelMap model, @AuthenticationPrincipal User user) {
		solicitante = service.buscarPorUsuarioEmail(user.getUsername());
		if (solicitante.hasNotId()) {
			solicitante.setUsuario(new Usuario(user.getUsername()));
		}
		model.addAttribute("solicitante", solicitante);
		return "/solicitante/cadastro";
	}

	@GetMapping("/solicitar")
	public String solicitar(Chamado chamado, ModelMap model, @AuthenticationPrincipal User user ) {
		Solicitante solicitante = service.buscarPorUsuarioEmail(user.getUsername());
		if (solicitante.hasNotId()) {
			solicitante.setUsuario(new Usuario(user.getUsername()));
		}
		
		chamado.setSolicitante(solicitante);
    	chamado.setchSituacao(Situacao.ABERTO);
		chamado.setChDataAbertura(LocalDate.now());
		
		model.addAttribute("solicitante", solicitante);
		model.addAttribute("chamado",chamado);
		
		return "/solicitante/solicitacao";
	}
	
	@GetMapping("/historico/solicitante")
	public String historico(Chamado chamado, ModelMap model, @AuthenticationPrincipal User user) {

		model.addAttribute("chamado", service.buscarPorUsuarioEmail(user.getUsername()));
		return "/solicitante/historico-solicitante";
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
		return "/solicitante/cadastro";
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
		return "/solicitante/cadastro";
	}
	
	@GetMapping("/nome")
	public String buscarDescricao(@RequestParam("nome") String nome, ModelMap modelo) {
		modelo.addAttribute("solicitantes", service.buscarPorNome(nome));
		return "/solicitante/lista";
	}
	
	@ModelAttribute("setores")
	private List<Setor> getSetor() {
		return setorService.buscarTodos();
	}
	
	@ModelAttribute("chamados")
	private List<Chamado> getChamados() {
		return chamadoService.buscarTodos();
	}
	
	@ModelAttribute("equipamentos")
	private List<Equipamento> getEquipamentos() {
		return equipamentoService.buscarTodos();
	}
	
	@ModelAttribute("equipamentos")
	private List<Solicitante> getSolicitantes() {
		return service.buscarTodos();
	}
}
