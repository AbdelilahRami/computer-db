package fr.excilys.db.console;
import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
@Configuration
@ComponentScan(basePackages = {"fr.excilys.db.model","fr.excilys.db.console","fr.excilys.db.dao","fr.excilys.db.service","fr.excilys.db.dto",
		"fr.excilys.db.dto","fr.excilys.db.mapper","fr.excilys.db.validators"})
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
public class ConsoleConfiguration {
	@Autowired
	private Environment env;
	@Bean
	 public DataSource dataSource() {
		 DriverManagerDataSource dataSource = new DriverManagerDataSource();
		 dataSource.setDriverClassName(env.getProperty("dataSource.driver"));
		 dataSource.setUrl(env.getProperty("dataSource.url"));
		 dataSource.setUsername(env.getProperty("dataSource.user"));
		 dataSource.setPassword(env.getProperty("dataSource.password"));
		 return dataSource;
	 }
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean factoryBean= new LocalSessionFactoryBean();
		factoryBean.setDataSource(dataSource());
		factoryBean.setPackagesToScan("fr.excilys.db.model");
		factoryBean.setHibernateProperties(getHibernateProperties());
		return factoryBean;
	}
	@Bean
	public HibernateTransactionManager getTransactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());
		return transactionManager;
	}
	private Properties getHibernateProperties() {
		Properties properties= new Properties();
		properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
		properties.put("hbm2ddl.auto", env.getProperty("hbm2ddl.auto"));
		properties.put("hibernate.show_sql",env.getProperty("hibernate.show_sql"));
		return properties;
	}
}
