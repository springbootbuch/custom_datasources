package de.springbootbuch.custom_datasources;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.*;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("better-builder-usage")
@JdbcTest(includeFilters = @ComponentScan.Filter(Configuration.class))
@AutoConfigureTestDatabase(replace = NONE)
public class BetterBuilderUsageConfigTest {
	
	@Autowired
	private DataSource dataSource;
	
	@Test
	public void configShouldWork() throws SQLException {
		final DatabaseMetaData metaData = dataSource.getConnection().getMetaData();
		assertThat("jdbc:h2:mem:mydb").isEqualTo(metaData.getURL());
		assertThat("foobar").isEqualTo(metaData.getUserName().toLowerCase());
	}
	
}
