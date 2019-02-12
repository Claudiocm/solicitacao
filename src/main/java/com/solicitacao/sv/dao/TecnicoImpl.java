package com.solicitacao.sv.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.solicitacao.sv.dominio.Tecnico;

@Repository
public class TecnicoImpl extends AbstractDao<Tecnico, Long> implements TecnicoDao{

	@Override
	public List<Tecnico> findByName(String nome) {
		return createQuery("select t from Tecnico t where t.tec_nome like concat('%',?1,'%') ", nome);
	}

}
