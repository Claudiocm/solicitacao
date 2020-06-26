package com.solicitacao.sv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.solicitacao.sv.dominio.Cargo;

public interface CargoRepository extends JpaRepository<Cargo, Long>{
	@Query("Select c from Cargo c where c.nome like :nome%")
	List<Cargo> buscarPorNome(String nome);
   
}
