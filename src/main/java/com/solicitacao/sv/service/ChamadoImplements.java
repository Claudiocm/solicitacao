package com.solicitacao.sv.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.solicitacao.sv.datatables.Datatables;
import com.solicitacao.sv.datatables.DatatablesColunas;
import com.solicitacao.sv.dominio.Chamado;
import com.solicitacao.sv.dominio.Servico;
import com.solicitacao.sv.dominio.Tecnico;
import com.solicitacao.sv.exception.AcessoNegadoException;
import com.solicitacao.sv.repository.ChamadoRepository;
import com.solicitacao.sv.repository.projection.HistoricoSolicitante;
import com.solicitacao.sv.repository.projection.HistoricoTecnico;

@Service
@Transactional(readOnly = false)
public class ChamadoImplements implements ChamadoService {
	@Autowired
	private ChamadoRepository dao;
	@Autowired
	private Datatables datatables;

	@Override
	public void salvar(Chamado chamado) {
		dao.save(chamado);
	}

	@Override
	@Transactional(readOnly = false)
	public void editar(Chamado chamado) {
		Chamado chama = dao.findById(chamado.getId()).get();

		//chama.setId(chamado.getId());
		chama.setChDataAbertura(chamado.getChDataAbertura());
		chama.setChDataFechamento(chamado.getChDataFechamento());
		chama.setChIp(chamado.getChIp());
		chama.setChObservacao(chamado.getChObservacao());
		chama.setChPrioridade(chamado.getChPrioridade());
		chama.setChSituacao(chamado.getChSituacao());
		chama.setChProblema(chamado.getChProblema());
		if (chamado != null) {
			chama.setEquipamento(chamado.getEquipamento());
		}
		if (chamado != null) {
			chama.setServico(chamado.getServico());
		}
		chama.setSetor(chamado.getSetor());
		chama.setTecnico(chamado.getTecnico());
		chama.setSolicitante(chamado.getSolicitante());
	}

	@Override
	public void excluir(Long id) {
		dao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Chamado buscarPorId(Long id) {
		return dao.findById(id).get();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Chamado> buscarTodos() {
		return dao.findAll();
	}
	
	@Override
	public Iterable<Chamado> todos() {
		return dao.findAll();
	}

	@Override
	public List<Chamado> buscarPorDatas(LocalDate entrada, LocalDate saida) {
		if (entrada != null && saida != null) {
			return dao.findByDataAberturaDataFechamento(entrada, saida);
		} else if (entrada != null) {
			return dao.findByDataAbertura(entrada);
		} else if (saida != null) {
			return dao.findByDataFechamento(saida);
		} else {
			return new ArrayList<>();
		}
	}

	@Override
	public List<Chamado> buscarPorBpEquipamento(String bp) {
		return dao.findByBp(bp);
	}

	@Override
	public List<Chamado> buscarPorNumero(Long id) {
		return dao.findByNumber(id);
	}

	@Override
	public void adicionaServico(@Valid Chamado chamado, List<Servico> buscarPorNome) {
		dao.save(chamado);
	}

	@Override
	public List<Chamado> buscar() {
		return dao.buscar();
	}

	@Override
	public List<Chamado> buscarLista() {
		return dao.buscaLista();
	}

	@Transactional(readOnly = true)
	public List<Chamado> buscarHistoricoPorSolicitanteEmail(String email) {
		List<Chamado> page = dao.findHistoricoBySolicitanteEmail(email);
		return page;
	}

	@Transactional(readOnly = true)
	public List<Chamado> buscarHistoricoPorTecnicoEmail(String email) {
		List<Chamado> page = dao.findHistoricoByTecnicoEmail(email);
		return page;
	}

	@Transactional(readOnly = true)
	public Chamado buscarPorIdEUsuario(Long id, String email) {
		return dao
				.findByIdAndSolicitanteOrTecnicoEmail(id, email)
				.orElseThrow(() -> new AcessoNegadoException("Acesso negado ao usuário: " + email));
	}

	public Chamado buscarPorUsuarioEmail(String username) {
		return dao.findByUsuarioEmail(username).orElse(new Chamado());
	}

}
