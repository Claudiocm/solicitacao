package com.solicitacao.sv.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import com.solicitacao.sv.dominio.TipoEquipamento;
import com.solicitacao.sv.service.TipoEquipamentoImpl;

public class StringToTipoequipamentoConverter implements Converter<String, TipoEquipamento> {

	@Autowired
	private TipoEquipamentoImpl service;

	@Override
	public TipoEquipamento convert(String text) {
		if (text.isEmpty()) {
			return null;
		}
		Long id = Long.valueOf(text);
		return service.buscarPorId(id);
	}
}
