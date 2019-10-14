package com.solicitacao.sv.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solicitacao.sv.dominio.Equipamento;
import com.solicitacao.sv.dominio.Servico;
import com.solicitacao.sv.repository.ServicoRepository;

@Service
@Transactional(readOnly = false)
public class ServicoImplements implements ServicoService {
	@Autowired
	private ServicoRepository dao;

	@Override
	public void salvar(Servico servico) {
		dao.save(servico);
	}

	@Override
	public void editar(Servico servico) {
		Servico s = dao.findById(servico.getId()).get();
		s.setId(servico.getId());
		s.setSerNome(servico.getSerNome());
		s.setEquipamento(servico.getEquipamento());

	}

	@Override
	public void excluir(Long id) {
		dao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Servico buscarPorId(Long id) {
		Optional<Servico> serv = dao.findById(id);
		return serv.orElse(null);
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

	@Override
	public List<Servico> buscarPorIdEquipamento(Long id) {
		return dao.buscarPorEquipamento(id);
	}

	@Override
	public List<Servico> buscarServico(Long id) {
		return dao.buscarServicoId(id);
	}

	@Override
	public Iterable<Servico> buscarPorIdEquipamento(Equipamento e) {
		return dao.findByEquipamento(e);
	}

}
