package com.solicitacao.sv.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name="Setor")
public class Setor extends AbstractEntity<Long> {

    private static final long serialVersionUID = 1L;
   
    @NotBlank(message = "O nome do setor não pode ser em branco.")
	@Size(min = 3, max = 60, message = "O nome do Setor deve ter entre {min} e {max} caracteres.")
    @Column(name = "set_nome", nullable = false, length = 60)
    private String setNome;
    @JoinColumn(name = "id_usuario_fk", referencedColumnName = "id")
	@ManyToOne
    private Usuario usuario;
    @Digits(integer = 4, fraction = 0)
    @Column(name = "set_ramal")
    private Integer setRamal;

    public Setor() {
    }

    public String getSetNome() {
        return setNome;
    }

    public void setSetNome(String setNome) {
        this.setNome = setNome;
    }

	public Integer getSetRamal() {
		return setRamal;
	}

	public void setSetRamal(Integer setRamal) {
		this.setRamal = setRamal;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	
}
