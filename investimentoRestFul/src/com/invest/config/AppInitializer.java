package com.invest.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class[] getRootConfigClasses() {
		// return new Class[] { WebConfig.class };
		return new Class[] { RootConfig.class };
	}

	@Override
	protected Class[] getServletConfigClasses() {
		// return null;
		return new Class[] { WebConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

}