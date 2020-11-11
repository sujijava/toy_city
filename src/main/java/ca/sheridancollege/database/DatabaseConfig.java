package ca.sheridancollege.database;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

	@Configuration
	public class DatabaseConfig {

		
		@Bean
		public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
		    return new NamedParameterJdbcTemplate(dataSource);
		}

		@Bean
		public DataSource dataSource() {
		    DriverManagerDataSource dataSource = new DriverManagerDataSource();
		    dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		    dataSource.setUrl("jdbc:mysql://localhost/NBA");
		    dataSource.setUsername("root");
		    dataSource.setPassword("root");
		    
		    dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		    dataSource.setUrl("jdbc:mysql://b95423c21fdcd4:4c8ad120@us-cdbr-east-02.cleardb.com/heroku_0657e10a54b4617?reconnect=true");
		    dataSource.setUsername("b95423c21fdcd4");
		    dataSource.setPassword("4c8ad120");
		    	    
		    
		    return dataSource;
		}
		
		
		
		
		
		
//		
//		
//		
//		//Used in our DatabaseAccess class to submit JDBC Query Strings
//		@Bean
//		public NamedParameterJdbcTemplate 
//				namedParemterJdbcTemplate(DataSource dataSource) {
//			return new NamedParameterJdbcTemplate(dataSource);
//		}
//		
//		//Creates our connection to our database.  In this case h2
//		@Bean
//		public DataSource dataSource() {
//			DriverManagerDataSource dataSource = new DriverManagerDataSource();
//			dataSource.setDriverClassName("org.h2.Driver");
//			dataSource.setUrl("jdbc:h2:mem:testdb");
//			dataSource.setUsername("sa");
//			dataSource.setPassword("");
//			return dataSource;
//		}
//		
		//Load any default sql files when we compile the project.
//		@Bean
//		public DataSource loadSchema() {
//		    return new EmbeddedDatabaseBuilder()
//		      .setType(EmbeddedDatabaseType.H2)
//		      .addScript("classpath:inventory.sql")
//		      .addScript("classpath:users.sql")
//		      //You can include additional .addScript() for multiple sql files.
//		      .build();
//		}
	}
