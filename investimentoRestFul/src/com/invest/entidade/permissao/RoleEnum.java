package com.invest.entidade.permissao;

public enum RoleEnum {

	ROLE_ADMIN(1, Constants.ROLE_ADMIN), //
	ROLE_CONVIDADOO(2, Constants.ROLE_CONVIDADO);

	private RoleEnum(Integer id, String descricao) {
	}

	public static class Constants {
		public static final String ROLE_ADMIN = "ROLE_ADMIN";
		public static final String ROLE_CONVIDADO = "ROLE_CONVIDADO";
	}

}
