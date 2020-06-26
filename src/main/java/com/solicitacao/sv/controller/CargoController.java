package com.solicitacao.sv.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.solicitacao.sv.service.CargoServiceImpl;
import com.solicitacao.sv.service.TecnicoImplements;

@Controller
@RequestMapping("/cargos")
public class CargoController {
	@Autowired
	private CargoServiceImpl cargoService;
	@Autowired
	private TecnicoImplements tecnicoService;

	@GetMapping("/cadastrar")
	public String cadastrar(Cargo cargo) {
		return "/cargo/cadastro";
	}

	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("cargos", cargoService.buscarTodos());
		return "/cargo/lista";
	}

	@GetMapping("/listaPage")
	public String listaPage(Model model) {
		return findPage(1, model, "firtName", "asc");
	}
	
	@PostMapping("/salvar")
	public String salvar(@Valid Cargo cargo, BindingResult result, RedirectAttributes attr) {

		if (result.hasErrors()) {
			return "/cargo/cadastro";
		}
        try {
		    cargoService.salvar(cargo);
		    attr.addFlashAttribute("success", "Cargo inserido com sucesso.");
        }catch(DataIntegrityViolationException ex) {
        	result.reject("cargo", "Ops... Este cargo já existe na base de dados.");
			return "/cargo/cadastro";
        }
		
		return "redirect:/cargos/cadastrar";
	}

	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		Cargo cargo = cargoService.buscarPorId(id);
		model.addAttribute("cargo", cargo);
		return "/cargo/cadastro";
	}

	@PostMapping("/editar")
	public String editar(@Valid Cargo cargo, BindingResult result, RedirectAttributes attr) {

		if (result.hasErrors()) {
			return "/cargo/cadastro";
		}

		cargoService.editar(cargo);
		attr.addFlashAttribute("success", "Registro atualizado com sucesso.");
		return "redirect:/cargos/cadastrar";
	}

	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
		if (cargoService.cargoTemTecnicos(id)) {
			attr.addFlashAttribute("fail", "Cargo não excluido. Tem funcionário(s) vinculado(s).");
		} else {
			cargoService.excluir(id);
			attr.addFlashAttribute("success", "Cargo excluido com sucesso.");
		}
		return "redirect:/cargos/listar";
	}

	@GetMapping("/nome")
	public String buscarDescricao(@RequestParam("nome") String nome, ModelMap modelo) {
		modelo.addAttribute("cargos", cargoService.buscarPorNome(nome));
		return "/cargo/lista";
	}

	@ModelAttribute("departamentos")
	public List<Tecnico> listaDeTecnicos() {
		return tecnicoService.buscarTodos();
	}

	@GetMapping("/page/{page}")
	public String findPage(@PathVariable(value = "page") int page, Model modelo,
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir) {
		int pageSize = 5;
		
		Page<Cargo> pagina = cargoService.findPagenated(page, pageSize, sortField, sortDir);
		List<Cargo> cargos = pagina.getContent();
		
		modelo.addAttribute("totalPages", pagina.getTotalPages());
		modelo.addAttribute("currentPage", page);
		modelo.addAttribute("totalItems", pagina.getTotalElements());
		
		modelo.addAttribute("sortField", sortField);
		modelo.addAttribute("sortDir", sortDir);
		modelo.addAttribute("reverseSortdir", sortDir.equals("asc") ?  "desc" : "asc");
		
		modelo.addAttribute("cargos",cargos);

		return "/cargo/lista";
	}
}
