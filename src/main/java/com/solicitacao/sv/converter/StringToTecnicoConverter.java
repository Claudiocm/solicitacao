package com.solicitacao.sv.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.solicitacao.sv.dominio.Tecnico;
import com.solicitacao.sv.service.TecnicoService;

@Component
public class StringToTecnicoConverter implements Converter<String, Tecnico> {
	@Autowired
	private TecnicoService service;

	@Override
	public Tecnico convert(String text) {
		if (text.isEmpty()) {
			return null;
		}
		Long id = Long.valueOf(text);
		return service.buscarPorId(id);
	}

}
