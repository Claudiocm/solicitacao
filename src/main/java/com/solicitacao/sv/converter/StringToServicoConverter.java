package com.solicitacao.sv.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.solicitacao.sv.dominio.Servico;
import com.solicitacao.sv.service.ServicoService;

@Component
public class StringToServicoConverter implements Converter<String, Servico> {

	@Autowired
	private ServicoService service;
	
	@Override
	public Servico convert(String text) {
		if (text.isEmpty()) {
			return null;
		}
		Long id = Long.valueOf(text);
		return service.buscarPorId(id);
	}
}
