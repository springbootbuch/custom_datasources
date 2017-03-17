package de.springbootbuch.custom_datasources;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("multiple-data-sources")
@JdbcTest(includeFilters = 
	@ComponentScan.Filter(Configuration.class))
@AutoConfigureTestDatabase(replace = NONE)
public class MultipleDataSourcesConfigIT {

	@Autowired
	private DataSource dataSource;

	@Autowired
	@Qualifier("dataSourceMySql")
	private DataSource dataSourceMySql;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	public void configShouldWork() throws SQLException {
		try (Connection con = dataSource.getConnection()) {
			assertThat(con.getMetaData().getURL(),
				containsString("jdbc:postgresql://127.0.0.1:"));
		}

		try (Connection con = dataSourceMySql.getConnection()) {
			assertThat(con.getMetaData().getURL(),
				containsString("jdbc:mysql://127.0.0.1:"));
		}
	}

	@Test
	public void migrationOnPrimarySourceShouldWork() {
		String foo
			= this.jdbcTemplate.queryForObject(
				"Select foo from test", String.class);
		assertThat(foo, is("bar"));
	}

	@Test
	public void noMigrationOnOther() throws SQLException {
		try (Connection con = dataSourceMySql.getConnection()) {
			final ResultSet rs = con.createStatement().executeQuery(""
				+ " SELECT count(*)"
				+ "   FROM information_schema.tables"
				+ "  WHERE table_schema = 'spring_mysql'"
				+ "    AND table_name = 'test'\n"
				+ "  LIMIT 1"
			);
			rs.next();
			assertThat(rs.getInt(1), is(0));
		}		
	}
}
