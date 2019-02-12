package com.solicitacao.sv.dao;

import java.util.List;

import com.solicitacao.sv.dominio.Tecnico;

public interface TecnicoDao {
	void save(Tecnico tecnico);

	void update(Tecnico tecnico);

	void delete(Long id);

	Tecnico findById(Long id);

	List<Tecnico> findAll();

	List<Tecnico> findByName(String nome);
}
