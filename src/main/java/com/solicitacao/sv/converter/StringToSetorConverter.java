package com.solicitacao.sv.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.solicitacao.sv.dominio.Setor;
import com.solicitacao.sv.service.SetorService;

@Component
public class StringToSetorConverter implements Converter<String, Setor> {
	@Autowired
	private SetorService service;

	@Override
	public Setor convert(String text) {
		if (text.isEmpty()) {
			return null;
		}
		Long id = Long.valueOf(text);
		return service.buscarPorId(id);
	}
}
