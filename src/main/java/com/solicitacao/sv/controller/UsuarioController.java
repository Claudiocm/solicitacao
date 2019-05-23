package com.solicitacao.sv.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.solicitacao.sv.dominio.Setor;
import com.solicitacao.sv.dominio.Usuario;
import com.solicitacao.sv.service.SetorService;
import com.solicitacao.sv.service.UsuarioService;

@Controller
@RequestMapping("/usuarios")
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
		if(result.hasErrors()){
			return "/usuario/cadastro";
		}
		servico.salvar(usuario);
		attr.addAttribute("success", "Usuário inserido com sucesso!");
		return "redirect:/usuarios/cadastrar";
	}

	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap modelo) {
		modelo.addAttribute("usuario", servico.buscarPorId(id));
		return "/usuario/cadastro";
	}
	
	@PostMapping("/editar")
	public String editar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attr) {
		if(result.hasErrors()){
			return "/usuario/cadastro";
		}
		servico.editar(usuario);
		attr.addFlashAttribute("success", "Usuário editado com sucesso!");
		return "redirect:/usuarios/cadastrar";
	}
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, ModelMap modelo){
		
		servico.excluir(id);
		modelo.addAttribute("success","Usuário excluido com sucesso");
	
		return listar(modelo);
	}
	
	@GetMapping("/buscar/nome")
	public String getPorNome(@RequestParam("nome") String nome, ModelMap modelo){
		modelo.addAttribute("usuarios",servico.buscarPorNome(nome));		
		return "/usuario/lista";
	}

	@GetMapping("/buscar/login")
	public String getPorLogin(@RequestParam("login") String login, ModelMap modelo){
		modelo.addAttribute("usuarios",servico.buscarPorLogin(login));		
		return "/usuario/lista";
	}
	
	@ModelAttribute("setores")
	public List<Setor> getSetor() {
		return setService.buscarTodos();
	}
}
