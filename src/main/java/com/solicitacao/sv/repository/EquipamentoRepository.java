package com.solicitacao.sv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.solicitacao.sv.dominio.Equipamento;

public interface EquipamentoRepository extends JpaRepository<Equipamento, Long>{
		
	@Query("Select e from Equipamento e where e.eqDescricao like :descricao%")
	List<Equipamento> findByDescricao(String descricao);
	
	@Query("Select e from Equipamento e where e.eqSeriebp like :serie%")
	List<Equipamento> findBySerie(String serie);
}
