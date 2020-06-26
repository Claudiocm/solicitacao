package com.solicitacao.sv.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.solicitacao.sv.dominio.Servico;

public interface ServicoRepository extends JpaRepository<Servico, Long> {

	@Query("Select s from Servico s where s.serNome like :nome%")
	List<Servico> buscarPorNome(String nome);

	@Query("select s from Servico s join s.equipamentos e where e.id = :id")
	List<Servico> findByIdEquipamento(Long id);
	
	@Query("select s from Servico s join s.equipamentos e where e.id = :id")
	List<Servico> buscarServicoId(Long id);

	@Query("select s from Servico s where s.serNome like :search%")
	Page<Servico> findAllByTitulo(String search, Pageable pageable);

	@Query("select s from Servico s join s.equipamentos e where e.id = :id") 
	Page<Servico> findByIdEquipamento(Long id, Pageable pageable);

	@Query("select s from Servico s where s.serNome IN :titulos")
	Set<Servico> findByTitulos(String[] titulos);

}
