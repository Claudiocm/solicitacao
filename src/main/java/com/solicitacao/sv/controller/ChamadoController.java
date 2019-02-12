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

import com.solicitacao.sv.dominio.Chamado;
import com.solicitacao.sv.dominio.Equipamento;
import com.solicitacao.sv.dominio.Prioridade;
import com.solicitacao.sv.dominio.Servico;
import com.solicitacao.sv.dominio.Setor;
import com.solicitacao.sv.dominio.Situacao;
import com.solicitacao.sv.dominio.Tecnico;
import com.solicitacao.sv.dominio.TipoEquipamento;
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

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new ChamadoValidator());
	}

	@GetMapping("/cadastrar")
	public String cadastrar(Chamado Chamado) {
		return "/chamado/cadastro";
	}

	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("chamados", chService.buscarTodos());
		return "/chamado/lista";
	}

	@GetMapping("/painel")
	public String painel(ModelMap model) {
		model.addAttribute("chamados", chService.buscarTodos());
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
		modelo.addAttribute("chamado", chService.buscarPorId(id));
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

	@GetMapping("/buscar/numero")
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
			@RequestParam("dataAbertura") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate abertura,
			@RequestParam("dataFechamento") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechamento,
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

	@ModelAttribute("tecnicos")
	public List<Tecnico> getTecnico() {
		return tecService.buscarTodos();
	}

	@ModelAttribute("eqTipos")
	public TipoEquipamento[] getEqTipos() {
		return TipoEquipamento.values();
	}
}
