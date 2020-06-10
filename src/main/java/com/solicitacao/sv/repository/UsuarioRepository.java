package com.solicitacao.sv.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.solicitacao.sv.dominio.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	@Query("select u from Usuario u where u.email like :email%")
	Usuario findByEmail(String email);
	
	@Query("select distinct u from Usuario u join u.perfis p "
			+ "where u.email like :search% OR p.desc like :search%")
	Page<Usuario> findByEmailOrPerfil(String search, Pageable pageable);

	@Query("select u from Usuario u join u.perfis p where u.id = :usuarioId AND p.id IN :perfisId")
	Optional<Usuario> findByIdAndPerfis(Long usuarioId, Long[] perfisId);

	@Query("select u from Usuario u where u.email like :email AND u.ativo = true")
	Optional<Usuario> findByEmailAndAtivo(String email);
	
	@Query("select u from Usuario u where u.email like :email%")
	List<Usuario> buscaPorEmail(String email);
}
