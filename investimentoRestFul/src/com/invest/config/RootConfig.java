package com.invest.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.ejb.HibernatePersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource({ "application.properties" })
@EnableJpaRepositories("com.invest.*")
@ComponentScan(basePackages = { "com.invest.*" })
@EntityScan("com.invest.*")
@EnableTransactionManagement
public class RootConfig {
	private static final Logger logger = LoggerFactory.getLogger(RootConfig.class);

	private static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
	private static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";
	private static final String PROPERTY_NAME_DATABASE_URL = "db.url";
	private static final String PROPERTY_NAME_DATABASE_USERNAME = "db.username";
	private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
	private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
	private static final String PROPERTY_NAME_HIBERNATE_AUTO = "hibernate.hbm2ddl.auto";
	public static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "com.invest.entidade.*";
	@Autowired
	private Environment env;

	@Bean
	public DataSource dataSource() {
		logger.info("RootConfig.dataSource()");
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName(this.env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
		dataSource.setUrl(this.env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
		dataSource.setUsername(this.env.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
		dataSource.setPassword(this.env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));

		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		logger.info("RootConfig.entityManagerFactory()");
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistence.class);
		entityManagerFactoryBean.setPackagesToScan(new String[] { PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN });

		entityManagerFactoryBean.setJpaProperties(hibProperties());

		return entityManagerFactoryBean;
	}

	private Properties hibProperties() {
		logger.info("RootConfig.hibProperties()");
		Properties properties = new Properties();
		properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, this.env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
		properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL,
				this.env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
		properties.put(PROPERTY_NAME_HIBERNATE_AUTO, this.env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_AUTO));
		return properties;
	}

	@Bean
	public JpaTransactionManager transactionManager() {
		logger.info("RootConfig.transactionManager()");
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}
}
