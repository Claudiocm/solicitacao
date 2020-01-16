package com.solicitacao.sv.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.solicitacao.sv.dominio.TipoEquipamento;


public interface TipoEquipamentoRepository extends JpaRepository<TipoEquipamento, Long> {
	@Query("select t from TipoEquipamento t where t.id = :id")
	Optional<TipoEquipamento> findByTipoEquipamentoId(Long id);

	@Query("select t.descricao from TipoEquipamento t where t.descricao like :modelo%")
	Optional<TipoEquipamento> findByTipoEquipamentoModelo(String modelo);

	@Query("select t.descricao from TipoEquipamento t where t.descricao like :termo%")
	List<String> findTipoEquipamentoByTermo(String termo);

	@Query("select t from TipoEquipamento t where t.descricao like :search%")
	Page<TipoEquipamento> findAllByDescricao(String search, Pageable pageable);

	@Query("select t from TipoEquipamento t where t.descricao IN :nomes")
	Set<TipoEquipamento> findByNomes(String[] nomes);

	@Query("select t from TipoEquipamento t where t.id = :id")
	Page<TipoEquipamento> findByTipoEquipamento(Long id, Pageable pageable);

}
