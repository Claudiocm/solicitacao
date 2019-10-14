package com.solicitacao.sv.dominio;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name="Tecnico")
public class Tecnico extends AbstractEntity<Long>{

    private static final long serialVersionUID = 1L;
 
    @NotBlank(message = "O nome do técnico é obrigatório.")
	@Size(min = 3, max = 60, message = "O nome do técnico deve ter entre {min} e {max} caracteres.")
    @Column(name = "tec_nome", nullable = false, unique = true, length = 60)
    private String tecNome;
    @OneToMany(mappedBy = "tecnico")
    private List<Chamado> Chamados;
    @JoinColumn(name="id_cargo_fk", referencedColumnName = "id")
	@ManyToOne
	private Cargo cargo;
    @OneToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	
    public Tecnico() {
		super();
	}

	public Tecnico(Long id) {
		super.setId(id);
	}

	public Tecnico(Usuario usuario) {
		this.usuario = usuario;
	}

    public String getTecNome() {
        return tecNome;
    }

    public void setTecNome(String tecNome) {
        this.tecNome = tecNome;
    }

	public List<Chamado> getChamados() {
		return Chamados;
	}

	public void setChamados(List<Chamado> Chamados) {
		this.Chamados = Chamados;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	
}
