package com.solicitacao.sv.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.solicitacao.sv.dominio.Perfil;
import com.solicitacao.sv.dominio.PerfilTipo;
import com.solicitacao.sv.dominio.Setor;
import com.solicitacao.sv.dominio.Tecnico;
import com.solicitacao.sv.dominio.Usuario;
import com.solicitacao.sv.repository.UsuarioRepository;
import com.solicitacao.sv.service.SetorImplements;
import com.solicitacao.sv.service.TecnicoImplements;
import com.solicitacao.sv.service.UsuarioServiceImpl;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
	@Autowired
	private UsuarioRepository repositorio;

	@Autowired
	private UsuarioServiceImpl servico;
	@Autowired
	private TecnicoImplements tecnicoService;
	@Autowired
	private SetorImplements setorService;

	@GetMapping("/cadastrar")
	public String cadastrar(Usuario usuario) {
		return "/usuario/cadastro";
	}

	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("usuarios", servico.buscarTodos());
		return "/usuario/lista";
	}

	// metodo produzir relatorio
	@GetMapping(value = "/{id}/", produces = "application/pdf")
	public ResponseEntity<Usuario> relatorio(@PathVariable(value = "id") Long id) {
		Optional<Usuario> usuario = repositorio.findById(id);
		return new ResponseEntity<Usuario>(usuario.get(), HttpStatus.OK);
	}

	@PostMapping("/salvar")
	public String salvar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			return "/usuario/cadastro";
		}
		try {
			usuario.setCriadoPor(usuario.getEmail());
			usuario.setDataCriada(new Date(System.currentTimeMillis()));

			servico.salvar(usuario);
			attr.addFlashAttribute("sucess", "Usuário inserido com sucesso!");
			attr.addAttribute("usuario", usuario);
		} catch (DataIntegrityViolationException ex) {
			attr.addFlashAttribute("falha", "Cadastro não realizado, email já existente.");
		}

		return "redirect:/usuarios/cadastrar";
	}

	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, Usuario usuario, ModelMap modelo) {
		modelo.addAttribute("usuario", servico.buscarPorId(id));

		return "/usuario/cadastro";
	}

	@PostMapping("/editar")
	public String editar(@AuthenticationPrincipal Usuario usuario, ModelMap modelo) {

		// usuario.setModificadoPor(user.getUsername());
		// usuario.setUltimaDataModificada(new Date(System.currentTimeMillis()));
		Usuario us = servico.buscarPorId(usuario.getId());

		if (us.getPerfis().contains(new Perfil(PerfilTipo.ADMIN.getCod()))
				&& us.getPerfis().contains(new Perfil(PerfilTipo.TECNICO.getCod()))) {
			
			servico.editar(us);
			modelo.addAttribute("success", "Usuário cadastrado com sucesso!");
			modelo.addAttribute("usuario", us);

			return "usuario/cadastro";
		} else if (us.getPerfis().contains(new Perfil(PerfilTipo.TECNICO.getCod()))) {

			Tecnico tecnico = tecnicoService.buscarPorUsuarioId(us.getId());
			if (tecnico.hasId()) {
				servico.editar(tecnico.getUsuario());
				modelo.addAttribute("success", "Técnico cadastrado com sucesso!");
				modelo.addAttribute("tecnico", tecnico);

				return "tecnico/cadastro";
			}else {
				modelo.addAttribute("fail", "Não foi possível adicionar o usuário.");
			}

		} else if (us.getPerfis().contains(new Perfil(PerfilTipo.SOLICITANTE.getCod()))) {
			modelo = new ModelMap("error");
			modelo.addAttribute("status", 403);
			modelo.addAttribute("error", "Área restrita");
			modelo.addAttribute("message", "Os dados de pacientes são restritos a ele.");

		}

		return "/usuario/cadastro";
	}

	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, ModelMap modelo) {

		servico.excluir(id);
		modelo.addAttribute("success", "Usuário excluido com sucesso");

		return listar(modelo);
	}

	@GetMapping("/email")
	public String getPorEmail(@RequestParam("email") String email, ModelMap modelo) {
		modelo.addAttribute("usuarios", servico.buscaPorEmail(email));
		return "/usuario/lista";
	}

	@GetMapping("/editar/senha")
	public String abrirEditarSenha() {
		return "/usuario/editar-senha";
	}

	
	@PostMapping("/confirmar/senha")
	public String editarSenha(@RequestParam("senha1") String senha1, @RequestParam("senha2") String senha2,
			@RequestParam("senha3") String senha3, @AuthenticationPrincipal User user, RedirectAttributes attr) {

		if (!senha1.equals(senha2)) {
			attr.addFlashAttribute("falha", "Senhas não conferem, tente novamente");
			return "redirect:/usuarios/editar/senha";
		}

		Usuario u = servico.buscarPorEmail(user.getUsername());
		if (!UsuarioServiceImpl.isSenhaCorreta(senha3, u.getUsuSenha())) {
			attr.addFlashAttribute("falha", "Senha atual não confere, tente novamente");
			return "redirect:/usuarios/editar/senha";
		}

		servico.alterarSenha(u, senha1);
		attr.addFlashAttribute("sucesso", "Senha alterada com sucesso.");
		//attr.addAttribute("usuario", u);
		return "redirect:/usuarios/editar/senha";
	}

	// abrir página de novo cadastro de solicitante
	@GetMapping("/cadastro")
	public String novoCadastro(Usuario usuario) {
		return "cadastrar-se";
	}

	// pagina de resposta do cadatro de solicitante
	@GetMapping("/cadastro/realizado")
	public String cadastroRealizado() {

		return "/fragments/mensagem";
	}

	// rebece o form da página cadastrar-se
	@PostMapping("/cadastro/solicitante/salvar")
	public String salvarCadastroSolicitante(Usuario usuario, BindingResult result) throws MessagingException {
		try {
			servico.salvarCadastroSolicitante(usuario);
		} catch (DataIntegrityViolationException ex) {
			result.reject("email", "Ops... Este e-mail já existe na base de dados.");
			return "cadastrar-se";
		}
		return "redirect:/usuarios/cadastro/realizado";
	}

	// rebece o form da página cadastrar-se
	@PostMapping("/cadastro/tecnico/salvar")
	public String salvarCadastroTecnico(Usuario usuario, BindingResult result) throws MessagingException {
		try {
			servico.salvarCadastroTecnico(usuario);
		} catch (DataIntegrityViolationException ex) {
			result.reject("email", "Ops... Este e-mail já existe na base de dados.");
			return "cadastrar-se";
		}
		return "redirect:/usuarios/cadastro/realizado";
	}

	// recebe a requisicao de confirmacao de cadastro

	@GetMapping("/confirmacao/cadastro/solicitante")
	public String respostaConfirmacaoCadastroSolicitante(@RequestParam("codigo") String codigo,
			RedirectAttributes attr) {
		servico.ativarCadastroSolicitante(codigo);
		attr.addFlashAttribute("alerta", "sucesso");
		attr.addFlashAttribute("titulo", "Cadastro Ativado!");
		attr.addFlashAttribute("texto", "Parabéns, seu cadastro está ativo.");
		attr.addFlashAttribute("subtexto", "Siga com seu login/senha");
		return "redirect:/login";
	}

	// recebe a requisicao de confirmacao de cadastro
	@GetMapping("/confirmacao/cadastro")
	public String respostaConfirmacaoCadastroTecnico(@RequestParam("codigo") String codigo, RedirectAttributes attr) {
		servico.ativarCadastroTecnico(codigo);
		attr.addFlashAttribute("alerta", "sucesso");
		attr.addFlashAttribute("titulo", "Cadastro Ativado!");
		attr.addFlashAttribute("texto", "Parabéns, seu cadastro está ativo.");
		attr.addFlashAttribute("subtexto", "Siga com seu login/senha");
		return "redirect:/login";
	}

	// abre a pagina de pedido de redefinicao de senha
	@GetMapping("/p/redefinir/senha")
	public String pedidoRedefinirSenha() {
		return "usuario/pedido-recuperar-senha";
	}

	// form de pedido de recuperar senha
	@GetMapping("/p/recuperar/senha")
	public String redefinirSenha(String email, ModelMap model) throws MessagingException {
		servico.pedidoRedefinicaoDeSenha(email);
		model.addAttribute("sucesso",
				"Em instantes você reberá um e-mail para " + "prosseguir com a redefinição de sua senha.");
		model.addAttribute("usuario", new Usuario(email));
		return "usuario/recuperar-senha";
	}

	// salvar a nova senha via recuperacao de senha
	@PostMapping("/p/nova/senha")
	public String confirmacaoDeRedefinicaoDeSenha(Usuario usuario, ModelMap model) {
		Usuario u = servico.buscarPorEmail(usuario.getEmail());
		if (!usuario.getCodigoVerificador().equals(u.getCodigoVerificador())) {
			model.addAttribute("falha", "Código verificador não confere.");
			return "usuario/recuperar-senha";
		}
		u.setCodigoVerificador(null);
		servico.alterarSenha(u, usuario.getUsuSenha());
		model.addAttribute("alerta", "sucesso");
		model.addAttribute("titulo", "Senha redefinida!");
		model.addAttribute("texto", "Você já pode logar no sistema.");
		return "login";
	}

	// salvar cadastro de usuarios por administrador
	@ModelAttribute("setores")
	public List<Setor> getSetor() {
		return setorService.buscarTodos();
	}

	@ModelAttribute("perfis")
	public PerfilTipo[] getPerfis() {
		return PerfilTipo.values();
	}
}
