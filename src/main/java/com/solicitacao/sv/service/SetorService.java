package com.solicitacao.sv.service;

import java.util.List;

import com.solicitacao.sv.dominio.Setor;

public interface SetorService {
	public void salvar(Setor setor);

	public void editar(Setor setor);

	public void excluir(Long id);

	public Setor buscarPorId(Long id);

	public List<Setor> buscarTodos();

	public List<Setor> buscarPorNome(String nome);
}
