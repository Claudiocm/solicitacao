package com.solicitacao.sv.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import com.solicitacao.sv.datatables.Datatables;
import com.solicitacao.sv.datatables.DatatablesColunas;
import com.solicitacao.sv.dominio.Perfil;
import com.solicitacao.sv.dominio.PerfilTipo;
import com.solicitacao.sv.dominio.Usuario;
import com.solicitacao.sv.exception.AcessoNegadoException;
import com.solicitacao.sv.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {
	@Autowired
	private UsuarioRepository dao;
	@Autowired
	private Datatables datatables;
	@Autowired
	private EmailService emailService;

	@Override
	public void salvar(Usuario usuario) {
		dao.save(usuario);
	}

	@Override
	public void editar(Usuario usuario) {
		Usuario u = dao.findById(usuario.getId()).get();
		u.setId(usuario.getId());
		u.setSetor(usuario.getSetor());
		u.setUsuSenha(usuario.getUsuSenha());
		u.setEmail(usuario.getEmail());
		u.setCodigoVerificador(usuario.getCodigoVerificador());
		if (!usuario.getPerfis().isEmpty()) {
			u.getPerfis().addAll(usuario.getPerfis());
		}
		u.setPerfis(usuario.getPerfis());
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

	@Transactional(readOnly = true)
	public Map<String, Object> buscarTodos(HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.USUARIOS);
		Page<Usuario> page = datatables.getSearch().isEmpty() ? dao.findAll(datatables.getPageable())
				: dao.findByEmailOrPerfil(datatables.getSearch(), datatables.getPageable());
		return datatables.getResponse(page);
	}

	@Override
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
		return new User(usuario.getEmail(), usuario.getUsuSenha(),
				AuthorityUtils.createAuthorityList(getAtuthorities(usuario.getPerfis())));
	}

	private String[] getAtuthorities(List<Perfil> perfis) {
		String[] authorities = new String[perfis.size()];
		for (int i = 0; i < perfis.size(); i++) {
			authorities[i] = perfis.get(i).getDesc();
		}
		return authorities;
	}

	@Transactional(readOnly = false)
	public void pedidoRedefinicaoDeSenha(String email) throws MessagingException {
		Usuario usuario = buscarPorEmailEAtivo(email)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario " + email + " não encontrado."));
		;

		String verificador = RandomStringUtils.randomAlphanumeric(6);

		usuario.setCodigoVerificador(verificador);

		emailService.enviarPedidoRedefinicaoSenha(email, verificador);
	}

	@Transactional(readOnly = false)
	public void salvarCadastroSolicitante(Usuario usuario) throws MessagingException {
		String crypt = new BCryptPasswordEncoder().encode(usuario.getUsuSenha());
		usuario.setUsuSenha(crypt);
		usuario.addPerfil(PerfilTipo.SOLICITANTE);
		dao.save(usuario);

		emailDeConfirmacaoDeCadastro(usuario.getEmail());
	}

	@Transactional(readOnly = false)
	public void salvarCadastroTecnico(Usuario usuario) throws MessagingException {
		String crypt = new BCryptPasswordEncoder().encode(usuario.getUsuSenha());
		usuario.setUsuSenha(crypt);
		usuario.addPerfil(PerfilTipo.TECNICO);
		dao.save(usuario);

		emailDeConfirmacaoDeCadastro(usuario.getEmail());
	}

	@Transactional(readOnly = true)
	public Optional<Usuario> buscarPorEmailEAtivo(String email) {

		return dao.findByEmailAndAtivo(email);
	}

	public void emailDeConfirmacaoDeCadastro(String email) throws MessagingException {
		String codigo = Base64Utils.encodeToString(email.getBytes());
		emailService.enviarPedidoDeConfirmacaoDeCadastro(email, codigo);
	}

	@Transactional(readOnly = false)
	public void ativarCadastroSolicitante(String codigo) {
		String email = new String(Base64Utils.decodeFromString(codigo));
		Usuario usuario = buscarPorEmail(email);
		if (usuario.hasNotId()) {
			throw new AcessoNegadoException(
					"Não foi possível ativar seu cadastro. Entre em " + "contato com o suporte.");
		}
		usuario.setAtivo(true);
	}

	@Transactional(readOnly = false)
	public void ativarCadastroTecnico(String codigo) {
		String email = new String(Base64Utils.decodeFromString(codigo));
		Usuario usuario = buscarPorEmail(email);
		if (usuario.hasNotId()) {
			throw new AcessoNegadoException(
					"Não foi possível ativar seu cadastro. Entre em " + "contato com o suporte.");
		}
		usuario.setAtivo(true);
	}

}
