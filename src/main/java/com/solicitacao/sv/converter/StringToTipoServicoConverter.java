package com.solicitacao.sv.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import com.solicitacao.sv.dominio.TipoServico;
import com.solicitacao.sv.service.TipoServicoImpl;

public class StringToTipoServicoConverter implements Converter<String, TipoServico> {

	@Autowired
	private TipoServicoImpl service;

	@Override
	public TipoServico convert(String text) {
		if (text.isEmpty()) {
			return null;
		}
		Long id = Long.valueOf(text);
		return service.buscarPorId(id);
	}
}
