package com.solicitacao.sv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.solicitacao.sv.dominio.Cargo;
import com.solicitacao.sv.util.PaginacaoUtil;

public interface CargoRepository extends JpaRepository<Cargo, Long>{
	@Query("Select c from Cargo c where c.nome like :nome%")
	List<Cargo> buscarPorNome(String nome);
		
	@Query("Select count(*) from Cargo")
	public long count();
 
}
