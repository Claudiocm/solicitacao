package com.solicitacao.sv.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.solicitacao.sv.dominio.TipoServico;
import com.solicitacao.sv.service.TipoServicoImpl;

@Controller
@RequestMapping("/tipoServicos")
public class TipoServicoController {
	@Autowired
	private TipoServicoImpl service;

	@GetMapping("/cadastrar")
	public String cadastrar(TipoServico tipoServico) {
		return "tipo-servico/cadastro";
	}

	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("tipoServicos", service.buscarTodos());
		return "/tipo-servico/lista";
	}

	@GetMapping("/datatables/server")
	public ResponseEntity<?> getTipoServicos(HttpServletRequest request) {
		return ResponseEntity.ok(service.buscarTipoServicos(request));
	}

	@PostMapping("/salvar")
	public String salvarTipos(TipoServico tipoServico, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			return "/tipo-servico/cadastro";
		}
		service.salvar(tipoServico);
		attr.addFlashAttribute("successo", "Operação realizada com sucesso!");

		return "redirect:/tipoServicos/cadastrar";
	}

	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap modelo) {
		modelo.addAttribute("tipoServico", service.buscarPorId(id));
		return "tipo-servico/cadastro";
	}

	@PostMapping("/editar")
	public String editar(TipoServico tipoServico, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			return "/tipo-servico/cadastro";
		}

		service.editar(tipoServico);
		
		attr.addFlashAttribute("success", "Tipo editado com sucesso!");
		attr.addFlashAttribute("tipoServico", tipoServico);
		return "redirect:/tipoServicos/cadastrar";
	}

	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
		service.remover(id);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso.");
		return "redirect:/tipoServicos";
	}

	@GetMapping("/nome")
	public ResponseEntity<?> getTipoServicosPorTermo(@RequestParam("termo") String termo) {
		List<String> tipos = service.buscarTipoServicoByTermo(termo);
		return ResponseEntity.ok(tipos);
	}
}
