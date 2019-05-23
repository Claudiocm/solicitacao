package com.solicitacao.sv.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.solicitacao.sv.dominio.Chamado;

@Repository
public class ChamadoImpl extends AbstractDao<Chamado, Long> implements ChamadoDao {

	@Override
	public List<Chamado> findByDataAberturaDataFechamento(LocalDate entrada, LocalDate saida) {
		String jpql = new StringBuilder("select c from chamado c ")
				.append("where c.chDataAbertura >= ?1 and c.chDataFechamento <= ?2 ").append("order by c.chDataAbertura asc")
				.toString();
		return createQuery(jpql, entrada, saida);
	}

	@Override
	public List<Chamado> findByDataAbertura(LocalDate entrada) {
		String jpql = new StringBuilder("select c from chamado c ").append("where c.chDataAbertura = ?1 ")
				.append("order by c.chDataAbertura asc").toString();
		return createQuery(jpql, entrada);
	}

	@Override
	public List<Chamado> findByDataFechamento(LocalDate saida) {
		String jpql = new StringBuilder("select c from chamado c ").append("where c.chDataFechamento = ?1 ")
				.append("order by c.chDataAbertura asc").toString();
		return createQuery(jpql, saida);
	}

	@Override
	public List<Chamado> findByServico(Long id) {
		return createQuery("Select c from Chamado c where c.servico.id = ?1 ", id);
	}

	@Override
	public List<Chamado> findByBp(String bp) {
		return createQuery("Select c from Chamado c where c.equipamento.eqSeriebp like concat('%',?1,'%')", bp);
	}

	@Override
	public List<Chamado> findByNumber(Long id) {
		return createQuery("Select c from Chamado c where c.id = ?1", id);
	}
	
	@Override
	public List<Chamado> buscar() {
		return createQuery("Select c from Chamado c WHERE c.chSituacao = 'Aberto'");
	}

	@Override
	public List<Chamado> buscaLista() {
		return createQuery("Select c from Chamado c WHERE c.chSituacao = 'Aberto' or c.chSituacao = 'Andamento' or c.chSituacao = 'Aguardando Peça'");
		
	}

}
