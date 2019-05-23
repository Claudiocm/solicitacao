package com.solicitacao.sv.service;

import java.util.List;

import com.solicitacao.sv.dominio.Servico;

public interface ServicoService {
	public void salvar(Servico servico);

	public void editar(Servico servico);

	public void excluir(Long id);

	public Servico buscarPorId(Long id);

	public List<Servico> buscarTodos();

	public List<Servico> buscarPorNome(String nome);

	public List<Servico> buscarPorIdEquipamento(Long id);

	public List<Servico> buscarServico(Long id);
}
