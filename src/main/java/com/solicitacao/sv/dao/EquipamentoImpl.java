package com.solicitacao.sv.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.solicitacao.sv.dominio.Equipamento;

@Repository
public class EquipamentoImpl extends AbstractDao<Equipamento, Long> implements EquipamentoDao{

	@Override
	public List<Equipamento> findByServicoId(Long id) {
		return createQuery("select e from Equipamento e where e.servico.id = ?1", id);
	}

	@Override
	public List<Equipamento> findByDescricao(String descricao) {
		return createQuery("select e from Equipamento e where e.eq_descricao like concat('%',?1,'%') ", descricao);
	}

	@Override
	public List<Equipamento> findBySerie(String serie) {
		return createQuery("select e from Equipamento e where e.eq_seriebp = ?1 ", serie);
	}


}
