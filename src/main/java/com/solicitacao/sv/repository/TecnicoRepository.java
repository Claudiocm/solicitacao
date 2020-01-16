package com.solicitacao.sv.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.solicitacao.sv.dominio.Tecnico;

public interface TecnicoRepository extends JpaRepository<Tecnico, Long>{
	@Query("select t from Tecnico t where t.tecNome like :nome%")
	List<Tecnico> findByName(String nome);
	@Query("Select t from Tecnico t where t.id = :id")
	Optional<Tecnico> findByUsuarioId(Long id);
	@Query("select t from Tecnico t where t.usuario.email like :email")
	Optional<Tecnico> findByUsuarioEmail(String email);
}
