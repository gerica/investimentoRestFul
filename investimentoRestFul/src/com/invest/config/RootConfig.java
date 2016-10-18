package com.invest.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

// @Configuration
// @PropertySource({ "application.properties" })
// @ComponentScan(basePackages = { "com.invest.*" })
// @EntityScan("com.invest.*")

@Configuration
@ComponentScan(basePackages = { "com.invest" }, excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class) })
public class RootConfig {
}
