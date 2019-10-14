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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.solicitacao.sv.dominio.Equipamento;
import com.solicitacao.sv.dominio.Servico;
import com.solicitacao.sv.dominio.TipoEquipamento;
import com.solicitacao.sv.service.EquipamentoService;
import com.solicitacao.sv.service.ServicoService;

@Controller
@RequestMapping("/equipamentos")
public class EquipamentoController {
	@Autowired
	private EquipamentoService equipamentoService;
	@Autowired
	private ServicoService servicoService;

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

	@GetMapping("/listar/{id}/equipamentos")
	public String buscaPorEquipamento(@PathVariable Long idEquip, ModelMap modelo, ModelAndView mv) {
		Iterable <Servico> servicos = servicoService.buscarPorIdEquipamento(idEquip);
		mv.addObject("servicos", servicos);
		return "equipamento/lista";
	}

	@GetMapping("/listar/{id}/equipamentos/servicos/{id}s")
	public String servicoPorEquipamento(@PathVariable Long idEquip, @PathVariable Long id, ModelMap modelo) {
		modelo.addAttribute("equipamento", equipamentoService.buscarServicoPorEquipamento(idEquip, id));
		return "equipamento/lista";
	}

	@PostMapping(value = "/servico/{id}")
	public String adicionaServico(@PathVariable("id") long id, Servico servico) {
		Equipamento e = equipamentoService.buscarPorId(id);
		servico.setEquipamento(e);
		equipamentoService.salvar(servico.getEquipamento());
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

		equipamentoService.excluir(id);
		modelo.addAttribute("success", "Equipamento excluido com sucesso");

		return listar(modelo);
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
	public TipoEquipamento[] getTipos() {
		return TipoEquipamento.values();
	}

	@ModelAttribute("servicos")
	private List<Servico> getServico() {
		return servicoService.buscarTodos();
	}
}
