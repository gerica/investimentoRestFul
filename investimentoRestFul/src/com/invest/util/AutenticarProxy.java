package com.invest.util;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

public class AutenticarProxy {

	public void autenticar() {
		final String authUser = "s203489";
		final String authPassword = "Cardoso1";

		Authenticator.setDefault(new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(authUser, authPassword.toCharArray());
			}
		});
		
		System.setProperty("http.proxyHost", "proxy3.trt18.jus.br");
		System.setProperty("http.proxyPort", "80");

	}

}
