package com.solicitacao.sv.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.solicitacao.sv.dominio.Setor;

@Repository
public class SetorImpl extends AbstractDao<Setor, Long> implements SetorDao{

	@Override
	public List<Setor> findByName(String nome) {
		return createQuery("Select s from Setor s where s.setNome = ?1", nome);
	}

}
