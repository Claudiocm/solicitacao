package com.solicitacao.sv.service;

import java.util.List;

import com.solicitacao.sv.dominio.Equipamento;
import com.solicitacao.sv.dominio.Servico;

public interface EquipamentoService {
	public void salvar(Equipamento equipamento);

	public void editar(Equipamento equipamento);

	public void excluir(Long id);

	public Equipamento buscarPorId(Long id);

	public List<Equipamento> buscarTodos();

	public List<Equipamento> buscarPorDescricao(String descricao);

	public List<Equipamento> buscarPorSerie(String serie);
	
	public Servico adicionaServico(Long id, Long idServico);
	
	public Servico buscarServicoPorEquipamento(Long idEquip, Long idServ);
}
