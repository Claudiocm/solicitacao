package com.solicitacao.sv.auditoria;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class AuditorAwareImpl implements AuditorAware<User>{

	@Override
	public Optional<User> getCurrentAuditor() {
		
	return Optional.ofNullable(SecurityContextHolder.getContext())
				  .map(SecurityContext::getAuthentication)
				  .filter(Authentication::isAuthenticated)
				  .map(Authentication::getPrincipal)
				  .map(User.class::cast);
	}
  
}
