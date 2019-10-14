package com.solicitacao.sv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.solicitacao.sv.dominio.Equipamento;
import com.solicitacao.sv.dominio.Servico;

public interface ServicoRepository extends JpaRepository<Servico, Long> {

	@Query("Select s from Servico s where s.serNome like :nome%")
	List<Servico> buscarPorNome(String nome);

	@Query("Select s from Servico s where s.equipamento.id = :id")
	List<Servico> buscarPorEquipamento(Long id);

	@Query("select s from Servico s where s.equipamento.id = :id")
	List<Servico> buscarServicoId(Long id);
    
	Iterable<Servico> findByEquipamento(Equipamento e);
}
