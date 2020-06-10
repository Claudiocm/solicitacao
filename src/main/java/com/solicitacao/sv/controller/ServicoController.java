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

import com.solicitacao.sv.dominio.Chamado;
import com.solicitacao.sv.dominio.Servico;
import com.solicitacao.sv.dominio.TipoServico;
import com.solicitacao.sv.service.ChamadoImplements;
import com.solicitacao.sv.service.ServicoImplements;
import com.solicitacao.sv.service.TipoServicoImpl;

@Controller
@RequestMapping("/servicos")
public class ServicoController {
	@Autowired
	private ServicoImplements servico;
	@Autowired
	private TipoServicoImpl tipoServicoService;
	@Autowired
	private ChamadoImplements chamado;

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

	@GetMapping("/nome")
	public String getPorNome(@RequestParam("nome") String nome, ModelMap modelo) {
		modelo.addAttribute("servicos", servico.buscarPorNome(nome));
		return "/servico/lista";
	}

	@ModelAttribute("chamados")
	public List<Chamado> getChamado() {
		return chamado.buscarTodos();
	}

	@ModelAttribute("tipos")
	public List<TipoServico> getTipo() {
		return tipoServicoService.buscarTodos();
	}

	@GetMapping("/datatables/server")
	public ResponseEntity<?> getServicosPorNome(@RequestParam("termo") String nome) {
		return ResponseEntity.ok(servico.buscarPorNome(nome));
	}

	@GetMapping("/datatables/server/equipamento/{id}")
	public ResponseEntity<?> getServicosPorTecnico(@PathVariable("id") Long id, HttpServletRequest request) {
		return ResponseEntity.ok(servico.buscarServicosPorTecnico(id, request));
	}

}
