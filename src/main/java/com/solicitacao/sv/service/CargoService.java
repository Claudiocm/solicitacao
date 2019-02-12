package com.solicitacao.sv.service;

import java.util.List;

import com.solicitacao.sv.dominio.Cargo;

public interface CargoService {
	public void salvar(Cargo cargo);

	public void editar(Cargo cargo);

	public void excluir(Long id);

	public Cargo buscarPorId(Long id);

	public List<Cargo> buscarTodos();

	boolean cargoTemTecnicos(Long id);
}
