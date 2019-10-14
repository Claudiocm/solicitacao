package com.solicitacao.sv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solicitacao.sv.dominio.Tecnico;
import com.solicitacao.sv.repository.TecnicoRepository;

@Service
@Transactional(readOnly = false)
public class TecnicoImplements implements TecnicoService {
	@Autowired
	private TecnicoRepository dao;

	@Override
	public void salvar(Tecnico tecnico) {
		dao.save(tecnico);
	}

	@Override
	public void editar(Tecnico tecnico) {
		Tecnico t = dao.findById(tecnico.getId()).get();
		t.setCargo(tecnico.getCargo());
		t.setId(tecnico.getId());
		t.setTecNome(tecnico.getTecNome());
		t.setUsuario(tecnico.getUsuario());
		if (!tecnico.getChamados().isEmpty()) {
			t.getChamados().addAll(tecnico.getChamados());
			;
		}
	}

	@Override
	public void excluir(Long id) {
		dao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Tecnico buscarPorId(Long id) {
		return dao.findById(id).get();
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

	@Override
	public Tecnico buscarPorUsuarioId(Long id) {
		return dao.findByUsuarioId(id).orElse(new Tecnico());
	}

}
