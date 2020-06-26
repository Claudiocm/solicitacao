package com.solicitacao.sv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.solicitacao.sv.dominio.File;
import com.solicitacao.sv.repository.FileRepository;

@Controller("/arquivos")
public class UploadFileController {
	@Autowired
	FileRepository repository;

	@PostMapping("/upload")
	public String uploadMultipartFile(@RequestParam("uploadfile") MultipartFile uploadfile) {
		try {
			// save file to PostgreSQL
			File filemode = new File(uploadfile.getOriginalFilename(), uploadfile.getContentType(),
					uploadfile.getBytes());
			repository.save(filemode);
			return "Arquivo carregado com Sucesso! -> arquivo = " + uploadfile.getOriginalFilename();
		} catch (Exception e) {
			return "FALHA! Talvez tenha carregado menor que > 500KB";
		}
	}
}
