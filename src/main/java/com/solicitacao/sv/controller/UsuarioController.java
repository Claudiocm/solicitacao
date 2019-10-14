package com.solicitacao.sv.controller;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
import com.solicitacao.sv.dominio.Setor;
import com.solicitacao.sv.dominio.Usuario;
import com.solicitacao.sv.service.SetorService;
import com.solicitacao.sv.service.UsuarioService;

@Controller
@RequestMapping("usuarios")
public class UsuarioController {
	@Autowired
	private UsuarioService servico;
	@Autowired
	private SetorService setService;

	@GetMapping("/cadastrar")
	public String cadastrar(Usuario usuario) {
		return "/usuario/cadastro";
	}

	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("usuarios", servico.buscarTodos());
		return "/usuario/lista";
	}

	@PostMapping("/salvar")
	public String salvar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			return "/usuario/cadastro";
		}

		List<Perfil> perfis = usuario.getPerfis();
		if (perfis.size() > 2 || 
    			perfis.containsAll(Arrays.asList(new Perfil(1L), new Perfil(3L))) ||
    			perfis.containsAll(Arrays.asList(new Perfil(2L), new Perfil(3L)))) {
    		attr.addFlashAttribute("falha", "Solicitante não pode ser Admin e/ou Técnico.");
    		attr.addFlashAttribute("usuario", usuario);
    	} else {
    		try {
    			servico.salvar(usuario); 
    			attr.addFlashAttribute("sucess", "Usuário inserido com sucesso!");
    		} catch (DataIntegrityViolationException ex) {
    			attr.addFlashAttribute("falha", "Cadastro não realizado, email já existente.");
			}
    	}
		return "redirect:/usuarios/cadastrar";
	}

	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap modelo) {
		modelo.addAttribute("usuario", servico.buscarPorId(id));
		return "/usuario/cadastro";
	}

	@PostMapping("/editar")
	public String editar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			return "/usuario/cadastro";
		}
		servico.editar(usuario);
		attr.addFlashAttribute("success", "Usuário editado com sucesso!");
		return "redirect:/usuarios/cadastrar";
	}

	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, ModelMap modelo) {

		servico.excluir(id);
		modelo.addAttribute("success", "Usuário excluido com sucesso");

		return listar(modelo);
	}
	
	// abrir página de novo cadastro de solicitante
    @GetMapping("/cadastro")
    public String novoCadastro(Usuario usuario) {

        return "cadastrar-se";
    } 
    
    // pagina de resposta do cadatro de solicitante
    @GetMapping("/cadastro/realizado")
    public String cadastroRealizado() {

        return "fragments/mensagem";
    }

	@GetMapping("/buscar/nome")
	public String getPorNome(@RequestParam("nome") String nome, ModelMap modelo) {
		modelo.addAttribute("usuarios", servico.buscarPorNome(nome));
		return "/usuario/lista";
	}

	@GetMapping("/buscar/login")
	public String getPorLogin(@RequestParam("login") String login, ModelMap modelo) {
		modelo.addAttribute("usuarios", servico.buscarPorLogin(login));
		return "/usuario/lista";
	}

	// salvar cadastro de usuarios por administrador


	@ModelAttribute("setores")
	public List<Setor> getSetor() {
		return setService.buscarTodos();
	}
}
