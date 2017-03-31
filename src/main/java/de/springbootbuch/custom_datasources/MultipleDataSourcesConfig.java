package de.springbootbuch.custom_datasources;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("multiple-data-sources")
@Configuration
public class MultipleDataSourcesConfig {
	@Primary @Bean	
	@ConfigurationProperties("app.datasource-pg")
	public DataSourceProperties dataSourceProperties() {
		return new DataSourceProperties();
	}

	@Primary @Bean	
	@ConfigurationProperties("app.datasource-pg")
	public DataSource dataSource(
		final DataSourceProperties properties) {
		return properties
			.initializeDataSourceBuilder().build();
	}
	
	@Bean	
	@ConfigurationProperties("app.datasource-h2")
	public DataSourceProperties dataSourceH2Properties() {
		return new DataSourceProperties();
	}

	@Bean	
	@ConfigurationProperties("app.datasource-h2")
	public DataSource dataSourceH2(
		@Qualifier("dataSourceH2Properties")
		final DataSourceProperties properties
	) {	
		// Alternativly, you can use
		// dataSourceH2Properties()
		// instead of a qualifier
		return properties
			.initializeDataSourceBuilder().build();
	}
}
