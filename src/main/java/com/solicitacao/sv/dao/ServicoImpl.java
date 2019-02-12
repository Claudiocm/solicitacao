package com.solicitacao.sv.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.solicitacao.sv.dominio.Servico;

@Repository
public class ServicoImpl extends AbstractDao<Servico, Long> implements ServicoDao{

	@Override
	public List<Servico> buscarPorNome(String nome) {
	  return createQuery("select s from servico s where s.ser_nome like concat('%',?1,'%') ", nome);
	}

}
