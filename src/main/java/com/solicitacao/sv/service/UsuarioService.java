package com.solicitacao.sv.service;

import java.util.List;

import com.solicitacao.sv.dominio.Usuario;

public interface UsuarioService {
	public void salvar(Usuario usuario);

	public void editar(Usuario usuario);

	public void excluir(Long id);

	public Usuario buscarPorId(Long id);

	public List<Usuario> buscarTodos();

	public List<Usuario> buscarPorNome(String nome);

	public List<Usuario> buscarPorLogin(String login);

	boolean usuarioExiste(String nome);
}
