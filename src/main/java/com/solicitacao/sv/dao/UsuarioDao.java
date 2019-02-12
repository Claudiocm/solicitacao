package com.solicitacao.sv.dao;

import java.util.List;

import com.solicitacao.sv.dominio.Usuario;

public interface UsuarioDao {
	void save(Usuario usuario);

	void update(Usuario usuario);

	void delete(Long id);

	Usuario findById(Long id);

	List<Usuario> findAll();

	List<Usuario> findByNome(String nome);

	List<Usuario> findByLogin(String login);
}
