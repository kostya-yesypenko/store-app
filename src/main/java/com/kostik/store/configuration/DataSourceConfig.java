package com.kostik.store.configuration;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class DataSourceConfig {
	@Value("${spring.datasource.driver-class-name}")
	private String driver;
	@Value("${spring.datasource.username}")
	private String username;
	@Value("${spring.datasource.password}")
	private String password;
	@Value("${spring.datasource.url}")
	private String url;
/*
	@Bean
	DataSource mysqlDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driver);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);

		return dataSource;
	}
	*/

	@Bean
	DataSource dataSource() {
		BasicDataSource ds = new BasicDataSource();

		ds.setUrl(url);
		ds.setUsername(username);
		ds.setPassword(password);
		ds.setMinIdle(5);
		ds.setMaxTotal(15);
		ds.setDefaultAutoCommit(true);
		ds.setMaxOpenPreparedStatements(100);
		return ds;
	}
}