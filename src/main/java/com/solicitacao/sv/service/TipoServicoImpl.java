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
import com.solicitacao.sv.dominio.TipoServico;
import com.solicitacao.sv.repository.TipoServicoRepository;

@Service
public class TipoServicoImpl implements TipoServicoService {
	@Autowired
	private Datatables datatables;
	@Autowired
	private TipoServicoRepository repository;

	@Transactional(readOnly = true)
	@Override
	public TipoServico buscarPorServicoId(Long id) {
		return repository.findByTipoServicoId(id).orElse(new TipoServico());
	}

	@Transactional(readOnly = false)
	@Override
	public void salvar(TipoServico tipoServico) {
		repository.save(tipoServico);
	}

	@Transactional(readOnly = false)
	@Override
	public void editar(TipoServico tipoServico) {
		TipoServico t = repository.findById(tipoServico.getId()).get();
		t.setDescricao(tipoServico.getDescricao());
	}

	@Transactional(readOnly = true)
	@Override
	public TipoServico buscarPorModelo(String descricao) {
		return repository.findByTipoServicoModelo(descricao).orElse(new TipoServico());
	}

	/*
	 * @Transactional(readOnly = false) public boolean excluirTipoPorId(Long id,
	 * Long idEq) { TipoEquipamento equipamento = repository.findById(id).get();
	 * 
	 * return repository.deleteById(idEq); }
	 */

	@Transactional(readOnly = true)

	public Object buscarTipoServicos(HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.TIPOSERVICOS);
		Page<?> page = datatables.getSearch().isEmpty() ? repository.findAll(datatables.getPageable())
				: repository.findAllByDescricao(datatables.getSearch(), datatables.getPageable());
		return datatables.getResponse(page);
	}

	@Transactional(readOnly = true)
	@Override
	public TipoServico buscarPorId(Long id) {
		return repository.findById(id).get();
	}

	@Transactional(readOnly = false)
	@Override
	public void remover(Long id) {
		repository.deleteById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<String> buscarTipoServicoByTermo(String termo) {
		return repository.findTipoServicoByTermo(termo);
	}

	@Transactional(readOnly = true)
	@Override
	public Set<TipoServico> buscarPorNomes(String[] nomes) {
		return repository.findByNomes(nomes);
	}

	@Override
	@Transactional(readOnly = true)
	public List<TipoServico> buscarTodos() {
		return repository.findAll();
	}
}
