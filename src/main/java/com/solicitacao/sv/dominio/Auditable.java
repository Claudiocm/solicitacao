package com.solicitacao.sv.dominio;

import java.util.Date;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<U> {
	@CreatedBy
	protected U criadoPor;
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	protected Date dataCriada;
	@LastModifiedBy
	protected U modificadoPor;

	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	protected Date ultimaDataModificada;

	public U getCriadoPor() {
		return criadoPor;
	}

	public void setCriadoPor(U criadoPor) {
		this.criadoPor = criadoPor;
	}

	public Date getDataCriada() {
		return dataCriada;
	}

	public void setDataCriada(Date dataCriada) {
		this.dataCriada = dataCriada;
	}

	public U getModificadoPor() {
		return modificadoPor;
	}

	public void setModificadoPor(U modificadoPor) {
		this.modificadoPor = modificadoPor;
	}

	public Date getUltimaDataModificada() {
		return ultimaDataModificada;
	}

	public void setUltimaDataModificada(Date ultimaDataModificada) {
		this.ultimaDataModificada = ultimaDataModificada;
	}

}
