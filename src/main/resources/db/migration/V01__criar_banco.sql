CREATE TABLE `cargo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(60) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_91fcfd99kg1af67rjv08heu1q` (`nome`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `` (`id`,`nome`) VALUES (1,'Analista de Sistemas');
INSERT INTO `` (`id`,`nome`) VALUES (6,'Coordenador de Setor');
INSERT INTO `` (`id`,`nome`) VALUES (3,'Estagiário de Manutenção');
INSERT INTO `` (`id`,`nome`) VALUES (5,'Estagiário de Rede');
INSERT INTO `` (`id`,`nome`) VALUES (4,'Estagiário de Sistemas');
INSERT INTO `` (`id`,`nome`) VALUES (2,'Programador de Sistemas');

CREATE TABLE `tipo_equipamento` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `` (`id`,`eq_descricao`,`eq_modelo`,`eq_seriebp`,`tipo_equipamento`) VALUES (1,'Computador de Mesa DEll','Dell Optplex 3060','a12b12c',1);
INSERT INTO `` (`id`,`eq_descricao`,`eq_modelo`,`eq_seriebp`,`tipo_equipamento`) VALUES (2,'Notebook Dell  64 bits','Inspira 3020','ADF123SDF',2);

CREATE TABLE `tipo_servico` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(80) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `` (`id`,`descricao`) VALUES (1,'Analise de Sistema Operacional');
INSERT INTO `` (`id`,`descricao`) VALUES (2,'Configuração de Sistema');
INSERT INTO `` (`id`,`descricao`) VALUES (3,'Configuração de Rede');
INSERT INTO `` (`id`,`descricao`) VALUES (4,'Desintalação de Equipamento');
INSERT INTO `` (`id`,`descricao`) VALUES (5,'Desinstalação de Software');
INSERT INTO `` (`id`,`descricao`) VALUES (6,'Limpeza de Hardware');
INSERT INTO `` (`id`,`descricao`) VALUES (7,'Limpeza de Cache de Sistema');
INSERT INTO `` (`id`,`descricao`) VALUES (8,'Instalação de Equipamento');
INSERT INTO `` (`id`,`descricao`) VALUES (9,'Instalação de Software');
INSERT INTO `` (`id`,`descricao`) VALUES (10,'Troca de Peça');

CREATE TABLE `equipamento` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `eq_descricao` varchar(80) COLLATE utf8_bin DEFAULT NULL,
  `eq_modelo` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `eq_seriebp` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `tipo_equipamento` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tipoequipamento` (`tipo_equipamento`),
  CONSTRAINT `fk_tipoequipamento` FOREIGN KEY (`tipo_equipamento`) REFERENCES `tipo_equipamento` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `` (`id`,`eq_descricao`,`eq_modelo`,`eq_seriebp`,`tipo_equipamento`) VALUES (1,'Computador de Mesa DEll','Dell Optplex 3060','a12b12c',1);
INSERT INTO `` (`id`,`eq_descricao`,`eq_modelo`,`eq_seriebp`,`tipo_equipamento`) VALUES (2,'Notebook Dell  64 bits','Inspira 3020','ADF123SDF',2);


CREATE TABLE `perfis` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_46fwiur1v4jn08eg093a3bckv` (`descricao`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `` (`id`,`descricao`) VALUES (1,'ADMIN');
INSERT INTO `` (`id`,`descricao`) VALUES (3,'SOLICITANTE');
INSERT INTO `` (`id`,`descricao`) VALUES (2,'TECNICO');
INSERT INTO `` (`id`,`descricao`) VALUES (4,'USUARIO');

CREATE TABLE `servico` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ser_nome` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `tipo_servico` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tiposervico` (`tipo_servico`),
  CONSTRAINT `fk_tiposervico` FOREIGN KEY (`tipo_servico`) REFERENCES `tipo_servico` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `` (`id`,`ser_nome`,`tipo_servico`) VALUES (1,'Limpeza de memória',6);
INSERT INTO `` (`id`,`ser_nome`,`tipo_servico`) VALUES (2,'Instalação de SIAP',9);
INSERT INTO `` (`id`,`ser_nome`,`tipo_servico`) VALUES (3,'Instalação de Programa',9);
INSERT INTO `` (`id`,`ser_nome`,`tipo_servico`) VALUES (4,'Limpeza de Sistema',7);
INSERT INTO `` (`id`,`ser_nome`,`tipo_servico`) VALUES (5,'Instalação de impressora',8);
INSERT INTO `` (`id`,`ser_nome`,`tipo_servico`) VALUES (6,'Instalação de programa de impressora',9);
INSERT INTO `` (`id`,`ser_nome`,`tipo_servico`) VALUES (7,'Instalação de computador',8);
INSERT INTO `` (`id`,`ser_nome`,`tipo_servico`) VALUES (8,'Instalação de Monitor',8);
INSERT INTO `` (`id`,`ser_nome`,`tipo_servico`) VALUES (9,'Instalação de Estabilizador',8);
INSERT INTO `` (`id`,`ser_nome`,`tipo_servico`) VALUES (10,'Instalação de Programas',9);

CREATE TABLE `setor` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `set_nome` varchar(60) COLLATE utf8_bin NOT NULL,
  `set_ramal` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `` (`id`,`set_nome`,`set_ramal`) VALUES (1,'ADMIN PAÇO',NULL);
INSERT INTO `` (`id`,`set_nome`,`set_ramal`) VALUES (2,'SEAD',NULL);
INSERT INTO `` (`id`,`set_nome`,`set_ramal`) VALUES (3,'SEGOV',NULL);
INSERT INTO `` (`id`,`set_nome`,`set_ramal`) VALUES (4,'SEFAZ',NULL);
INSERT INTO `` (`id`,`set_nome`,`set_ramal`) VALUES (5,'RECURSOS HUMANOS',NULL);
INSERT INTO `` (`id`,`set_nome`,`set_ramal`) VALUES (6,'RENDAS',NULL);
INSERT INTO `` (`id`,`set_nome`,`set_ramal`) VALUES (7,'IPTU',NULL);
INSERT INTO `` (`id`,`set_nome`,`set_ramal`) VALUES (8,'DINF',NULL);
INSERT INTO `` (`id`,`set_nome`,`set_ramal`) VALUES (9,'DIFIN',NULL);
INSERT INTO `` (`id`,`set_nome`,`set_ramal`) VALUES (10,'CONTRATOS',NULL);
INSERT INTO `` (`id`,`set_nome`,`set_ramal`) VALUES (11,'MINISTERIO PUBLICO',NULL);
INSERT INTO `` (`id`,`set_nome`,`set_ramal`) VALUES (12,'DIVIDA ATIVA',NULL);
INSERT INTO `` (`id`,`set_nome`,`set_ramal`) VALUES (13,'CAC',NULL);
INSERT INTO `` (`id`,`set_nome`,`set_ramal`) VALUES (14,'SEJUR',NULL);
INSERT INTO `` (`id`,`set_nome`,`set_ramal`) VALUES (15,'SEPES',NULL);

CREATE TABLE `equipamentos_tem_servicos` (
  `equipamento` bigint(20) NOT NULL,
  `servico` bigint(20) NOT NULL,
  PRIMARY KEY (`equipamento`,`servico`),
  KEY `fk_equipamento_idx` (`equipamento`),
  KEY `FKnm6347x31y1hifpm42eowqnp0` (`servico`),
  CONSTRAINT `FK6aq08sp1d0ne0otij0r80k4uu` FOREIGN KEY (`equipamento`) REFERENCES `equipamento` (`id`),
  CONSTRAINT `FKnm6347x31y1hifpm42eowqnp0` FOREIGN KEY (`servico`) REFERENCES `servico` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `usuario` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) COLLATE utf8_bin NOT NULL,
  `ativo` tinyint(1) NOT NULL,
  `codigo_verificador` varchar(6) COLLATE utf8_bin DEFAULT NULL,
  `usu_senha` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `setor` bigint(20) DEFAULT NULL,
  `criado_por` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `data_criada` datetime DEFAULT NULL,
  `modificado_por` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ultima_data_modificada` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_5171l57faosmj8myawaucatdw` (`email`),
  KEY `idx_usuario_email` (`email`),
  KEY `FKj7sw8sjqbcw8lsqj61vdeq1cj` (`setor`),
  CONSTRAINT `FKj7sw8sjqbcw8lsqj61vdeq1cj` FOREIGN KEY (`setor`) REFERENCES `setor` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `` (`id`,`email`,`ativo`,`codigo_verificador`,`usu_senha`,`setor`,`criado_por`,`data_criada`,`modificado_por`,`ultima_data_modificada`) VALUES (1,'claudio@saovicente.sp.gov.br',1,NULL,'$2a$10$bQlRqan4ZBSDrI4Gk0v3suzZ37fz3JiSabOWEDG.XHtI4M8IOvBUi',8,NULL,NULL,NULL,NULL);
INSERT INTO `` (`id`,`email`,`ativo`,`codigo_verificador`,`usu_senha`,`setor`,`criado_por`,`data_criada`,`modificado_por`,`ultima_data_modificada`) VALUES (2,'kelly@saovicente.sp.gov.br',1,NULL,'$2a$10$2Ylp/F.4StKZXF081QXrw.zlqQsY1Z7qmkmKCJ9JJdEccsS09l0JC',8,NULL,NULL,NULL,NULL);
INSERT INTO `` (`id`,`email`,`ativo`,`codigo_verificador`,`usu_senha`,`setor`,`criado_por`,`data_criada`,`modificado_por`,`ultima_data_modificada`) VALUES (3,'leticia@saovicente.sp.gov.br',1,NULL,'$2a$10$Njdg3V/c.yHZOWQJnn61gu/Qc1SrrO4wPelJgvQsPRfGMEXnSI4iy',8,NULL,NULL,NULL,NULL);

CREATE TABLE `usuarios_tem_perfis` (
  `usuario` bigint(20) NOT NULL,
  `perfil` bigint(20) NOT NULL,
  PRIMARY KEY (`usuario`,`perfil`),
  KEY `FKcm3a3slrf7hvvp652dg5yog8d` (`perfil`),
  CONSTRAINT `FK127w14fqytwue22d8xqohn7ew` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`id`),
  CONSTRAINT `FKcm3a3slrf7hvvp652dg5yog8d` FOREIGN KEY (`perfil`) REFERENCES `perfis` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `` (`usuario`,`perfil`) VALUES (1,1);
INSERT INTO `` (`usuario`,`perfil`) VALUES (3,1);
INSERT INTO `` (`usuario`,`perfil`) VALUES (2,2);

CREATE TABLE `setores_tem_usuarios` (
  `usuario` bigint(20) NOT NULL,
  `setor` bigint(20) NOT NULL,
  PRIMARY KEY (`usuario`,`setor`),
  KEY `FKq90y6flcl952boaijso9c1peg` (`setor`),
  CONSTRAINT `FK8k4heoe0e2glufbyftck1oveo` FOREIGN KEY (`usuario`) REFERENCES `setor` (`id`),
  CONSTRAINT `FKq90y6flcl952boaijso9c1peg` FOREIGN KEY (`setor`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `solicitantes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dt_cadastro` date NOT NULL,
  `nome` varchar(255) COLLATE utf8_bin NOT NULL,
  `setor` bigint(20) DEFAULT NULL,
  `usuario` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_jlhjf61i1d33cfripychtq0o0` (`nome`),
  KEY `usuario` (`usuario`),
  KEY `FK1qk0ikb7wg97fcrs779vhw8b5` (`setor`),
  CONSTRAINT `FK1qk0ikb7wg97fcrs779vhw8b5` FOREIGN KEY (`setor`) REFERENCES `setor` (`id`),
  CONSTRAINT `setor` FOREIGN KEY (`usuario`) REFERENCES `setor` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `usuario` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE `tecnico` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tec_nome` varchar(50) COLLATE utf8_bin NOT NULL,
  `cargo` bigint(20) DEFAULT NULL,
  `usuario` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_pvny8tx5iidxaxr6q27nci3oj` (`tec_nome`),
  KEY `fk_cargo` (`cargo`),
  KEY `fk_usuario` (`usuario`),
  CONSTRAINT `fk_cargo` FOREIGN KEY (`cargo`) REFERENCES `cargo` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `` (`id`,`tec_nome`,`cargo`,`usuario`) VALUES (1,'Kelly Cristina',1,2);
INSERT INTO `` (`id`,`tec_nome`,`cargo`,`usuario`) VALUES (2,'Gustavo',1,3);

CREATE TABLE `chamado` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `data_abertura` date NOT NULL,
  `data_fechamento` date DEFAULT NULL,
  `ch_ip` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ch_observacao` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ch_problema` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `tecnico` bigint(20) NOT NULL,
  `equipamento` bigint(20) DEFAULT NULL,
  `servico` bigint(20) DEFAULT NULL,
  `setor` bigint(20) DEFAULT NULL,
  `solicitante` bigint(20) DEFAULT NULL,
  `ch_prioridade` varchar(255) COLLATE utf8_bin NOT NULL,
  `ch_situacao` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_equipamento` (`equipamento`),
  KEY `fk_setor` (`setor`),
  KEY `fk_servico` (`servico`),
  KEY `fk_solicitante` (`solicitante`),
  KEY `fk_tecnico` (`tecnico`),
  CONSTRAINT `fk_equipamento` FOREIGN KEY (`equipamento`) REFERENCES `equipamento` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_servico` FOREIGN KEY (`servico`) REFERENCES `servico` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_setor` FOREIGN KEY (`setor`) REFERENCES `setor` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_solicitante` FOREIGN KEY (`solicitante`) REFERENCES `solicitantes` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tecnico` FOREIGN KEY (`tecnico`) REFERENCES `tecnico` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `tecnicos_tem_chamados` (
  `tecnico` bigint(20) NOT NULL,
  `chamado` bigint(20) NOT NULL,
  PRIMARY KEY (`tecnico`,`chamado`),
  KEY `tecnico_idx` (`tecnico`),
  KEY `chamado` (`chamado`),
  CONSTRAINT `chamado` FOREIGN KEY (`chamado`) REFERENCES `chamado` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tecnico` FOREIGN KEY (`tecnico`) REFERENCES `tecnico` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `` (`id`,`data_abertura`,`data_fechamento`,`ch_ip`,`ch_observacao`,`ch_problema`,`tecnico`,`equipamento`,`servico`,`setor`,`solicitante`,`ch_prioridade`,`ch_situacao`) VALUES (1,'2019-12-13',NULL,'','Checar Fonte.','PC não liga',1,1,NULL,8,NULL,'MEDIA','FECHADO');
INSERT INTO `` (`id`,`data_abertura`,`data_fechamento`,`ch_ip`,`ch_observacao`,`ch_problema`,`tecnico`,`equipamento`,`servico`,`setor`,`solicitante`,`ch_prioridade`,`ch_situacao`) VALUES (2,'2019-12-18',NULL,'10.171.1.69','Verificar fonte','PC não liga',1,1,NULL,2,NULL,'MEDIA','ENTREGUE');
INSERT INTO `` (`id`,`data_abertura`,`data_fechamento`,`ch_ip`,`ch_observacao`,`ch_problema`,`tecnico`,`equipamento`,`servico`,`setor`,`solicitante`,`ch_prioridade`,`ch_situacao`) VALUES (3,'2019-12-19',NULL,'10.171.1.69','Verificar fonte','PC não liga',2,1,NULL,2,NULL,'BAIXA','AGUARDANDO_PECA');
INSERT INTO `` (`id`,`data_abertura`,`data_fechamento`,`ch_ip`,`ch_observacao`,`ch_problema`,`tecnico`,`equipamento`,`servico`,`setor`,`solicitante`,`ch_prioridade`,`ch_situacao`) VALUES (4,'2019-12-19',NULL,'10.171.1.69','Verificar fonte','PC não liga',2,1,NULL,2,NULL,'BAIXA','AGUARDANDO_PECA');
INSERT INTO `` (`id`,`data_abertura`,`data_fechamento`,`ch_ip`,`ch_observacao`,`ch_problema`,`tecnico`,`equipamento`,`servico`,`setor`,`solicitante`,`ch_prioridade`,`ch_situacao`) VALUES (6,'2019-12-19',NULL,'10.171.1.69','Limpeza do pente de memória','PC não liga',1,1,NULL,1,NULL,'MEDIA','AGUARDANDO_PECA');
INSERT INTO `` (`id`,`data_abertura`,`data_fechamento`,`ch_ip`,`ch_observacao`,`ch_problema`,`tecnico`,`equipamento`,`servico`,`setor`,`solicitante`,`ch_prioridade`,`ch_situacao`) VALUES (7,'2019-12-19',NULL,'10.171.1.69','Verificar pente de memória','Tela azul',1,1,NULL,1,NULL,'MEDIA','AGUARDANDO_PECA');

CREATE TABLE `tecnicos_tem_equipamentos` (
  `tecnico` bigint(20) NOT NULL,
  `equipamento` bigint(20) NOT NULL,
  `id_tecnico` bigint(20) NOT NULL,
  PRIMARY KEY (`tecnico`,`equipamento`),
  KEY `FKkrwi352iotn7mdry1l9ldsrgh` (`id_tecnico`),
  KEY `FKhi4irfufmrxx0b3rqkfjajt5m` (`equipamento`),
  CONSTRAINT `FKa75pxr0t2d208lbb878fx00yq` FOREIGN KEY (`tecnico`) REFERENCES `tecnico` (`id`),
  CONSTRAINT `FKhi4irfufmrxx0b3rqkfjajt5m` FOREIGN KEY (`equipamento`) REFERENCES `equipamento` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

