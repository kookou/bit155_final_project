package kr.or.bit3004.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	
	
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

	@Autowired
	private BCryptPasswordEncoder bCrypPasswordEncoder;
	
	@Autowired
	private DataSource dataSource;
	
	@Value("${spring.queries.users-query}")
	private String usersQuery;
	
	@Value("${spring.queries.roles-query}")
	private String rolesQuery;
	
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) {
		try {
			auth.jdbcAuthentication()
						.usersByUsernameQuery(usersQuery)
						.authoritiesByUsernameQuery(rolesQuery)
						.dataSource(dataSource)
						.passwordEncoder(bCrypPasswordEncoder);
			
		} catch (Exception e) {
			e.getMessage();
		}
	}
	
	@Override
	public void configure(HttpSecurity http) {
		try {
			
			http.authorizeRequests()
						.antMatchers("/user/**", "/test", "/resetpassword") 
								.hasAnyRole("ADMIN", "USER")
						.antMatchers("/login/**", "/signin/**", "/signup/**", "/css/**", "/images/**", "/js/**", "/console/**", "/favicon.ico/**", "/assets/**", "/dist/**", "/error**")
								.permitAll()
						.anyRequest().authenticated();
			
			http.csrf().disable();
			
			http.formLogin()
							.loginPage("/signin")
							.defaultSuccessUrl("/loginSuccess")
							.failureUrl("/signin?error")
							.usernameParameter("id")
							.passwordParameter("pwd");
			
			http.logout()
							.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
							.logoutSuccessUrl("/")
							.invalidateHttpSession(true);
		} catch (Exception e) {
			e.getMessage();
		}
	}
}
