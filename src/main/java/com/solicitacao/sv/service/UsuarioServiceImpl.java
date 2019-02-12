package com.solicitacao.sv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solicitacao.sv.dao.UsuarioDao;
import com.solicitacao.sv.dominio.Usuario;

@Service
@Transactional(readOnly = false)
public class UsuarioServiceImpl implements UsuarioService{
    @Autowired
    private UsuarioDao dao;
    
	@Override
	public void salvar(Usuario usuario) {
		dao.save(usuario);
	}

	@Override
	public void editar(Usuario usuario) {
		dao.update(usuario);
	}

	@Override
	public void excluir(Long id) {
		dao.delete(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario buscarPorId(Long id) {
		return dao.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> buscarTodos() {
		return dao.findAll();
	}

	@Override
	public List<Usuario> buscarPorNome(String nome) {
		return dao.findByNome(nome);
	}

	@Override
	public List<Usuario> buscarPorLogin(String login) {
		return dao.findByLogin(login);
	}

	@Override
	public boolean usuarioExiste(String nome) {
		if (buscarPorNome(nome).isEmpty()) {
			return false;
		}
		return true;
	}


}
