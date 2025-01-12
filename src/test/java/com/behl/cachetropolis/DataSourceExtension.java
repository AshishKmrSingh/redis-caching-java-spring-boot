package com.behl.cachetropolis;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class DataSourceExtension implements BeforeAllCallback {

	private static final DockerImageName MYSQL_IMAGE = DockerImageName.parse("mysql:8");
	private static final MySQLContainer<?> mySQLContainer = new MySQLContainer<>(MYSQL_IMAGE);

	@Override
	public void beforeAll(final ExtensionContext context) {
		log.info("Creating datasource container : {}", MYSQL_IMAGE);
		mySQLContainer.start();
		addDataSourceProperties();
		log.info("Successfully started datasource container : {}", MYSQL_IMAGE);
	}

	private void addDataSourceProperties() {
		System.setProperty("spring.datasource.url", mySQLContainer.getJdbcUrl());
		System.setProperty("spring.datasource.username", mySQLContainer.getUsername());
		System.setProperty("spring.datasource.password", mySQLContainer.getPassword());
	}

}
