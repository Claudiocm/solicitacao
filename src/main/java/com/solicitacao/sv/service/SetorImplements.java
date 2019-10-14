package com.solicitacao.sv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solicitacao.sv.dominio.Setor;
import com.solicitacao.sv.repository.SetorRepository;

@Service
@Transactional(readOnly = false)
public class SetorImplements implements SetorService {
	@Autowired
	private SetorRepository dao;

	@Override
	public void salvar(Setor setor) {
		dao.save(setor);
	}

	@Override
	public void editar(Setor setor) {
		Setor s = dao.findById(setor.getId()).get();
		s.setId(setor.getId());
		s.setSetNome(setor.getSetNome());
		s.setSetRamal(setor.getSetRamal());
		if (!setor.getUsuarios().isEmpty()) {
			s.getUsuarios().addAll(setor.getUsuarios());
		}
	}

	@Override
	public void excluir(Long id) {
		dao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Setor buscarPorId(Long id) {
		return dao.findById(id).get();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Setor> buscarTodos() {
		return dao.findAll();
	}

	@Override
	public List<Setor> buscarPorNome(String nome) {
		return dao.findByName(nome);
	}

}
