package com.solicitacao.sv.service;

import java.util.List;

import com.solicitacao.sv.dominio.Usuario;

public interface UsuarioService {
	public void salvar(Usuario usuario);

	public void editar(Usuario usuario);

	public void excluir(Long id);

	public Usuario buscarPorId(Long id);

	public List<Usuario> buscarTodos();

	public void salvarUsuario(Usuario usuario);

	public Usuario buscarPorIdEPerfis(Long usuarioId, Long[] perfisId);

	public Usuario buscarPorEmail(String name);

	public static boolean isSenhaCorreta(String s3, String usuSenha) {
		return false;
	}

	public void alterarSenha(Usuario u, String s1);

}
