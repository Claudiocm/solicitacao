package com.solicitacao.sv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.solicitacao.sv.dominio.Solicitante;

public interface SolicitanteRepository extends JpaRepository<Solicitante, Long> {
	@Query("select s from Solicitante s where s.usuario.email like :email")
	<Optional> Solicitante findByUsuarioEmail(String email);
    @Query("select s from Solicitante s where s.nome like :nome%")
	List<Solicitante> findByBuscarPorNome(String nome);

}
