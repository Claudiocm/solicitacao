package com.solicitacao.sv.util;

import java.util.List;

public class PaginacaoUtil<T> {
	private int tamanho;
	private int pagina;
	private long totalPaginas;
	private String direcao;
	
	private List<T> registros;

	public PaginacaoUtil(int tamanho, int pagina, long totalPaginas, String direcao, List<T> registros) {
		super();
		this.tamanho = tamanho;
		this.pagina = pagina;
		this.totalPaginas = totalPaginas;
		this.direcao = direcao;
		this.registros = registros;
	}

	public int getTamanho() {
		return tamanho;
	}

	public int getPagina() {
		return pagina;
	}

	public long getTotalPaginas() {
		return totalPaginas;
	}

	public List<T> getRegistros() {
		return registros;
	}
	
	public String getDirecao() {
		return direcao;
	}
 
}
