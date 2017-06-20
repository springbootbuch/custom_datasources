package de.springbootbuch.custom_datasources;

import com.vladmihalcea.flexypool.FlexyPoolDataSource;
import com.vladmihalcea.flexypool.adaptor.HikariCPPoolAdapter;
import com.vladmihalcea.flexypool.config.Configuration.Builder;
import com.vladmihalcea.flexypool.strategy.IncrementPoolOnTimeoutConnectionAcquiringStrategy;
import com.vladmihalcea.flexypool.strategy.RetryConnectionAcquiringStrategy;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("flexy-pool-example")
@Configuration
public class FlexyPoolExampleConfig {

	@Bean
	public DataSource dataSource(
		DataSourceProperties dataSourceProperties) {
		final DataSource dataSource = dataSourceProperties
			.initializeDataSourceBuilder().build();
		return new FlexyPoolDataSource(
			new Builder(
				dataSourceProperties.getName(),
				dataSource,
				HikariCPPoolAdapter.FACTORY
			).build(),
			new IncrementPoolOnTimeoutConnectionAcquiringStrategy
				.Factory(5),
			new RetryConnectionAcquiringStrategy
				.Factory(2)
		);
	}
}
