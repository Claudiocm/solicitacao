package com.solicitacao.sv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solicitacao.sv.dominio.Solicitante;
import com.solicitacao.sv.repository.SolicitanteRepository;

@Service
public class SolicitanteServiceImpl implements SolicitanteService {
	@Autowired
	private SolicitanteRepository dao;

	@Transactional(readOnly = true)
	public Solicitante buscarPorUsuarioEmail(String email) {
		return dao.findByUsuarioEmail(email);
	}

	@Override
	public void salvar(Solicitante solicitante) {
          dao.save(solicitante);
	}

	@Override
	public void editar(Solicitante solicitante) {
		Solicitante sol = dao.findById(solicitante.getId()).get();
		sol.setNome(solicitante.getNome());
		sol.setDtCadastro(solicitante.getDtCadastro());
		sol.setSetor(solicitante.getSetor());
		sol.setUsuario(solicitante.getUsuario());
		sol.setChamados(solicitante.getChamados());
	}

	@Override
	public void excluir(Long id) {
	}

	@Override
	public Solicitante buscarPorId(Long id) {
		Solicitante sol = dao.findById(id).get();
		return sol;
	}

	@Override
	public List<Solicitante> buscarTodos() {
		return dao.findAll();
	}

	@Override
	public List<Solicitante> buscarPorNome(String nome) {
		return dao.findByBuscarPorNome(nome);
	}


}
