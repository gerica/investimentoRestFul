package com.invest.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.invest.entidade.rendaVariavel.Papel;
import com.invest.entidade.rendaVariavel.SetorEnum;
import com.invest.execao.InvestimentoBusinessException;
import com.invest.service.rendaVariavel.TabelaMagicaService;

@RestController
public class PapelRestController {
	private static final Logger logger = LoggerFactory.getLogger(PapelRestController.class);

	@Autowired
	private TabelaMagicaService papelService;

	@GetMapping("/tabelaMagica")
	// @RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public List getTabelaMagica() {
		logger.info("PapelRestController.getTabelaMagica()");

		List<Papel> papeis = null;
		try {
			papeis = papelService.analizarPapeis(papelService.findBySetor(SetorEnum.TODOS));

		} catch (InvestimentoBusinessException e) {
			e.printStackTrace();
		}
		return papeis;
	}

}
