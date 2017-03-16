package de.springbootbuch.custom_datasources;

import com.vladmihalcea.flexypool.FlexyPoolDataSource;
import javax.sql.DataSource;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("flexy-pool-example")
@JdbcTest(includeFilters = @Filter(Configuration.class))
@AutoConfigureTestDatabase(replace = NONE)
public class FlexyPoolExampleConfigTest {

	@Autowired
	private DataSource dataSource;

	@Test
	public void configShouldWork() {
		assertTrue(dataSource instanceof FlexyPoolDataSource);
	}
}
