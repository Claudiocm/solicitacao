package com.solicitacao.sv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solicitacao.sv.dao.EquipamentoDao;
import com.solicitacao.sv.dominio.Equipamento;

@Service
@Transactional(readOnly = false)
public class EquipamentoImplements implements EquipamentoService {

	@Autowired
	private EquipamentoDao dao;

	@Override
	public void salvar(Equipamento equipamento) {
		dao.save(equipamento);
	}

	@Override
	public void editar(Equipamento equipamento) {
		dao.update(equipamento);
	}

	@Override
	public void excluir(Long id) {
		dao.delete(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Equipamento buscarPorId(Long id) {
		return dao.findById(id);
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

}
