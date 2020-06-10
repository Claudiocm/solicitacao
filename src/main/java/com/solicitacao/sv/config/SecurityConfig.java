package com.solicitacao.sv.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
				.antMatchers("/webjars/**", "/css/**", "/image/**", "/js/**").permitAll().antMatchers("/", "/home")
				.permitAll()
				.antMatchers("/usuarios/novo/cadastro", "/usuarios/cadastro/realizado",
						"/usuarios/cadastro/solicitante/salvar")
				.permitAll().antMatchers("/usuarios/confirmacao/cadastro").permitAll().antMatchers("/usuarios/p/**")
				.permitAll()

				// acessos privados admin
				.antMatchers("/usuarios/editar/senha", "/usuarios/confirmar/senha")
				.hasAnyAuthority(SOLICITANTE, TECNICO, ADMIN)
				.antMatchers("/usuarios/**").hasAuthority(ADMIN)

				// acessos privados solicitantes
				.antMatchers("/setores").hasAnyAuthority(TECNICO, ADMIN)
				
				// acessos privados tecnicos
				.antMatchers("/tecnicos/**").hasAnyAuthority(TECNICO, ADMIN)

				// acessos privados chamados
				.antMatchers("/chamados/**").hasAnyAuthority(TECNICO, ADMIN)
				.antMatchers("/chamados/cadastrar", "/chamados/salvar", "/chamados/editar", "/chamados/buscar").hasAnyAuthority(SOLICITANTE, TECNICO, ADMIN)

				// acessos privados tecnicos
				.antMatchers("/equipamentos/**").hasAnyAuthority(TECNICO, ADMIN)

				// acessos privados tecnicos
				.antMatchers("/tipo-equipamento/**").hasAnyAuthority(TECNICO, ADMIN)
				
				// acessos privados servicos
				.antMatchers("/servicos/datatables/server/equipamento/*").hasAnyAuthority(TECNICO, ADMIN)
				.antMatchers("/servicos/**").hasAnyAuthority(ADMIN, TECNICO)
				
				// acessos privados solicitantes
				.antMatchers("/solicitantes/**").hasAuthority(SOLICITANTE)
				.antMatchers("/solicitantes/dados").hasAnyAuthority(SOLICITANTE, ADMIN)

				.anyRequest().authenticated().and().formLogin().loginPage("/login").defaultSuccessUrl("/", true)
				.failureUrl("/login-error").permitAll().and().logout().logoutSuccessUrl("/").and().exceptionHandling()
				.accessDeniedPage("/acesso-negado").and().rememberMe();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(service).passwordEncoder(new BCryptPasswordEncoder());
	}

}
