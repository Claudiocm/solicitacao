package com.solicitacao.sv.service;

import java.util.List;
import java.util.Set;

import com.solicitacao.sv.dominio.TipoServico;

public interface TipoServicoService {

	TipoServico buscarPorServicoId(Long id);

	void salvar(TipoServico tipoServico);

	void editar(TipoServico tipoServico);

	TipoServico buscarPorModelo(String descricao);

	TipoServico buscarPorId(Long id);

	void remover(Long id);

	List<String> buscarTipoServicoByTermo(String termo);

	Set<TipoServico> buscarPorNomes(String[] nomes);

	List<TipoServico> buscarTodos();

}
