package com.solicitacao.sv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import com.solicitacao.sv.service.EmailService;

@SpringBootApplication
@EntityScan(basePackages = { "com.solicitacao.sv.dominio" })
public class SolicitacaoSvApplication implements CommandLineRunner {

	public static void main(String[] args) {
		// System.out.println(new BCryptPasswordEncoder().encode("123456"));
		SpringApplication.run(SolicitacaoSvApplication.class, args);
	}

	@Autowired
	EmailService service;

	@Override
	public void run(String... args) throws Exception {
		//service.enviarPedidoDeConfirmacaoDeCadastro("claudio.c.matos@gmail.com", "a2018c44");
	}

}
