package de.springbootbuch.custom_datasources;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("multiple-data-sources")
@JdbcTest(includeFilters = 
	@ComponentScan.Filter(Configuration.class))
@AutoConfigureTestDatabase(replace = NONE)
public class MultipleDataSourcesConfigIT {

	@Autowired
	private DataSource dataSource;

	@Autowired
	@Qualifier("dataSourceH2")
	private DataSource dataSourceH2;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	public void configShouldWork() throws SQLException {
		try (Connection con = dataSource.getConnection()) {
			assertThat(con.getMetaData().getURL())
				.contains("jdbc:postgresql://127.0.0.1:");
		}

		try (Connection con = dataSourceH2.getConnection()) {
			assertThat(con.getMetaData().getURL())
				.contains("jdbc:h2:mem:test_mem");
		}
	}

	@Test
	public void migrationOnPrimarySourceShouldWork() {
		String foo
			= this.jdbcTemplate.queryForObject(
				"Select foo from test", String.class);
		assertThat(foo).isEqualTo("bar");
	}

	@Test
	public void noMigrationOnOther() throws SQLException {
		try (Connection con = dataSourceH2.getConnection()) {
			final ResultSet rs = con.createStatement().executeQuery(""
				+ " SELECT count(*)"
				+ "   FROM information_schema.tables"
				+ "  WHERE table_catalog = 'TEST_MEM'"
				+ "    AND table_name = 'TEST'\n"
				+ "  LIMIT 1"
			);
			rs.next();
			assertThat(rs.getInt(1)).isEqualTo(0);
		}		
	}
}
