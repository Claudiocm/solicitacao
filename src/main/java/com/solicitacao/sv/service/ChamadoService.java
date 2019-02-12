package com.solicitacao.sv.service;

import java.time.LocalDate;
import java.util.List;

import com.solicitacao.sv.dominio.Chamado;

public interface ChamadoService {
	public void salvar(Chamado Chamado);

	public void editar(Chamado Chamado);

	public void excluir(Long id);

	public Chamado buscarPorId(Long id);

	public List<Chamado> buscarTodos();

	boolean solicitacaoTemServicos(Long id);

	public List<Chamado> buscarPorDatas(LocalDate entrada, LocalDate saida);

	public List<Chamado> buscarPorServico(Long id);

	public List<Chamado> buscarPorBpEquipamento(String bp);

	public List<Chamado> buscarPorNumero(Long id);
}
