package com.solicitacao.sv.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.solicitacao.sv.dominio.PerfilTipo;
import com.solicitacao.sv.service.UsuarioServiceImpl;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String ADMIN = PerfilTipo.ADMIN.getDesc();
	private static final String TECNICO = PerfilTipo.TECNICO.getDesc();
	private static final String SOLICITANTE = PerfilTipo.SOLICITANTE.getDesc();

	@Autowired
	private UsuarioServiceImpl service;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
				// acessos públicos liberados
				.antMatchers("/webjars/**", "/css/**", "/image/**", "/js/**").permitAll()
				.antMatchers("/", "/home", "/expired").permitAll()
				.antMatchers("/usuarios/novo/cadastro", "/usuarios/cadastro/realizado",
						"/usuarios/cadastro/solicitante/salvar")
				.permitAll().antMatchers("/usuarios/confirmacao/cadastro").permitAll().antMatchers("/usuarios/p/**")
				.permitAll()

				.antMatchers("/usuarios/editar/senha", "/usuarios/confirmar/senha")
				.hasAnyAuthority(TECNICO, ADMIN, SOLICITANTE).antMatchers("/usuarios/**").hasAuthority(ADMIN)

				// acessos privados solicitantes
				.antMatchers("/setores").hasAnyAuthority(TECNICO, ADMIN)

				// acessos privados tecnicos
				.antMatchers("/tecnicos/**").hasAnyAuthority(TECNICO, ADMIN)

				// acessos privados chamados
				.antMatchers("/chamados/cadastrar", "/chamados/salvar", "/chamados/editar", "/chamados/buscar")
				.hasAnyAuthority(SOLICITANTE, TECNICO, ADMIN).antMatchers("/chamados/**")
				.hasAnyAuthority(TECNICO, ADMIN)

				// acessos privados tecnicos
				.antMatchers("/equipamentos/**").hasAnyAuthority(TECNICO, ADMIN)

				// acessos privados tecnicos
				.antMatchers("/tipo-equipamento/**").hasAnyAuthority(TECNICO, ADMIN)

				// acessos privados cargos
				.antMatchers("/cargos/**").hasAnyAuthority(TECNICO, ADMIN)
				
				// acessos privados servicos
				.antMatchers("/servicos/datatables/server/equipamento/*").hasAnyAuthority(TECNICO, ADMIN)
				.antMatchers("/servicos/**").hasAnyAuthority(ADMIN, TECNICO)

				// acessos privados solicitantes
				.antMatchers("/solicitantes/**").hasAuthority(SOLICITANTE).antMatchers("/solicitantes/dados")
				.hasAnyAuthority(SOLICITANTE, ADMIN)

				.anyRequest().authenticated()
				.and()
				.formLogin().loginPage("/login").defaultSuccessUrl("/", true)
				.failureUrl("/login-error").permitAll()
				.and()
				.logout().logoutSuccessUrl("/")
				.deleteCookies("JSESSIONID")
				.and().exceptionHandling()
				.accessDeniedPage("/acesso-negado")
				.and().rememberMe();
		
		// controle de sessão de usuário
		http.sessionManagement()
		.maximumSessions(1)
		.expiredUrl("/expired")
		.maxSessionsPreventsLogin(false)
		.sessionRegistry(sessionRegistry());
		
		http.sessionManagement()
		.sessionFixation()
		.newSession()
		.sessionAuthenticationStrategy(sessionAuthStrategy());

	}

	public SessionAuthenticationStrategy sessionAuthStrategy() {
		return new RegisterSessionAuthenticationStrategy(sessionRegistry());
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(service).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}

	@Bean
	public ServletListenerRegistrationBean<?> servletListenterRegistrationBean() {
		return new ServletListenerRegistrationBean<>(new HttpSessionEventPublisher());
	}
}
