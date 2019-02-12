package com.solicitacao.sv.dao;

import java.util.List;

import com.solicitacao.sv.dominio.Equipamento;

public interface EquipamentoDao {
	void save(Equipamento equipamento);

	void update(Equipamento equipamento);

	void delete(Long id);

	Equipamento findById(Long id);

	List<Equipamento> findAll();

	List<Equipamento> findByServicoId(Long id);

	List<Equipamento> findByDescricao(String descricao);

	List<Equipamento> findBySerie(String serie);
}
