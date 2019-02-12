package com.solicitacao.sv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solicitacao.sv.dao.ServicoDao;
import com.solicitacao.sv.dominio.Servico;

@Service
@Transactional(readOnly = false)
public class ServicoImplements implements ServicoService {
	@Autowired
	private ServicoDao dao;

	@Override
	public void salvar(Servico servico) {
		dao.save(servico);
	}

	@Override
	public void editar(Servico servico) {
		dao.update(servico);
	}

	@Override
	public void excluir(Long id) {
		dao.delete(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Servico buscarPorId(Long id) {
		return dao.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Servico> buscarTodos() {
		return dao.findAll();
	}

	@Override
	public List<Servico> buscarPorNome(String nome) {
		return dao.buscarPorNome(nome);
	}

}
