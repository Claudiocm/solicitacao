package com.solicitacao.sv.service;

import java.util.List;
import java.util.Set;

import com.solicitacao.sv.dominio.TipoEquipamento;

public interface TipoEquipamentoService {
	public TipoEquipamento buscarPorEquipamentoId(Long id);

	public void salvar(TipoEquipamento equipamento);

	public void editar(TipoEquipamento equipamento);

	public TipoEquipamento buscarPorModelo(String modelo);

	public Set<TipoEquipamento> buscarPorNomes(String[] nomes);

	public List<String> buscarTipoEquipamentoByTermo(String termo);

	public void remover(Long id);

	public TipoEquipamento buscarPorId(Long id);

	List<TipoEquipamento> buscarTodos();
}
