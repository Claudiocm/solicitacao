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

import com.solicitacao.sv.dominio.Chamado;
import com.solicitacao.sv.dominio.Servico;
import com.solicitacao.sv.service.ChamadoService;
import com.solicitacao.sv.service.ServicoService;

@Controller
@RequestMapping("/servicos")
public class ServicoController {
	@Autowired
	private ServicoService servico;
	@Autowired
	private ChamadoService chamado;

	@GetMapping("/cadastrar")
	public String cadastrar(Servico servico) {
		return "/servico/cadastro";
	}

	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("servicos", servico.buscarTodos());
		return "/servico/lista";
	}

	@PostMapping("/salvar")
	public String salvar(@Valid Servico ser, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			return "/servico/cadastro";
		}
		servico.salvar(ser);
		attr.addAttribute("success", "Servico inserido com sucesso!");
		return "redirect:/servicos/cadastrar";
	}

	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap modelo) {
		modelo.addAttribute("servico", servico.buscarPorId(id));
		return "/servico/cadastro";
	}

	@PostMapping("/editar")
	public String editar(@Valid Servico e, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			return "/servico/cadastro";
		}
		servico.editar(e);
		attr.addFlashAttribute("success", "Servico editado com sucesso!");
		return "redirect:/servicos/cadastrar";
	}

	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, ModelMap modelo) {

		servico.excluir(id);
		modelo.addAttribute("success", "Servico excluido com sucesso");

		return listar(modelo);
	}

	@GetMapping("/buscar/nome")
	public String getPorNome(@RequestParam("nome") String nome, ModelMap modelo) {
		modelo.addAttribute("servicos", servico.buscarPorNome(nome));
		return "/servico/lista";
	}

	@ModelAttribute("chamados")
	public List<Chamado> getChamado() {
		return chamado.buscarTodos();
	}

}
