package com.solicitacao.sv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
/*import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;*/
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solicitacao.sv.dominio.Perfil;
import com.solicitacao.sv.dominio.Usuario;
import com.solicitacao.sv.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService /*UserDetailsService */{
	@Autowired
	private UsuarioRepository dao;

	@Override
	public void salvar(Usuario usuario) {
		dao.save(usuario);
	}

	@Override
	public void editar(Usuario usuario) {
		Usuario u = dao.findById(usuario.getId()).get();
		u.setId(usuario.getId());
		u.setSetor(usuario.getSetor());
		u.setUsuLogin(usuario.getUsuLogin());
		u.setUsuNome(usuario.getUsuNome());
		u.setUsuSenha(usuario.getUsuSenha());
		u.setEmail(usuario.getEmail());
		u.setCodigoVerificador(usuario.getCodigoVerificador());
		if (!usuario.getPerfis().isEmpty()) {
			u.getPerfis().addAll(usuario.getPerfis());
		}
	}

	@Override
	public void excluir(Long id) {
		dao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario buscarPorId(Long id) {
		return dao.findById(id).get();
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

	/*@Override
	public void salvarUsuario(Usuario usuario) {
		String crypt = new BCryptPasswordEncoder().encode(usuario.getUsuSenha());
		usuario.setUsuSenha(crypt);

		dao.save(usuario);
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario buscarPorIdEPerfis(Long usuarioId, Long[] perfisId) {

		return dao.findByIdAndPerfis(usuarioId, perfisId)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário inexistente!"));
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario buscarPorEmail(String email) {
		return dao.findByEmail(email);
	}

	public static boolean isSenhaCorreta(String senhaDigitada, String senhaArmazenada) {

		return new BCryptPasswordEncoder().matches(senhaDigitada, senhaArmazenada);
	}

	@Override
	public void alterarSenha(Usuario usuario, String senha) {
		usuario.setUsuSenha(new BCryptPasswordEncoder().encode(senha));
		dao.save(usuario);
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = buscarPorEmail(username);
		return new User(
				usuario.getEmail(), 
				usuario.getUsuSenha(),
				AuthorityUtils.createAuthorityList(
						getAtuthorities(usuario.getPerfis())));
	}*/

	private String[] getAtuthorities(List<Perfil> perfis) {
		String[] authorities = new String[perfis.size()];
		for (int i = 0; i < perfis.size(); i++) {
			authorities[i] = perfis.get(i).getDesc();
		}
		return authorities;
	}
}
