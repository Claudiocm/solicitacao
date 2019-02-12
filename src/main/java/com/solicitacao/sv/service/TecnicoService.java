package com.solicitacao.sv.service;

import java.util.List;

import com.solicitacao.sv.dominio.Tecnico;

public interface TecnicoService {
	public void salvar(Tecnico tecnico);

	public void editar(Tecnico tecnico);

	public void excluir(Long id);

	public Tecnico buscarPorId(Long id);

	public List<Tecnico> buscarTodos();

	public List<Tecnico> buscarPorNome(String nome);
}
