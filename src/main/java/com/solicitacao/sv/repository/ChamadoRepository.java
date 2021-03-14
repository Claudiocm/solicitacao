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
import com.solicitacao.sv.dominio.Tecnico;
import com.solicitacao.sv.repository.projection.HistoricoSolicitante;
import com.solicitacao.sv.repository.projection.HistoricoTecnico;

public interface ChamadoRepository extends JpaRepository<Chamado, Long> {

	@Query("Select distinct c.chSituacao From Chamado c Where c.chSituacao like %:situacao%")
	List<String> findByTermo(@Param("situacao") String situacao);
	
	@Query("select c from Chamado c where (c.id = :id AND c.solicitante.usuario.email like :email%) "
			+ " OR (c.id = :id AND c.tecnico.usuario.email like :email%) order by c.chDataAbertura asc")
	Optional<Chamado> findByIdAndSolicitanteOrTecnicoEmail(Long id, String email);

	@Query("Select c from Chamado c where c.chDataAbertura >= :entrada " + "and c.chDataFechamento <= :saida "
			+ "order by c.chDataAbertura asc")
	List<Chamado> findByDataAberturaDataFechamento(LocalDate entrada, LocalDate saida);

	@Query("Select c from Chamado c where c.chDataAbertura = :entrada order by c.chDataAbertura asc")
	List<Chamado> findByDataAbertura(@Param("entrada") LocalDate entrada);

	@Query("Select c from Chamado c where c.chDataFechamento = :saida order by c.chDataAbertura asc")
	List<Chamado> findByDataFechamento(@Param("saida") LocalDate saida);

	@Query("Select c from Chamado c where c.equipamento.eqSeriebp like :bp%")
	List<Chamado> findByBp(@Param("bp") String bp);

	@Query("Select c from Chamado c where c.id = :id")
	List<Chamado> findByNumber(@Param("id") Long id);

	@Query("Select c from Chamado c "
			+ "WHERE c.chSituacao = 'ABERTO' or c.chSituacao = 'EM_ANDAMENTO' or c.chSituacao = 'AGUARDANDO_PECA'"
			+ "Order by c.chDataAbertura asc")
	List<Chamado> buscar();
	
	@Query("Select COUNT(c) as total, c.servico as servico from Chamado c "
			+ "WHERE c.chSituacao = 'ABERTO' or c.chSituacao = 'EM_ANDAMENTO' or c.chSituacao = 'AGUARDANDO_PECA'"
			+ " GROUP BY c.servico.serNome Order by c.chDataAbertura asc")
	List<Chamado> buscarRelatorio();

	@Query("Select c from Chamado c order by c.chDataAbertura asc")
	List<Chamado> buscaLista();

	@Query("select c from Chamado c where c.solicitante.usuario.email like :email")
	List<Chamado> findHistoricoBySolicitanteEmail(String email);

	@Query("select c from Chamado c where c.tecnico.usuario.email like :email")
	List<Chamado> findHistoricoByTecnicoEmail(String email);
	
	@Query("select c from Chamado c where c.tecnico.usuario.email like :email"
			+ " OR c.solicitante.usuario.email like :email")
	Optional<Chamado> findByUsuarioEmail(String email);

	@Query("select c from Chamado c join c.equipamento e where e.id = :id Order By c.chDataAbertura desc")
	List<Chamado> findByIdEquipamentoChamado(Long id);
}
