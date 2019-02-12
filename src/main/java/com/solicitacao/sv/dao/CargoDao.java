package com.solicitacao.sv.dao;

import java.util.List;

import com.solicitacao.sv.dominio.Cargo;

public interface CargoDao {
	void save(Cargo cargo);

	void update(Cargo cargo);

	void delete(Long id);

	Cargo findById(Long id);

	List<Cargo> findAll();
}
