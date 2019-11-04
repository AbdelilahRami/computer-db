package fr.excilys.db.servlet;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.context.AbstractContextLoaderInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
@Configuration
@ComponentScan("fr.excilys.db")
@PropertySource("classpath:application.properties")
public class SpringConfiguration extends AbstractContextLoaderInitializer {
	@Value("${dataSource.driver}")
	private String driver;
	@Value("${dataSource.url}")
	private String url;
	@Value("${dataSource.user}")
	private String user;
	@Value("${dataSource.password}")
	private String password;
	@Override
     protected WebApplicationContext createRootApplicationContext() {
     AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
     rootContext.register(SpringConfiguration.class);
     return rootContext;
     } 
	 @Bean
	    public DataSource dataSource() {
	        DriverManagerDataSource dataSource = new DriverManagerDataSource();
	        dataSource.setDriverClassName(driver);
	        dataSource.setUrl(url);
	        dataSource.setUsername(user);
	        dataSource.setPassword(password);
	        return dataSource;
	    }

}
