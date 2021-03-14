package com.solicitacao.sv.dominio;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "Servico")
public class Servico extends AbstractEntity<Long> {
	private static final long serialVersionUID = 1L;
	@Size(max = 60, message = "O nome do serviço deve ter entre {min} e {max} caracteres.")
	@Column(name = "ser_nome", nullable = false, unique = true, length = 60)
	private String serNome;
	@ManyToOne
	@JoinColumn(name = "tipoServico", referencedColumnName = "id")
	private TipoServico tipo;
	@JsonIgnore
	@ManyToMany
	@JoinTable(
			name = "equipamentos_tem_servicos",
			joinColumns = @JoinColumn(name = "servico", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "equipamento", referencedColumnName = "id")
    )
	private List<Equipamento> equipamentos;
	
	public Servico() {
		super();
	}
	
	public Servico(Long id) {
		super.setId(id);
	}

	public String getSerNome() {
		return serNome;
	}

	public void setSerNome(String serNome) {
		this.serNome = serNome;
	}

	public TipoServico getTipo() {
		return tipo;
	}

	public void setTipo(TipoServico tipo) {
		this.tipo = tipo;
	}

	public List<Equipamento> getEquipamentos() {
		return equipamentos;
	}

	public void setEquipamentos(List<Equipamento> equipamentos) {
		this.equipamentos = equipamentos;
	}

	
}
