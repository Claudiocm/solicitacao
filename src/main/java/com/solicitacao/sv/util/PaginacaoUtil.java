package com.solicitacao.sv.util;

import java.util.List;

public class PaginacaoUtil<T> {
	private int tamanho;
	private int pagina;
	private long totalPaginas;
	private List<T> registros;

	public PaginacaoUtil(int tamanho, int pagina, long totalPaginas, List<T> registros) {
		super();
		this.tamanho = tamanho;
		this.pagina = pagina;
		this.totalPaginas = totalPaginas;
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
 
}
