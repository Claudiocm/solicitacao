package com.solicitacao.sv.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.solicitacao.sv.dominio.Cargo;
import com.solicitacao.sv.dominio.Chamado;
import com.solicitacao.sv.dominio.Equipamento;
import com.solicitacao.sv.dominio.Prioridade;
import com.solicitacao.sv.dominio.Servico;
import com.solicitacao.sv.dominio.Setor;
import com.solicitacao.sv.dominio.Situacao;
import com.solicitacao.sv.dominio.Tecnico;
import com.solicitacao.sv.dominio.TipoEquipamento;
import com.solicitacao.sv.service.CargoService;
import com.solicitacao.sv.service.ChamadoService;
import com.solicitacao.sv.service.EquipamentoService;
import com.solicitacao.sv.service.ServicoService;
import com.solicitacao.sv.service.SetorService;
import com.solicitacao.sv.service.TecnicoService;
import com.solicitacao.sv.validator.ChamadoValidator;

@Controller
@RequestMapping("/chamados")
public class ChamadoController {
	@Autowired
	private ChamadoService chService;
	@Autowired
	private EquipamentoService eqpService;
	@Autowired
	private ServicoService serService;
	@Autowired
	private TecnicoService tecService;
	@Autowired
	private SetorService setService;
	@Autowired
	private CargoService cargoService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new ChamadoValidator());
	}

	@GetMapping("/cadastrar")
	public String cadastrar(Chamado chamado) {
		return "/chamado/cadastro";
	}

	@GetMapping("/listar")
	public String listar(ModelMap model) {
		List<Chamado> c = chService.buscarTodos();
		for (Chamado ch : c) {
			ch.setEquipamento(ch.getEquipamento());
			model.addAttribute("chamados", chService.buscarTodos());
		}
		return "/chamado/lista";
	}

	@GetMapping("/buscarLista")
	public String buscaLista(ModelMap model) {
		model.addAttribute("chamados", chService.buscarLista());
		return "/chamado/lista";
	}

	@GetMapping("/buscar")
	public String busca(ModelMap model) {
		model.addAttribute("chamados", chService.buscar());
		return "/chamado/painel";
	}

	@PostMapping("/salvar")
	public String salvar(@Valid Chamado Chamado, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			return "/chamado/cadastro";
		}
		chService.salvar(Chamado);
		attr.addAttribute("success", "Chamado inserido com sucesso!");
		return "redirect:/chamados/cadastrar";
	}

	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap modelo) {
		Chamado chamado = chService.buscarPorId(id);
		modelo.addAttribute("chamado", chamado);
		return "/chamado/cadastro";
	}

	@PostMapping("/editar")
	public String editar(@Valid Chamado Chamado, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			return "/chamado/cadastro";
		}
		chService.editar(Chamado);
		attr.addFlashAttribute("success", "Chamado editado com sucesso!");
		return "redirect:/chamados/cadastrar";
	}

	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, ModelMap modelo) {

		chService.excluir(id);
		modelo.addAttribute("success", "Chamado excluido com sucesso");

		return listar(modelo);
	}

	@GetMapping("/buscar/{id}")
	public String getPorNumero(@RequestParam("id") Long id, ModelMap model) {
		model.addAttribute("chamados", chService.buscarPorNumero(id));
		return "/chamado/lista";
	}

	@GetMapping("/buscar/equipamento")
	public String getPorEquipamento(@RequestParam("eqSeriebp") String bp, ModelMap model) {
		model.addAttribute("chamados", chService.buscarPorBpEquipamento(bp));
		return "/chamado/lista";
	}

	@GetMapping("/buscar/data")
	public String getPorDatas(
			@RequestParam("chDataAbertura") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate abertura,
			@RequestParam("chDataFechamento") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechamento,
			ModelMap model) {

		model.addAttribute("chamados", chService.buscarPorDatas(abertura, fechamento));
		return "/chamado/lista";
	}

	@ModelAttribute("situacoes")
	public Situacao[] getSituacao() {
		return Situacao.values();
	}

	@ModelAttribute("prioridades")
	public Prioridade[] getPrioridade() {
		return Prioridade.values();
	}

	@ModelAttribute("servicos")
	public List<Servico> getServico() {
		return serService.buscarTodos();
	}

	@ModelAttribute("equipamentos")
	public List<Equipamento> getEquipamento() {
		return eqpService.buscarTodos();
	}

	@ModelAttribute("setores")
	public List<Setor> getSetor() {
		return setService.buscarTodos();
	}

	@ModelAttribute("cargos")
	public List<Cargo> getCargo() {
		return cargoService.buscarTodos();
	}

	@ModelAttribute("tecnicos")
	public List<Tecnico> getTecnico() {
		return tecService.buscarTodos();
	}

	@ModelAttribute("tipos")
	public TipoEquipamento[] getTipos() {
		return TipoEquipamento.values();
	}

}
