package com.solicitacao.sv.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.solicitacao.sv.dominio.Usuario;

@Repository
public class UsuarioImpl extends AbstractDao<Usuario, Long>  implements UsuarioDao{

	@Override
	public List<Usuario> findByNome(String nome) {
		return  createQuery("select u from Usuario u where u.usu_nome = ?1 ", nome);
	}

	@Override
	public List<Usuario> findByLogin(String login) {
		return createQuery("select u from Usuario u where u.usu_login = ?1 ",login);
	}

}
