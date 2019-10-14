package com.solicitacao.sv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solicitacao.sv.dominio.Cargo;
import com.solicitacao.sv.repository.CargoRepository;

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
		return dao.findById(id).get();
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
		if (!cargo.getTecnicos().isEmpty()) {
			c.getTecnicos().addAll(cargo.getTecnicos());
		}
	}

	@Override
	public boolean cargoTemTecnicos(Long id) {
		if(buscarPorId(id).getTecnicos().isEmpty()){
			return false;
		}
		return true;
	}

}
