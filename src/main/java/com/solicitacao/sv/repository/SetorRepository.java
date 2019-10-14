package com.solicitacao.sv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.solicitacao.sv.dominio.Setor;

public interface SetorRepository extends JpaRepository<Setor, Long> {
	@Query("Select s from Setor s where s.setNome like :nome%")
	List<Setor> findByName(String nome);
}
