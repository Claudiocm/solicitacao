package com.solicitacao.sv.rest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FilesController {
	private final Logger logger = LoggerFactory.getLogger(FilesController.class);
	private static String UPLOADED_FOLDER = "C:/users/claudio/arquivos/uploads/";

	// Single file upload
		@PostMapping("/rest/upload")
		public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile uploadfile) {

			logger.debug("Um simples upload de arquivo!");

			if (uploadfile.isEmpty()) {
				return new ResponseEntity("You must select a file!", HttpStatus.OK);
			}

			try {

				saveUploadedFiles(Arrays.asList(uploadfile));

			} catch (IOException e) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}

			return new ResponseEntity("Arquivo carregado - " + uploadfile.getOriginalFilename(), new HttpHeaders(),
					HttpStatus.OK);

		}

	// multiple upload
	@RequestMapping(value = "/rest/multipleupload", method = RequestMethod.POST)
	public ResponseEntity uploadFile(@RequestPart String metaData,
			@RequestPart(required = true) MultipartFile[] uploadfiles) {
		// Get file name
		String uploadedFileName = Arrays.stream(uploadfiles).map(x -> x.getOriginalFilename())
				.filter(x -> !StringUtils.isEmpty(x)).collect(Collectors.joining(" , "));

		if (StringUtils.isEmpty(uploadedFileName)) {
			return new ResponseEntity("Por favor selecione um arquivo!", HttpStatus.OK);
		}

		try {

			saveUploadedFiles(Arrays.asList(uploadfiles));

		} catch (IOException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity("Arquivo carregado - " + uploadedFileName, HttpStatus.OK);
	}

	// file download
	@RequestMapping(path = "/rest/download", method = RequestMethod.GET)
	public ResponseEntity<Resource> download(String param) throws IOException {

		File file = new File(param);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		Path path = Paths.get(file.getAbsolutePath());
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

		return ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
	}

	// save file
	private void saveUploadedFiles(List<MultipartFile> files) throws IOException {

		for (MultipartFile file : files) {

			if (file.isEmpty()) {
				continue; // next pls
			}

			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
			Files.write(path, bytes);

		}

	}

}
