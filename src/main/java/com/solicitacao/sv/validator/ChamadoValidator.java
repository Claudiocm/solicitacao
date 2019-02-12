package com.solicitacao.sv.validator;

import java.time.LocalDate;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.solicitacao.sv.dominio.Chamado;

public class ChamadoValidator implements Validator {
	@Override
	public boolean supports(Class<?> clazz) {
		return Chamado.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Chamado c = (Chamado) target;
        LocalDate entrada = c.getChDataAbertura();
		
		if (c.getChDataFechamento() != null) {
			if (c.getChDataFechamento().isBefore(entrada)) {
				errors.rejectValue("chDataFechamento", "PosteriorDataAbertura.chamado.chDataFechamento");
			}
		}
	}
}
