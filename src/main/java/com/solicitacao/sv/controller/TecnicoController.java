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

import com.solicitacao.sv.dominio.Cargo;
import com.solicitacao.sv.dominio.Tecnico;
import com.solicitacao.sv.service.CargoService;
import com.solicitacao.sv.service.TecnicoService;

@Controller
@RequestMapping("/tecnicos")
public class TecnicoController {
	@Autowired
	private TecnicoService servico;
	@Autowired
	private CargoService cargoService;
	
	@GetMapping("/cadastrar")
	public String cadastrar(Tecnico tecnico) {
		return "/tecnico/cadastro";
	}

	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("tecnicos", servico.buscarTodos());
		return "/tecnico/lista";
	}

	@PostMapping("/salvar")
	public String salvar(@Valid Tecnico tecnico, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			return "/tecnico/cadastro";
		}
		servico.salvar(tecnico);
		attr.addAttribute("success", "Técnico inserido com sucesso!");
		return "redirect:/tecnicos/cadastrar";
	}

	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap modelo) {
		modelo.addAttribute("tecnico", servico.buscarPorId(id));
		return "/tecnico/cadastro";
	}
	
	@PostMapping("/editar")
	public String editar(@Valid Tecnico tecnico, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			return "/tecnico/cadastro";
		}
		servico.editar(tecnico);
		attr.addFlashAttribute("success", "Técnico editado com sucesso!");
		return "redirect:/tecnicos/cadastrar";
	}
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, ModelMap modelo){
		
		servico.excluir(id);
		modelo.addAttribute("success","Técnico excluido com sucesso");
	
		return listar(modelo);
	}
	
	@GetMapping("/buscar/nome")
	public String getPorNome(@RequestParam("nome") String nome, ModelMap modelo){
		modelo.addAttribute("tecnicos",servico.buscarPorNome(nome));		
		return "/tecnico/lista";
	}
	
	@ModelAttribute("cargos")
	public List<Cargo> getCargos() {
		return cargoService.buscarTodos();
	}

}
