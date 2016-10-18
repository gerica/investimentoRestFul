package com.invest.controller;

import java.util.Collections;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.invest.entidade.Usuario;

@RestController
public class AutenticarRestController {
	private static final Logger logger = LoggerFactory.getLogger(AutenticarRestController.class);

	@PostMapping(value = "/tabelaMagica")
	@ResponseBody
	public Map<String,String> autenticar(@RequestBody Usuario usuario) {
		logger.info("AutenticarRestController.autenticar()");
		logger.debug(usuario.toString());

		// customerDAO.create(customer);

		return Collections.singletonMap("token", "1");
//		MultiValueMap<String, String> valores = new HttpHeaders();
//		List<String> idToken = new ArrayList<String>();
//		idToken.add("1");
//		valores.put("id_token", idToken);
//		return new ResponseEntity<Usuario>(usuario, valores, HttpStatus.OK);
	}

}
