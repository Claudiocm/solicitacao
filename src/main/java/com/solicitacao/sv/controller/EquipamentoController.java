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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.solicitacao.sv.dominio.Equipamento;
import com.solicitacao.sv.dominio.Servico;
import com.solicitacao.sv.dominio.TipoEquipamento;
import com.solicitacao.sv.service.EquipamentoImplements;
import com.solicitacao.sv.service.ServicoImplements;
import com.solicitacao.sv.service.TipoEquipamentoImpl;

@Controller
@RequestMapping("/equipamentos")
public class EquipamentoController {
	@Autowired
	private EquipamentoImplements equipamentoService;
	@Autowired
	private ServicoImplements servicoService;
	@Autowired
	private TipoEquipamentoImpl tipoService;

	@GetMapping("/cadastrar")
	public String cadastrar(Equipamento equipamento) {
		return "/equipamento/cadastro";
	}

	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("equipamentos", equipamentoService.buscarTodos());
		return "/equipamento/lista";
	}

	@PostMapping("/salvar")
	public String salvar(@Valid Equipamento equipamento, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			return "/equipamento/cadastro";
		}

		equipamentoService.salvar(equipamento);
		attr.addAttribute("success", "Equipamento inserido com sucesso!");
		return "redirect:/equipamentos/cadastrar";
	}

	// buscar equipamentos por servico via ajax
	@GetMapping("/servico/titulo/{titulo}")
	public ResponseEntity<?> getEquipamentosPorServico(@PathVariable("titulo") String titulo) {
		return ResponseEntity.ok(equipamentoService.buscarEquipamentosPorServico(titulo));
	}

	@PostMapping(value = "/servico/{id}")
	public String adicionaServico(@PathVariable("id") long id, Servico servico) {
		Equipamento e = equipamentoService.buscarPorId(id);
		for (Servico s : e.getServicos()) {
			s.setEquipamentos(servico.getEquipamentos());
		}

		servicoService.salvar(servico);

		return "redirect:/{id}";
	}

	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap modelo) {
		modelo.addAttribute("equipamento", equipamentoService.buscarPorId(id));
		return "/equipamento/cadastro";
	}

	@PostMapping("/editar")
	public String editar(@Valid Equipamento equipamento, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			return "/equipamento/cadastro";
		}

		equipamentoService.editar(equipamento);
		attr.addFlashAttribute("success", "Equipamento editado com sucesso!");
		return "redirect:/equipamentos/cadastrar";
	}

	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, ModelMap modelo) {
		if (equipamentoService.equipamentoTemServicos(id)) {
			modelo.addAttribute("fail", "Equipamento não removido. Possui serviços(s) vinculado(s).");
		} else if (equipamentoService.equiapamentoTemChamado(id)) {
			modelo.addAttribute("fail", "Equipamento não removido. Possui chamados(s) vinculado(s).");
		} else if (equipamentoService.equipamentoTemTecnicos(id)) {
			modelo.addAttribute("fail", "Equipamento não removido. Possui tecnicos(s) vinculado(s).");
		} else {
			equipamentoService.excluir(id);
			modelo.addAttribute("success", "Equipamento excluido com sucesso");
		}
		return listar(modelo);
	}

	@GetMapping("/datatables/server")
	public ResponseEntity<?> getEquipamentos(HttpServletRequest request) {

		return ResponseEntity.ok(equipamentoService.buscarEquipamentos(request));
	}

	@GetMapping("/buscar/descricao")
	public String getPorDescricao(@RequestParam("descricao") String descricao, ModelMap modelo) {
		modelo.addAttribute("equipamentos", equipamentoService.buscarPorDescricao(descricao));
		return "/equipamento/lista";
	}

	@GetMapping("/buscar/serie")
	public String getPorSerie(@RequestParam("eqSeriebp") String serie, ModelMap modelo) {
		modelo.addAttribute("equipamentos", equipamentoService.buscarPorSerie(serie));
		return "/equipamento/lista";
	}

	@ModelAttribute("tipos")
	public List<TipoEquipamento> getTipo() {
		return tipoService.buscarTodos();
	}

	@ModelAttribute("servicos")
	private List<Servico> getServico() {
		return servicoService.buscarTodos();
	}
}
