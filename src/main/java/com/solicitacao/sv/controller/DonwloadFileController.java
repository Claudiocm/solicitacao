package com.solicitacao.sv.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fasterxml.jackson.annotation.JsonView;
import com.solicitacao.sv.dominio.File;
import com.solicitacao.sv.dominio.View;
import com.solicitacao.sv.repository.FileRepository;

@Controller("/download")
public class DonwloadFileController {
	@Autowired
	FileRepository repository;

	@JsonView(View.FileInfo.class)
	@GetMapping("/listar")
	public List<File> getListaArquivos() {
		return repository.findAll();
	}

	/*
	 * Download Files
	 */
	@GetMapping("/file/{id}")
	public ResponseEntity<ByteArrayResource> getFile(@PathVariable Long id) {
		Optional<File> fileOptional = repository.findById(id);

		if (fileOptional.isPresent()) {
			File file = repository.findById(id).get();
			return ResponseEntity.ok()
					.contentType(MediaType.parseMediaType(file.getMimetype()))
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
					.body(new ByteArrayResource(file.getPic()));
		}

		return ResponseEntity.status(404).body(null);
	}
}
