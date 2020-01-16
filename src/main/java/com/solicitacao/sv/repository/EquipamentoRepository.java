package com.solicitacao.sv.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.solicitacao.sv.dominio.Equipamento;

public interface EquipamentoRepository extends JpaRepository<Equipamento, Long>{
		
	@Query("select distinct e from Equipamento e join e.servicos s where s.serNome like :descricao")
	List<Equipamento> findByDescricao(String descricao);
	
	@Query("Select e from Equipamento e where e.eqSeriebp like :serie%")
	List<Equipamento> findBySerie(String serie);

	@Query("select e from Equipamento e where e.eqModelo like :search%")
	Page<Equipamento> findAllByModelo(String search, Pageable pageable);

	@Query("select distinct e from Equipamento e join e.servicos s where s.serNome like :search")
	Page<Equipamento> findAllByTitulo(String search, Pageable pageable);
}

