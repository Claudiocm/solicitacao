package com.solicitacao.sv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solicitacao.sv.dao.TecnicoDao;
import com.solicitacao.sv.dominio.Tecnico;

@Service
@Transactional(readOnly = false)
public class TecnicoImplements implements TecnicoService {
	@Autowired
	private TecnicoDao dao;

	@Override
	public void salvar(Tecnico tecnico) {
		dao.save(tecnico);
	}

	@Override
	public void editar(Tecnico tecnico) {
		dao.update(tecnico);
	}

	@Override
	public void excluir(Long id) {
		dao.delete(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Tecnico buscarPorId(Long id) {
		return dao.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Tecnico> buscarTodos() {
		return dao.findAll();
	}

	@Override
	public List<Tecnico> buscarPorNome(String nome) {
		return dao.findByName(nome);
	}

}
