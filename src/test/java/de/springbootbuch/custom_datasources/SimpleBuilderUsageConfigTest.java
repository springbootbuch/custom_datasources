package de.springbootbuch.custom_datasources;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("simple-builder-usage")
@JdbcTest(includeFilters = @ComponentScan.Filter(Configuration.class))
@AutoConfigureTestDatabase(replace = NONE)
public class SimpleBuilderUsageConfigTest {
	
	@Autowired
	private DataSource dataSource;
	
	@Test
	public void configShouldWork() throws SQLException {
		final DatabaseMetaData metaData = dataSource.getConnection().getMetaData();
		assertEquals("jdbc:h2:mem:thedb", metaData.getURL());
		assertEquals("bazbar", metaData.getUserName().toLowerCase());
	}
}