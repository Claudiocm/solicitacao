package com.solicitacao.sv.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.solicitacao.sv.dominio.TipoServico;

public interface TipoServicoRepository extends JpaRepository<TipoServico, Long> {
	@Query("select t from TipoServico t where t.id = :id")
	Optional<TipoServico> findByTipoServicoId(Long id);

	@Query("select t.descricao from TipoServico t where t.descricao like :modelo%")
	Optional<TipoServico> findByTipoServicoModelo(String modelo);

	@Query("select t.descricao from TipoServico t where t.descricao like :termo%")
	List<String> findTipoServicoByTermo(String termo);

	@Query("select t from TipoServico t where t.descricao like :search%")
	Page<TipoServico> findAllByDescricao(String search, Pageable pageable);

	@Query("select t from TipoServico t where t.descricao IN :nomes")
	Set<TipoServico> findByNomes(String[] nomes);

	@Query("select t from TipoServico t where t.id = :id")
	Page<TipoServico> findByTipoServico(Long id, Pageable pageable);
}
