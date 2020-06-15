package com.solicitacao.sv.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.solicitacao.sv.auditoria.AuditorAwareImpl;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class PersistenceConfig implements WebMvcConfigurer {
	@Bean
	public AuditorAware<User> auditorAware() {
		return new AuditorAwareImpl();
	}

}
