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

import com.solicitacao.sv.dominio.TipoEquipamento;
import com.solicitacao.sv.service.EquipamentoImplements;
import com.solicitacao.sv.service.TipoEquipamentoImpl;

@Controller
@RequestMapping("/tipoEquipamentos")
public class TipoEquipamentoController {
	@Autowired
	private EquipamentoImplements equipamentoService;
	@Autowired
	private TipoEquipamentoImpl service;

	@GetMapping("/cadastrar")
	public String abrir(TipoEquipamento equipamento) {
		return "tipo-equipamento/cadastro";
	}

	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("tipoEquipamentos", service.buscarTodos());
		return "/tipo-equipamento/lista";
	}

	@GetMapping("/datatables/server")
	public ResponseEntity<?> getTipoEquipamentos(HttpServletRequest request) {
		return ResponseEntity.ok(service.buscarTipoEquipamentos(request));
	}

	@PostMapping({ "/salvar" })
	public String salvar(@Valid TipoEquipamento equipamento, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			return "/tipo-equipamento/cadastro";
		}
		service.salvar(equipamento);
		attr.addFlashAttribute("successo", "Operação realizada com sucesso!");

		return "redirect:/tipoEquipamentos/cadastrar";
	}

	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap modelo) {
		modelo.addAttribute("tipoEquipamento", service.buscarPorId(id));
		return "tipo-equipamento/cadastro";
	}

	@PostMapping("/editar")
	public String editar(@Valid TipoEquipamento equipamento, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			return "/tipo-equipamento/cadastro";
		}

		service.editar(equipamento);
		attr.addFlashAttribute("success", "Tipo editado com sucesso!");
		return "redirect:/tipoEquipamentos/cadastrar";
	}

	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
		service.remover(id);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso.");
		return "redirect:/tipoEquipamentos";
	}

	@GetMapping("/nome")
	public String tipoEquipamentoPorNome(@RequestParam("nome") String nome, ModelMap modelo) {
		modelo.addAttribute("tipoEquipamentos",service.buscarTipoEquipamentoByNome(nome));
		
		return "/tipo-equipamento/lista";
	}

	/*
	 * @GetMapping("/datatables/server/equipamento/{id}") public ResponseEntity<?>
	 * getEquipamentoPorTipo(@PathVariable("id") Long id, HttpServletRequest
	 * request) { return ResponseEntity.ok(service.buscarEquipamentoPorTipo(id,
	 * request)); }
	 */

}
