package com.solicitacao.sv.service;

import java.util.List;

import com.solicitacao.sv.dominio.Solicitante;

public interface SolicitanteService {
	public void salvar(Solicitante solicitante);

	public void editar(Solicitante solicitante);

	public void excluir(Long id);

	public Solicitante buscarPorId(Long id);

	public List<Solicitante> buscarTodos();

	public List<Solicitante> buscarPorNome(String nome);
}
