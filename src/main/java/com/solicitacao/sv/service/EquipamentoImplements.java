package com.solicitacao.sv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solicitacao.sv.dominio.Equipamento;
import com.solicitacao.sv.dominio.Servico;
import com.solicitacao.sv.repository.EquipamentoRepository;

@Service
@Transactional(readOnly = false)
public class EquipamentoImplements implements EquipamentoService {

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

}
