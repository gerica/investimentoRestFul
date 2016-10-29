package com.invest.controller;

public abstract class UriConstInvestimento {
	// Functionalidades para os AUTENTICAR
	public static final String URI_AUTH =  "auth";
	public static final String URI_REFRESH =  "refresh";
	
	// Functionalidades para os PAPEIS
	public static final String URI_TABELA_MAGICA = "/tabelaMagica";

	// Functionalidades para USUÁRIOS
	public static final String URI_REGISTRAR_USUARIO = "/registrarUsuario";
	public static final String URI_ALTERAR_USUARIO = "/alterarUsuario";

	// Funcionalidades para OPERAÇÃO
	public static final String URI_SALVAR_OPERACAO = "/salvarOperacao";
	public static final String URI_RECUPERAR_OPERACAO_ENTRADA = "/getOperacao";
	public static final String URI_RECUPERAR_TODAS_OPERACAO_ENTRADA = "/getAllOperacaoEntrada";

}
