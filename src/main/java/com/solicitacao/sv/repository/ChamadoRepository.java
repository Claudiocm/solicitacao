package com.solicitacao.sv.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.solicitacao.sv.dominio.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado, Long> {

	@Query("Select c from Chamado c where c.chDataAbertura >= :entrada " + "and c.chDataFechamento <= :saida "
			+ "order by c.chDataAbertura asc")
	List<Chamado> findByDataAberturaDataFechamento(LocalDate entrada, LocalDate saida);

	@Query("Select c from Chamado c where c.chDataAbertura = :entrada order by c.chDataAbertura asc")
	List<Chamado> findByDataAbertura(@Param("entrada") LocalDate entrada);

	@Query("Select c from Chamado c where c.chDataFechamento = :saida order by c.chDataAbertura asc")
	List<Chamado> findByDataFechamento(LocalDate saida);

	@Query("Select c from Chamado c where c.equipamento.eqSeriebp like :bp%")
	List<Chamado> findByBp(@Param("bp") String bp);

	@Query("Select c from Chamado c where c.id = :id")
	List<Chamado> findByNumber(@Param("id") Long id);

	@Query("Select c from Chamado c WHERE c.chSituacao = 'Aberto'")
	List<Chamado> buscar();

	@Query("Select c from Chamado c WHERE c.chSituacao = 'Aberto' or c.chSituacao = 'Andamento' "
			+ "or c.chSituacao = 'Aguardando Peça'")
	List<Chamado> buscaLista();

}
