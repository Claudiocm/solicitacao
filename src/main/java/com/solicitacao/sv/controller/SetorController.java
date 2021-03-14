package com.solicitacao.sv.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.solicitacao.sv.dominio.Setor;
import com.solicitacao.sv.dominio.Solicitante;
import com.solicitacao.sv.dominio.Usuario;
import com.solicitacao.sv.service.SetorImplements;
import com.solicitacao.sv.service.UsuarioServiceImpl;

@Controller
@RequestMapping("/setores")
public class SetorController {
	@Autowired
	private SetorImplements servico;
	@Autowired
	private UsuarioServiceImpl usuarioService;

	@GetMapping("/cadastrar")
	public String cadastrar(Setor setor) {
		return "/setor/cadastro";
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("setores", servico.buscarTodos());
		return "/setor/lista";
	}

	@PostMapping("/salvar")
	public String salvar(@Valid Setor setor, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			return "/setor/cadastro";
		}
		servico.salvar(setor);
		attr.addAttribute("success", "Setor inserido com sucesso!");
        attr.addAttribute("setor",setor);
		return "redirect:/setores/cadastrar";
	}

	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap modelo) {
		modelo.addAttribute("setor", servico.buscarPorId(id));
		return "/setor/cadastro";
	}

	@PostMapping("/editar")
	public String editar(@Valid Setor setor, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			return "/setor/cadastro";
		}
		servico.editar(setor);
		attr.addFlashAttribute("success", "Setor editado com sucesso!");
		attr.addAttribute("setor",setor);
		return "redirect:/setores/cadastrar";
	}

	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, ModelMap modelo) {
		if (servico.setorTemUsuarios(id)) {
			modelo.addAttribute("fail", "Setor não removido. Possui usuario(s) vinculado(s).");
		} else {
			servico.excluir(id);
			modelo.addAttribute("success", "Setor excluido com sucesso");
		}
		return listar(modelo);
	}

	@GetMapping("/nome")
	public String getPorNome(@RequestParam("nome") String nome, ModelMap modelo) {
		modelo.addAttribute("setores", servico.buscarPorNome(nome));
		return "/setor/lista";
	}

	@ModelAttribute("usuarios")
	public List<Usuario> getUsuario() {
		return usuarioService.buscarTodos();
	}
}
