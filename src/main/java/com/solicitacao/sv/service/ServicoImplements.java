package com.solicitacao.sv.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.solicitacao.sv.datatables.Datatables;
import com.solicitacao.sv.datatables.DatatablesColunas;
import com.solicitacao.sv.dominio.Chamado;
import com.solicitacao.sv.dominio.Servico;
import com.solicitacao.sv.repository.ServicoRepository;

@Service
@Transactional(readOnly = false)
public class ServicoImplements implements ServicoService {
	@Autowired
	private ServicoRepository dao;
	@Autowired
	private Datatables datatables;
	
	@Override
	public void salvar(Servico servico) {
		dao.save(servico);
	}

	@Override
	public void editar(Servico servico) {
		Servico s = dao.findById(servico.getId()).get();
		s.setId(servico.getId());
		s.setSerNome(servico.getSerNome());
		s.setTipo(servico.getTipo());
		
	}

	@Override
	public void excluir(Long id) {
		dao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Servico buscarPorId(Long id) {
		Optional<Servico> serv = dao.findById(id);
		return serv.orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Servico> buscarTodos() {
		return dao.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public List<Servico> buscarPorNome(String nome) {
		return dao.buscarPorNome(nome);
	}

	@Override
	public List<Servico> buscarPorIdEquipamento(Long id) {
		// dao.buscarPorEquipamento(id);
		return dao.findByIdEquipamento(id);
	}

	@Override
	public List<Servico> buscarServico(Long id) {
		return dao.buscarServicoId(id);
	}

	@Transactional(readOnly = true)
	public Map<String, Object> buscarServicos(HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.SERVICOS);
		Page<?> page = datatables.getSearch().isEmpty()
				? dao.findAll(datatables.getPageable())
				: dao.findAllByTitulo(datatables.getSearch(), datatables.getPageable());
		return datatables.getResponse(page);
	}

	@Transactional(readOnly = true)
	public Map<String, Object> buscarServicosPorTecnico(Long id, HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.SERVICOS);
		Page<Servico> page = datatables.getSearch().isEmpty()
				? dao.findAll(datatables.getPageable())
				: dao.findByIdEquipamento(id, datatables.getPageable());
		return datatables.getResponse(page);
	}

	@Transactional(readOnly = true)
	public Set<Servico> buscarPorTitulos(String[] titulos) {
		return dao.findByTitulos(titulos);
	}

}
