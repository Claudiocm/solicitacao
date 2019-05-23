package com.solicitacao.sv.dominio;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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

	@Column(nullable = false, name = "tipo")
	@Enumerated(EnumType.STRING)
	private TipoEquipamento tipo;

	@OneToMany(mappedBy = "equipamento")
	private List<Servico> servicos;

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

}
