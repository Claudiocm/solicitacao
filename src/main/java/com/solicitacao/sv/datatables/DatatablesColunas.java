package com.solicitacao.sv.datatables;

public class DatatablesColunas {

	public static final String[] SERVICOS = { "id", "nome","tipoServico.descricao"};

	public static final String[] TECNICOS = { "id", "tecNome"};

	public static final String[] SOLICITANTES = { "id", "nome",  "chamados" };

	public static final String[] EQUIPAMENTOS = { "id", "descricao", "modelo", "tipoEquipamento.descricao", "seriebp" };

	public static final String[] SETORES = { "setNome", "setRamal", "usuarios.nome" };

	public static final String[] CHAMADOS = { "id", "solicitante.nome", "dataChAbertura","equipamento.modelo",
			"tecnico.tecNome", "servico.nome", "situacao" };

	public static final String[] USUARIOS = { "id", "email","setor","perfis.descricao","ativo"};
	
	public static final String[] TIPOEQUIPAMENTOS = { "id","descricao"};
	
	public static final String[] TIPOSERVICOS = { "id","descricao"};

}
