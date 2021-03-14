package com.solicitacao.sv.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.solicitacao.sv.dominio.Cargo;
import com.solicitacao.sv.util.PaginacaoUtil;

public interface CargoService {
	public void salvar(Cargo cargo);

	public void editar(Cargo cargo);

	public void excluir(Long id);

	public Cargo buscarPorId(Long id);

	public List<Cargo> buscarTodos();

	public boolean cargoTemTecnicos(Long id);

	public List<Cargo> buscarPorNome(String nome);

	Page<Cargo> findPagenated(int page, int pageSize, String sortField, String sortDirection);
}
