package com.solicitacao.sv.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solicitacao.sv.dominio.Cargo;
import com.solicitacao.sv.repository.CargoRepository;
import com.solicitacao.sv.util.PaginacaoUtil;

@Service
@Transactional(readOnly = false)
public class CargoServiceImpl implements CargoService {
	@Autowired
	private CargoRepository dao;

	@Override
	public void salvar(Cargo cargo) {
		dao.save(cargo);
	}

	@Override
	public void excluir(Long id) {
		dao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Cargo buscarPorId(Long id) {
		Optional<Cargo> cargo = dao.findById(id);
		Cargo c = null;
		if(cargo.isPresent()) {
			c = cargo.get();
		}else {
			throw new RuntimeException("O cargo não encontrado com este código: " + id);
		}
		return c;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Cargo> buscarTodos() {
		return dao.findAll();
	}

	@Transactional(readOnly = false)
	@Override
	public void editar(Cargo cargo) {
		Cargo c = dao.findById(cargo.getId()).get();
		c.setNome(cargo.getNome());
		c.setTecnicos(cargo.getTecnicos());
	
	}

	@Override
	public boolean cargoTemTecnicos(Long id) {
		if (buscarPorId(id).getTecnicos().isEmpty()) {
			return false;
		}
		return true;
	}

	@Override
	public List<Cargo> buscarPorNome(String nome) {
		return dao.buscarPorNome(nome);
	}

	@Override
	public Page<Cargo> findPagenated(int page, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortField).ascending() 
				: Sort.by(sortField).descending();
		Pageable pageable = PageRequest.of(page - 1, pageSize, sort);
		
		return this.dao.findAll(pageable);
	}


}
