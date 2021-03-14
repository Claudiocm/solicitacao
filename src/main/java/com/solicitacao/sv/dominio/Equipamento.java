package com.solicitacao.sv.dominio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Equipamento")
public class Equipamento extends AbstractEntity<Long> {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "A descrição do equipamento é obrigatório.")
	@Size(max = 80, message = "A descrição do equipamento deve ter no máximo caracteres.")
	@Column(name = "eq_descricao")
	private String eqDescricao;

	@NotBlank(message = "{NotNull.equipamento.eqModelo}")
	@Column(name = "eq_modelo", nullable = false, unique = true)
	private String eqModelo;

	@NotBlank(message = "{NotNull.equipamento.eqSeriebp}")
	@Column(name = "eq_seriebp", nullable = false, unique = true, length = 8)
	private String eqSeriebp;

	@ManyToOne
	@JoinColumn(name = "tipoEquipamento", referencedColumnName = "id")
	private TipoEquipamento tipo;
    @JsonIgnore
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	@JoinTable(name = "equipamentos_tem_servicos", joinColumns = @JoinColumn(name = "equipamento", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "servico", referencedColumnName = "id"))
	private List<Servico> servicos;
	
	@ManyToMany
	@JoinTable(name = "equipamentos_tem_chamados", joinColumns = @JoinColumn(name = "equipamento", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "chamado", referencedColumnName = "id"))
	private List<Chamado> chamados;

	public Equipamento() {
		super();
	}

	public Equipamento(Long id) {
		super.setId(id);
	}
	
	public void addServico(Servico servico) {
		if (this.servicos == null) {
			this.servicos = new ArrayList<>();
		}
		this.servicos.add(new Servico(servico.getId()));
	}

	public String getEqDescricao() {
		return eqDescricao;
	}

	public void setEqDescricao(String eqDescricao) {
		this.eqDescricao = eqDescricao;
	}

	public String getEqModelo() {
		return eqModelo;
	}

	public void setEqModelo(String eqModelo) {
		this.eqModelo = eqModelo;
	}

	public String getEqSeriebp() {
		return eqSeriebp;
	}

	public void setEqSeriebp(String eqSeriebp) {
		this.eqSeriebp = eqSeriebp;
	}

	public TipoEquipamento getTipo() {
		return tipo;
	}

	public void setTipo(TipoEquipamento tipo) {
		this.tipo = tipo;
	}

	public List<Servico> getServicos() {
		return servicos;
	}

	public void setServicos(List<Servico> servicos) {
		this.servicos = servicos;
	}

	public List<Chamado> getChamados() {
		return chamados;
	}

	public void setChamados(List<Chamado> chamados) {
		this.chamados = chamados;
	}

}
