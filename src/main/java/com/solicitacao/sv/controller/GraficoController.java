package com.solicitacao.sv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.solicitacao.sv.dominio.Chamado;
import com.solicitacao.sv.service.ChamadoImplements;

@RestController
@RequestMapping(value = "/graficos")
public class GraficoController {

	@Autowired
	private ChamadoImplements chService;
	
	
	@ResponseBody
	@GetMapping("/chamados")
	public ResponseEntity<Iterable<Chamado>> todos() {
		try {
			return new ResponseEntity<Iterable<Chamado>>(chService.todos(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Iterable<Chamado>>(HttpStatus.BAD_REQUEST);
		}
	}
}
