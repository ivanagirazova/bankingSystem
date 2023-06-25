package com.example.bankingsystem.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class Databases {

    Logger logger = LoggerFactory.getLogger(Databases.class);

    @Bean("masterDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    DataSource masterDataSource() {
        logger.info("create master datasource...");
        return DataSourceBuilder.create().build();
    }

    /**
     * Slave (read only) data source.
     */
    @Bean("slaveDataSource")
    @ConfigurationProperties(prefix = "spring.ro-datasource")
    DataSource slaveDataSource() {
        logger.info("create slave datasource...");
        return DataSourceBuilder.create().build();
    }
}
