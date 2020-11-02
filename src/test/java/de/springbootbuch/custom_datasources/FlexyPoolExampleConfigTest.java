package de.springbootbuch.custom_datasources;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.*;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.vladmihalcea.flexypool.FlexyPoolDataSource;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("flexy-pool-example")
@JdbcTest(includeFilters = @Filter(Configuration.class))
@AutoConfigureTestDatabase(replace = NONE)
public class FlexyPoolExampleConfigTest {

	@Autowired
	private DataSource dataSource;

	@Test
	public void configShouldWork() {
		assertThat(dataSource).isInstanceOf(FlexyPoolDataSource.class);
	}
}
