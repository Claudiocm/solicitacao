package com.solicitacao.sv.controller;

import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.solicitacao.sv.dominio.Solicitante;
import com.solicitacao.sv.dominio.Tecnico;
import com.solicitacao.sv.dominio.TipoEquipamento;
import com.solicitacao.sv.dominio.TipoServico;
import com.solicitacao.sv.service.CargoServiceImpl;
import com.solicitacao.sv.service.ChamadoImplements;
import com.solicitacao.sv.service.EquipamentoImplements;
import com.solicitacao.sv.service.ServicoImplements;
import com.solicitacao.sv.service.SetorImplements;
import com.solicitacao.sv.service.SolicitanteServiceImpl;
import com.solicitacao.sv.service.TecnicoImplements;
import com.solicitacao.sv.service.TipoEquipamentoImpl;
import com.solicitacao.sv.service.TipoServicoImpl;
import com.solicitacao.sv.validator.ChamadoValidator;

@Controller
@RequestMapping("/chamados")
public class ChamadoController {
	@Autowired
	private ChamadoImplements chService;
	@Autowired
	private EquipamentoImplements eqpService;
	@Autowired
	private ServicoImplements serService;
	@Autowired
	private TecnicoImplements tecService;
	@Autowired
	private SetorImplements setService;
	@Autowired
	private SolicitanteServiceImpl solService;
	@Autowired
	private CargoServiceImpl cargoService;
	@Autowired
	private TipoEquipamentoImpl tipoEquipamentoService;
	@Autowired
	private TipoServicoImpl tipoServicoService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new ChamadoValidator());
	}

	@PreAuthorize("hasAnyAuthority('TECNICO','ADMIN','SOLICITANTE')")
	@GetMapping("/cadastrar")
	public String cadastrar(Chamado chamado) throws UnknownHostException {
		/*
		 * quando for solicitante setar o ip da maquina InetAddress ip =
		 * InetAddress.getLocalHost(); chamado.setChIp(ip.getHostAddress());
		 */
		// long chamados = chService.buscarTodos().size();
		// chamado.setId((long) chamados + 1);
		chamado.setchSituacao(Situacao.ABERTO);
		chamado.setChDataAbertura(LocalDate.now());

		return "/chamado/cadastro";
	}

	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("chamados", chService.buscarTodos());
		return "/chamado/lista";
	}

	@PreAuthorize("hasAnyAuthority('TECNICO','ADMIN')")
	@GetMapping("/buscarLista")
	public String buscaLista(ModelMap model) {
		model.addAttribute("chamados", chService.buscarLista());
		return "/chamado/lista";
	}

	@PreAuthorize("hasAnyAuthority('TECNICO','ADMIN')")
	@GetMapping("/buscar")
	public String busca(ModelMap model) {
		model.addAttribute("chamados", chService.buscar());
		return "/chamado/painel";
	}

	@PreAuthorize("hasAnyAuthority('TECNICO','ADMIN','SOLICITANTE')")
	@PostMapping("/salvar")
	public synchronized String salvar(@Valid Chamado chamado, BindingResult result, RedirectAttributes attr,
			@AuthenticationPrincipal User user) throws InterruptedException {
        //Tecnico tecnico = tecService.buscarPorUsuarioEmail(user.getUsername());
        //Solicitante solicitante = solService.buscarPorUsuarioEmail(user.getUsername());

		if (result.hasErrors()) {
			return "/chamado/cadastro";
		}
		
		chService.salvar(chamado);
		attr.addAttribute("success", "Chamado Nº 000" + chamado.getId() + " aberto com sucesso!/n");
		attr.addAttribute("title","Anote o número do chamado para consulta");
		attr.addAttribute("text", "Data da Solicitação: "+chamado.getChDataAbertura()+" - Técnico: "+chamado.getTecnico().getTecNome());
		attr.addAttribute("chamado", chamado);
		return "redirect:/chamados/painel";
	}

	@PreAuthorize("hasAnyAuthority('TECNICO','ADMIN')")
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap modelo, @AuthenticationPrincipal User user) {
		Chamado chamado = chService.buscarPorIdEUsuario(id, user.getUsername());

		modelo.addAttribute("chamado", chamado);
		return "/chamado/cadastro";
	}

	@PreAuthorize("hasAnyAuthority('TECNICO','ADMIN')")
	@PostMapping("/editar")
	public String editar(@Valid Chamado chamado, RedirectAttributes attr, BindingResult result)
			throws InterruptedException {
		if (result.hasErrors()) {
			return "/chamado/cadastro";
		}
		if (chamado.getChSituacao().getDescricao().contains("FECHADO")
				|| chamado.getChSituacao().getDescricao().contains("ENTREGUE") && chamado.getChDataFechamento().equals(LocalDate.now())) {
			chamado.setChDataFechamento(LocalDate.now());
			chService.editar(chamado);
			attr.addFlashAttribute("success", "Chamado encerrado com sucesso!");
			return "redirect:/chamados/buscar";
		} else {
			chService.editar(chamado);
			attr.addFlashAttribute("success", "Solicitação Nº SS-000" + chamado.getId() + " atendido com sucesso!");
			attr.addFlashAttribute("text", "Data de Solicitação: "+chamado.getChDataAbertura()+" - Técnico"+chamado.getTecnico().getTecNome());
			attr.addAttribute("chamado", chamado);
			return "redirect:/chamados/cadastrar";
		}
		
	}

	@GetMapping("/selecionar/{id}")
	public String preSelecionar(@PathVariable("id") Long id, ModelMap modelo, @AuthenticationPrincipal User user) {
		Chamado chamado = chService.buscarPorIdEUsuario(id, user.getUsername());

		modelo.addAttribute("chamado", chamado);
		return "/chamado/detalhe-chamado";
	}

	@PreAuthorize("hasAnyAuthority('TECNICO','ADMIN')")
	@PostMapping("/selecionar")
	public String selecionar(@Valid Chamado chamado, RedirectAttributes attr, BindingResult result)
			throws InterruptedException {
		if (result.hasErrors()) {
			return "/chamado/detalhe-chamado";
		}
		return "redirect:/chamados/selecionar";
		
	}
	@PreAuthorize("hasAnyAuthority('TECNICO','ADMIN')")
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, ModelMap modelo) {

		chService.excluir(id);
		modelo.addAttribute("success", "Chamado excluido com sucesso");

		return listar(modelo);
	}

	// abrir pagina de historico de chamdoo do tecnico
	@PreAuthorize("hasAnyAuthority('TECNICO','ADMIN')")
	@GetMapping("/historico/tecnico")
	public String historicoTecnico(ModelMap model, @AuthenticationPrincipal User user) {
		/*
		 * if (user.getAuthorities().contains(new
		 * SimpleGrantedAuthority(PerfilTipo.TECNICO.getDesc()))) {
		 * model.addAttribute("chamados",
		 * chService.buscarHistoricoPorTecnicoEmail(user.getUsername())); }
		 */
		model.addAttribute("chamados", chService.buscarHistoricoPorTecnicoEmail(user.getUsername()));
		return "/chamado/historico-tecnico";
	}

	// abrir pagina de historico de chamdo do Solicitante
	@PreAuthorize("hasAnyAuthority('SOLICITANTE','ADMIN','TECNICO')")
	@GetMapping("/historico/solicitante")
	public String historicoSolicitante(ModelMap model, @AuthenticationPrincipal User user) {
		/*
		 * if (user.getAuthorities().contains(new
		 * SimpleGrantedAuthority(PerfilTipo.SOLICITANTE.getDesc()))) {
		 * model.addAttribute("chamados",
		 * chService.buscarHistoricoPorSolicitanteEmail(user.getUsername())); }
		 */
		model.addAttribute("chamados", chService.buscarHistoricoPorSolicitanteEmail(user.getUsername()));
		return "/solicitante/historico-Solicitante";
	}

	@GetMapping("/buscar/numero")
	public String getPorNumero(@RequestParam("numero") Long numero, ModelMap model) {
		model.addAttribute("chamados", chService.buscarPorNumero(numero));
		return "/chamado/painel";
	}

	@GetMapping("/buscar/patrimonio")
	public String getPorEquipamento(@RequestParam("eqSeriebp") String eqSeriebp, ModelMap model) {
		model.addAttribute("chamados", chService.buscarPorBpEquipamento(eqSeriebp));
		return "/chamado/painel";
	}

	@GetMapping("/buscar/data")
	public String getPorDatas(
			@RequestParam("chDataAbertura") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate abertura,
			@RequestParam("chDataFechamento") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechamento,
			ModelMap model) {

		model.addAttribute("chamados", chService.buscarPorDatas(abertura, fechamento));
		return "/chamado/painel";
	}

	@GetMapping("/equipamento/{id}")
	public ResponseEntity<?> getServicoEquipamentos(@PathVariable("id") Long id) {
		return ResponseEntity.ok(serService.buscarPorIdEquipamento(id));
	}

	@GetMapping("/page")
	public String findPage(@RequestParam(defaultValue = "0") int page, Model modelo) {
		modelo.addAttribute("data", chService.findPage(PageRequest.of(page, 5)));
		modelo.addAttribute("currentPage", page);

		return "/chamado/historico-tecnico";
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

	@ModelAttribute("solicitantes")
	public List<Solicitante> getSolicitante() {
		return solService.buscarTodos();
	}

	@ModelAttribute("tipoEquipamentos")
	public List<TipoEquipamento> getTipoEquipamentos() {
		return tipoEquipamentoService.buscarTodos();
	}

	@ModelAttribute("tiposServicos")
	public List<TipoServico> getTipoServicos() {
		return tipoServicoService.buscarTodos();
	}

}
