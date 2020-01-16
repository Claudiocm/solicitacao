package com.solicitacao.sv.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.solicitacao.sv.dominio.Chamado;
import com.solicitacao.sv.service.ChamadoImplements;
import com.solicitacao.sv.service.ChamadoService;

@Component
public class StringToChamadoConverter implements Converter<String, Chamado> {
	@Autowired
	private ChamadoImplements service;

	@Override
	public Chamado convert(String text) {
		if (text.isEmpty()) {
			return null;
		}
		Long id = Long.valueOf(text);
		return service.buscarPorId(id);
	}
}
