package fr.excilys.db.configuration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		UserBuilder users= User.withDefaultPasswordEncoder();
		auth.inMemoryAuthentication()
			.withUser(User.withUsername("user1").password("{noop}1234").roles("****"))
			.withUser(User.withUsername("user2").password("{noop}1234").roles("ADMIN"))
			.withUser(User.withUsername("user3").password("{noop}1234").roles("USER"));
	}
	@Override
    protected void configure(HttpSecurity http) throws Exception {
       http.authorizeRequests()
       	.anyRequest().authenticated()
       	.and()
       	.formLogin()
       	.loginPage("/loginPage")
       	.loginProcessingUrl("/authenticateTheUser")
       	.permitAll();
    }
	

}
