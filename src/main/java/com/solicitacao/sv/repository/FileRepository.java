package com.solicitacao.sv.repository;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.solicitacao.sv.dominio.File;

@Transactional
public interface FileRepository extends JpaRepository<File, Long> {
	public File findByName(String name);
}
