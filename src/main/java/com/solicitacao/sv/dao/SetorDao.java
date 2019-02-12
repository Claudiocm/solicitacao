package com.solicitacao.sv.dao;

import java.util.List;

import com.solicitacao.sv.dominio.Setor;

public interface SetorDao {
	void save(Setor servico);

	void update(Setor servico);

	void delete(Long id);

	Setor findById(Long id);

	List<Setor> findAll();

	List<Setor> findByName(String nome);
}
