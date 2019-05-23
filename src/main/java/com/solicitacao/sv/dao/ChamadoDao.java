package com.solicitacao.sv.dao;

import java.time.LocalDate;
import java.util.List;

import com.solicitacao.sv.dominio.Chamado;

public interface ChamadoDao {
	void save(Chamado Chamado);

	void update(Chamado Chamado);

	void delete(Long id);

	Chamado findById(Long id);

	List<Chamado> findAll();
	
	List<Chamado> findByDataAberturaDataFechamento(LocalDate entrada, LocalDate saida);

	List<Chamado> findByDataAbertura(LocalDate entrada);

	List<Chamado> findByDataFechamento(LocalDate saida);

	List<Chamado> findByServico(Long id);

	List<Chamado> findByBp(String bp);

	List<Chamado> findByNumber(Long id);

	List<Chamado> buscaLista();
	
	List<Chamado> buscar();

}
