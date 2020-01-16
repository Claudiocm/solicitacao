package com.solicitacao.sv.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.solicitacao.sv.dominio.Chamado;
import com.solicitacao.sv.repository.projection.HistoricoSolicitante;
import com.solicitacao.sv.repository.projection.HistoricoTecnico;

public interface ChamadoRepository extends JpaRepository<Chamado, Long> {

	@Query("select c from Chamado c where (c.id = :id AND c.solicitante.usuario.email like :email%) "
			+ " OR (c.id = :id AND c.tecnico.usuario.email like :email%) order by c.chDataAbertura asc")
	Optional<Chamado> findByIdAndSolicitanteOrTecnicoEmail(Long id, String email);

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

	@Query("Select c from Chamado c WHERE c.chSituacao = 'ABERTO' or c.chSituacao = 'EM ANDAMENTO' or c.chSituacao = 'AGUARDANDO PEÇA'"
			+ " order by c.chDataAbertura asc")
	List<Chamado> buscar();

	@Query("Select c from Chamado c order by c.chDataAbertura asc")
	List<Chamado> buscaLista();

	@Query("select c.id as id, c.solicitante as solicitante, c.chDataAbertura as dataAbertura,"
			+ "c.tecnico as tecnico, c.servico as servico from Chamado c "
			+ "where c.tecnico.usuario.email like :email")
	Page<HistoricoSolicitante> findHistoricoBySolicitanteEmail(String email, Pageable pageable);

	@Query("select c from Chamado c where (c.id = :id AND c.solicitante.usuario.email like :email) "
			+ " OR  (c.id = :id AND c.tecnico.usuario.email like :email)")
	Page<HistoricoTecnico> findHistoricoByTecnicoEmail(String email, Pageable pageable);
	

}
