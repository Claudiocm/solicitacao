package com.solicitacao.sv.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solicitacao.sv.datatables.Datatables;
import com.solicitacao.sv.datatables.DatatablesColunas;
import com.solicitacao.sv.dominio.Chamado;
import com.solicitacao.sv.dominio.Equipamento;
import com.solicitacao.sv.dominio.Servico;
import com.solicitacao.sv.repository.ChamadoRepository;
import com.solicitacao.sv.repository.EquipamentoRepository;
import com.solicitacao.sv.repository.ServicoRepository;

@Service
@Transactional(readOnly = false)
public class EquipamentoImplements implements EquipamentoService {
	@Autowired
	private Datatables datatables;
	@Autowired
	private EquipamentoRepository dao;
	@Autowired
	private ServicoRepository daoServico;
	@Autowired
	private ChamadoRepository daoChamado;

	@Override
	public void salvar(Equipamento equipamento) {
		dao.save(equipamento);
	}

	@Override
	public void editar(Equipamento equipamento) {
		Equipamento e = dao.findById(equipamento.getId()).get();
		List<Chamado> chamados = daoChamado.findByIdEquipamentoChamado(e.getId());
        List<Servico> servicos = daoServico.buscarServicoId(e.getId());
        
		e.setEqDescricao(equipamento.getEqDescricao());
		e.setEqModelo(equipamento.getEqModelo());
		e.setEqSeriebp(equipamento.getEqSeriebp());
		e.setTipo(equipamento.getTipo());
		if (!e.getServicos().isEmpty()) {
			 e.getServicos().addAll(equipamento.getServicos());
		}
		//e.setServicos(servicos);

		if (!e.getChamados().isEmpty()) {
			e.setChamados(chamados);
		}
	}

	@Override
	public void excluir(Long id) {
		dao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Equipamento buscarPorId(Long id) {
		return dao.findById(id).get();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Equipamento> buscarTodos() {
		return dao.findAll();
	}

	@Override
	public List<Equipamento> buscarPorDescricao(String descricao) {
		return dao.findByDescricao(descricao);
	}

	@Override
	public List<Equipamento> buscarPorSerie(String serie) {
		return dao.findBySerie(serie);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Equipamento> buscarEquipamentosPorServico(String titulo) {
		return dao.findByDescricao(titulo);
	}

	@Transactional(readOnly = true)
	public Map<String, Object> buscarServicos(HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.SERVICOS);
		Page<?> page = datatables.getSearch().isEmpty() ? dao.findAll(datatables.getPageable())
				: dao.findAllByTitulo(datatables.getSearch(), datatables.getPageable());
		return datatables.getResponse(page);
	}

	@Transactional(readOnly = true)
	public Map<String, Object> buscarEquipamentos(HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.EQUIPAMENTOS);
		Page<?> page = datatables.getSearch().isEmpty() ? dao.findAll(datatables.getPageable())
				: dao.findAllByModelo(datatables.getSearch(), datatables.getPageable());
		return datatables.getResponse(page);
	}

	public boolean equipamentoTemServicos(Long id) {
		if (buscarPorId(id).getServicos().isEmpty()) {
			return false;
		}
		return true;
	}

	public boolean equiapamentoTemChamado(Long id) {
		if (buscarPorId(id).getChamados().isEmpty()) {
			return false;
		}
		return true;
	}

}
