package com.solicitacao.sv.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solicitacao.sv.datatables.Datatables;
import com.solicitacao.sv.datatables.DatatablesColunas;
import com.solicitacao.sv.dominio.Equipamento;
import com.solicitacao.sv.dominio.Servico;
import com.solicitacao.sv.repository.EquipamentoRepository;

@Service
@Transactional(readOnly = false)
public class EquipamentoImplements implements EquipamentoService {
    @Autowired
    private Datatables datatables;
	@Autowired
	private EquipamentoRepository dao;

	@Override
	public void salvar(Equipamento equipamento) {
		dao.save(equipamento);
	}

	@Override
	public void editar(Equipamento equipamento) {
		Equipamento e = dao.findById(equipamento.getId()).get();
		e.setEqDescricao(equipamento.getEqDescricao());
		e.setEqModelo(equipamento.getEqModelo());
		e.setEqSeriebp(equipamento.getEqSeriebp());
		e.setTipo(equipamento.getTipo());
		if (!equipamento.getServicos().isEmpty()) {
			e.getServicos().addAll(equipamento.getServicos());
		}
		e.setTecnico(equipamento.getTecnico());
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

	@Override
	public Servico buscarServicoPorEquipamento(Long idEquip, Long idServ) {
		Equipamento equipamento = buscarPorId(idEquip);
		if (equipamento != null) {
			for (Servico servico : equipamento.getServicos()) {
				if (servico.getId().equals(idServ)) {
					return servico;
				}
			}
		}
		return null;
	}

	@Override
	public Servico adicionaServico(Long id, Long idServico) {
		Equipamento e = buscarPorId(id);
		if(e == null){
			return null;
		}
		
		return null;
	}

	@Override
	public Equipamento buscarEquipamentoPorTipo(Long id, HttpServletRequest request) {
		return null;
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
		Page<?> page = datatables.getSearch().isEmpty()
				? dao.findAll(datatables.getPageable())
				: dao.findAllByTitulo(datatables.getSearch(), datatables.getPageable());
		return datatables.getResponse(page);
	}

	@Transactional(readOnly = true)
	public Map<String, Object> buscarEquipamentos(HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.EQUIPAMENTOS);
		Page<?> page = datatables.getSearch().isEmpty()
				? dao.findAll(datatables.getPageable())
				: dao.findAllByModelo(datatables.getSearch(), datatables.getPageable());
		return datatables.getResponse(page);
	}

	public boolean equipamentoTemServicos(Long id) {
		if(buscarPorId(id).getServicos().isEmpty()) {
			return false;
		}
		return true;
	}

	public boolean equiapamentoTemChamado(Long id) {
		if(buscarPorId(id).getChamados().isEmpty()) {
			return false;
		}
		return true;
	}

	public boolean equipamentoTemTecnicos(Long id) {
		if(buscarPorId(id).getTecnico().isEmpty()) {
			return false;
		}
		return true;
	}

}
