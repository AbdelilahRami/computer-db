package fr.excilys.db.configuration;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
@Configuration                                             
@ComponentScan(basePackages = {"fr.excilys.db.configuration","fr.excilys.db.controller","fr.excilys.db.daoImp",
		"fr.excilys.db.mapper","fr.excilys.db.service.impl","fr.excilys.db.validators"})
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
@EnableWebMvc
public class SpringConfiguration implements WebApplicationInitializer  {
	@Value("${dataSource.driver}")
	private String driver;
	@Value("${dataSource.url}")
	private String url;
	@Value("${dataSource.user}")
	private String user;
	@Value("${dataSource.password}")
	private String password;
	@Value("${show.sql}")
	private String showSql;
	@Value("${hbm2ddl.auto}")
	private String hbm2ddl;
	@Value("${hibernate.dialect}")
	private String hibernateDialect;
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driver);
		dataSource.setUrl(url);
		dataSource.setUsername(user);
		dataSource.setPassword(password);
		return dataSource;
	}

	@Bean
	public LocalSessionFactoryBean getSessionFactory() {
		
		LocalSessionFactoryBean factoryBean= new LocalSessionFactoryBean();
		factoryBean.setDataSource(dataSource());
		factoryBean.setPackagesToScan("fr.excilys.db.model");
		Properties properties= new Properties();
		properties.setProperty("show.sql", showSql);
		properties.setProperty("hbm2ddl.auto", hbm2ddl);
		properties.setProperty("hibernate.dialect", hibernateDialect);
		return factoryBean;
	}
	@Bean
	public HibernateTransactionManager getTransactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(getSessionFactory().getObject());
		return transactionManager;
	}

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
	@Override
	public void onStartup(ServletContext context) throws ServletException {
		AnnotationConfigWebApplicationContext webCtx = new AnnotationConfigWebApplicationContext();
		webCtx.register(SpringConfiguration.class, SpringMvcConfiguration.class);
		webCtx.setServletContext(context);
		ServletRegistration.Dynamic servlet = context.addServlet("SpringApplication", new DispatcherServlet(webCtx));
		servlet.setLoadOnStartup(1);
		servlet.addMapping("/");
		
	}

}
