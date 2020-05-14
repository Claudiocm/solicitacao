package com.solicitacao.sv.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Material")
public class Material extends AbstractEntity<Long>{
	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "O nome do material é obrigatório.")
	@Size(max = 60, message = "O nome do material deve ter entre {min} e {max} caracteres.")
	@Column(name = "nome", nullable = false, unique = true, length = 60)
	private String nome;
	@ManyToOne
	@JoinColumn(name = "tipoMaterial", referencedColumnName = "id")
	private TipoMaterial tipo;
	
	@NotBlank(message = "A unidade é obrigatória.")
	@Size(max = 3, message = "A unidade deve ter entre {min} e {max} caracteres.")
	@Column(name = "unidade", nullable = false, unique = true, length = 3)
	private String unidade;

	public Material() {
		super();
	}
	
	public Material(Long id) {
		setId(id);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public TipoMaterial getTipo() {
		return tipo;
	}

	public void setTipo(TipoMaterial tipo) {
		this.tipo = tipo;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

}
