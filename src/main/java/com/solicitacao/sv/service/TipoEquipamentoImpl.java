package com.solicitacao.sv.service;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solicitacao.sv.datatables.Datatables;
import com.solicitacao.sv.datatables.DatatablesColunas;
import com.solicitacao.sv.dominio.TipoEquipamento;
import com.solicitacao.sv.repository.TipoEquipamentoRepository;

@Service
public class TipoEquipamentoImpl implements TipoEquipamentoService {
	@Autowired
	private Datatables datatables;
	@Autowired
	private TipoEquipamentoRepository repository;

	@Transactional(readOnly = true)
	@Override
	public TipoEquipamento buscarPorEquipamentoId(Long id) {
		return repository.findByTipoEquipamentoId(id).orElse(new TipoEquipamento());
	}

	@Transactional(readOnly = false)
	@Override
	public void salvar(TipoEquipamento equipamento) {
		repository.save(equipamento);
	}

	@Transactional(readOnly = false)
	@Override
	public void editar(TipoEquipamento equipamento) {
		TipoEquipamento e = repository.findById(equipamento.getId()).get();
		e.setDescricao(equipamento.getDescricao());
	}

	@Transactional(readOnly = true)
	@Override
	public TipoEquipamento buscarPorModelo(String modelo) {
		return repository.findByTipoEquipamentoModelo(modelo).orElse(new TipoEquipamento());
	}

	/*
	 * @Transactional(readOnly = false) public boolean excluirTipoPorId(Long id,
	 * Long idEq) { TipoEquipamento equipamento = repository.findById(id).get();
	 * 
	 * return repository.deleteById(idEq); }
	 */

	@Transactional(readOnly = true)

	public Object buscarTipoEquipamentos(HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.TIPOEQUIPAMENTOS);
		Page<?> page = datatables.getSearch().isEmpty() ? repository.findAll(datatables.getPageable())
				: repository.findAllByDescricao(datatables.getSearch(), datatables.getPageable());
		return datatables.getResponse(page);
	}

	@Transactional(readOnly = true)
	@Override
	public TipoEquipamento buscarPorId(Long id) {
		return repository.findById(id).get();
	}

	@Transactional(readOnly = false)
	@Override
	public void remover(Long id) {
		repository.deleteById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<String> buscarTipoEquipamentoByTermo(String termo) {
		return repository.findTipoEquipamentoByTermo(termo);
	}

	@Transactional(readOnly = true)
	@Override
	public Set<TipoEquipamento> buscarPorNomes(String[] nomes) {
		return repository.findByNomes(nomes);
	}

	@Override
	@Transactional(readOnly = true)
	public List<TipoEquipamento> buscarTodos() {
		return repository.findAll();
	}
}
