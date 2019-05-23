package com.solicitacao.sv.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solicitacao.sv.dao.ChamadoDao;
import com.solicitacao.sv.dominio.Chamado;
import com.solicitacao.sv.dominio.Servico;

@Service
@Transactional(readOnly = false)
public class ChamadoImplements implements ChamadoService {
	@Autowired
	private ChamadoDao dao;

	@Override
	public void salvar(Chamado Chamado) {
		dao.save(Chamado);
	}

	@Override
	public void editar(Chamado Chamado) {
		dao.update(Chamado);
	}

	@Override
	public void excluir(Long id) {
		dao.delete(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Chamado buscarPorId(Long id) {
		return dao.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Chamado> buscarTodos() {
		return dao.findAll();
	}

	@Override
	public List<Chamado> buscarPorDatas(LocalDate entrada, LocalDate saida) {
		if (entrada != null && saida != null) {
			return dao.findByDataAberturaDataFechamento(entrada, saida);
		} else if (entrada != null) {
			return dao.findByDataAbertura(entrada);
		} else if (saida != null) {
			return dao.findByDataFechamento(saida);
		} else {
			return new ArrayList<>();
		}
	}

	@Override
	public List<Chamado> buscarPorServico(Long id) {
		return dao.findByServico(id);
	}

	@Override
	public List<Chamado> buscarPorBpEquipamento(String bp) {
		return dao.findByBp(bp);
	}

	@Override
	public List<Chamado> buscarPorNumero(Long id) {
		return dao.findByNumber(id);
	}

	@Override
	public void adicionaServico(@Valid Chamado chamado, List<Servico> buscarPorNome) {
		dao.save(chamado);
	}

	@Override
	public List<Chamado> buscar() {
		return dao.buscar();
	}

	@Override
	public List<Chamado> buscarLista() {
		return dao.buscaLista();
	}


}
