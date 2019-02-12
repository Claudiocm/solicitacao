package com.solicitacao.sv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.solicitacao.sv.dominio"})
public class SolicitacaoSvApplication {

	public static void main(String[] args) {
		SpringApplication.run(SolicitacaoSvApplication.class, args);
	}

}

