package com.solicitacao.sv.dao;

import java.util.List;

import com.solicitacao.sv.dominio.Servico;

public interface ServicoDao {
	void save(Servico servico);

	void update(Servico servico);

	void delete(Long id);

	Servico findById(Long id);

	List<Servico> findAll();

	List<Servico> buscarPorNome(String nome);

	List<Servico> buscarPorEquipamento(Long id);

	List<Servico> buscarServicoId(Long id);
}
