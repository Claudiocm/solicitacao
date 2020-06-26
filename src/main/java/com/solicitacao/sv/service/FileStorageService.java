package com.solicitacao.sv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solicitacao.sv.repository.FileRepository;

@Service
public class FileStorageService {
	@Autowired
	private FileRepository repositorio;

	
}
