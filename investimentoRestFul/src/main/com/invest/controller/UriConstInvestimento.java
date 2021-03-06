package com.invest.controller;

public abstract class UriConstInvestimento {
	// Functionalidades para os AUTENTICAR
	public static final String URI_AUTH = "auth";
	public static final String URI_REFRESH = "refresh";

	// Functionalidades para os PAPEIS
	public static final String URI_TABELA_MAGICA = "/tabelaMagica";
	public static final String URI_ATIVAR_DESATIVAR_PAPEL = "ativarDesativarPapel";
	public static final String URI_RECUPERAR_PAPEIS_ATIVO = "recuperarPapeisAtivo";
	public static final String URI_RECUPERAR_TODOS_PAPEIS = "recuperarTodosPapeis";
	public static final String URI_RECUPERAR_BALANCO_HOJE = "recuperarBalancoHoje";
	public static final String URI_RECUPERAR_PAPEIS_OPERECAO = "recuperarPapeisOperacao";

	// Functionalidades para USUÁRIOS
	public static final String URI_REGISTRAR_USUARIO = "/registrarUsuario";
	public static final String URI_ALTERAR_USUARIO = "/alterarUsuario";

	// Funcionalidades para OPERAÇÃO
	public static final String URI_SALVAR_OPERACAO_ENTRADA = "salvarOperacaoEntrada";
	public static final String URI_SALVAR_OPERACAO_SAIDA = "salvarOperacaoSaida";
	public static final String URI_RECUPERAR_OPERACAO_ENTRADA_ABERTA = "recuperarOperacaoEntradaAberta";
	public static final String URI_RECUPERAR_OPERACAO_SAIDA = "recuperarOperacaoSaida";
	public static final String URI_RECUPERAR_TODAS_OPERACAO_ENTRADA = "recuperarTodasOperacaoEntrada";
	public static final String URI_EXCLUIR_OPERACAO_ENTRADA = "excluirOperacaoEntrada";
	public static final String URI_EDITAR_OPERACAO_ENTRADA = "editarOperacaoEntrada";
	public static final String URI_RECUPERAR_RELATORIO_SAIDA = "recuperarRelatorioSaida/{ano}/{mes}/{idPapel}";

	// Funcionalidades de CONFIGURAÇÃO ANÁLISE COTAÇÃO
	public static final String URI_RECUPERAR_CONFIGURACAO_ANALISE = "recuperarConfiguracaoAnalise";
	public static final String URI_SALVAR_CONFIGURACAO_ANALISE = "salvarConfiguracaoAnalise";
	
	// Funcionalidades de COTAÇÃO
	public static final String URI_RECUPERAR_ULTIMA_COTACAO = "recuperarUltimaCotacao/{idPapel}";
	public static final String URI_ATUALIZAR_HISTORICO_BMF = "atualizarHistoricoBMF";
	public static final String URI_ATUALIZAR_ATUAL_BMF = "atualizarAtualBMF";
	public static final String URI_RECUPERAR_COTACAO_POR_PAPEL = "recuperarCotacoesPorPapel/{idPapel}";
	public static final String URI_RECUPERAR_BALANCO_CARTEIRA = "recuperarBalancoCarteira";

}
