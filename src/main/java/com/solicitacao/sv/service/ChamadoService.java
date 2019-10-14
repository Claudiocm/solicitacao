package com.solicitacao.sv.service;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import com.solicitacao.sv.dominio.Chamado;
import com.solicitacao.sv.dominio.Servico;

public interface ChamadoService {
	public void salvar(Chamado Chamado);

	public void editar(Chamado Chamado);

	public void excluir(Long id);

	public Chamado buscarPorId(Long id);

	public List<Chamado> buscarTodos();

	public List<Chamado> buscar();

	public List<Chamado> buscarLista();

	public List<Chamado> buscarPorDatas(LocalDate entrada, LocalDate saida);

	public List<Chamado> buscarPorBpEquipamento(String bp);

	public List<Chamado> buscarPorNumero(Long id);

	public void adicionaServico(@Valid Chamado chamado, List<Servico> buscarPorNome);

}
