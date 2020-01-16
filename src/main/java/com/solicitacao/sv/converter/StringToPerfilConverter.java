package com.solicitacao.sv.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToPerfilConverter implements Converter<String, Long> {
    
	@Override
	public Long convert(String text) {

		text = text.trim();
		if (text.matches("[0-9]+")) {
			Long id = Long.valueOf(text);
			return id;
		}

		return null;
	}
}
