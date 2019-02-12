package com.solicitacao.sv.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.solicitacao.sv.dominio.Equipamento;
import com.solicitacao.sv.service.EquipamentoService;

@Component
public class StringToEquipamentoConverter implements Converter<String, Equipamento> {
	
	@Autowired
	private EquipamentoService service;

	@Override
	public Equipamento convert(String text) {
		if (text.isEmpty()) {
			return null;
		}
		Long id = Long.valueOf(text);
		return service.buscarPorId(id);
	}

}
