package kr.or.bit3004.config;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import kr.or.bit3004.handler.LoginFailureHandler;
import kr.or.bit3004.handler.LoginSuccessHandler;
import kr.or.bit3004.oauth2.CustomOAuth2Provider;
import kr.or.bit3004.serviceImpl.CustomOAuth2UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	
	
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

	@Autowired
	private BCryptPasswordEncoder bCrypPasswordEncoder;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private AuthenticationSuccessHandler loginSuccessHandler; 
	
	@Autowired
	private AuthenticationFailureHandler loginFailureHandler;

	
	
	
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
							.defaultSuccessUrl("/")
							.failureUrl("/signin?error")
							.usernameParameter("id")
							.passwordParameter("pwd")
							
							.successHandler(loginSuccessHandler)
							.failureHandler(loginFailureHandler);
			
			http.logout()
							.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
							.logoutSuccessUrl("/")
							.invalidateHttpSession(true);
			
			http.oauth2Login().userInfoEndpoint()
				.userService(new CustomOAuth2UserService()) // 네이버 USER INFO의 응답을 처리하기 위한 설정 
				.and() 
				.defaultSuccessUrl("/loginSuccess") 
				.failureUrl("/loginFailure") 
				.and() 
				.exceptionHandling() 
				.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/signin")); 
			
			
		} catch (Exception e) {
			e.getMessage();
		}
	}

	
	
	@Bean 
	public ClientRegistrationRepository clientRegistrationRepository( 
			OAuth2ClientProperties oAuth2ClientProperties, 
			@Value("${custom.oauth2.kakao.client-id}") 
			String kakaoClientId, 
			@Value("${custom.oauth2.kakao.client-secret}") 
			String kakaoClientSecret, 
			@Value("${custom.oauth2.naver.client-id}") 
			String naverClientId, 
			@Value("${custom.oauth2.naver.client-secret}") 
			String naverClientSecret) { 
		List<ClientRegistration> registrations = oAuth2ClientProperties 
				.getRegistration().keySet().stream() 
				.map(client -> getRegistration(oAuth2ClientProperties, client)) 
				.filter(Objects::nonNull) .collect(Collectors.toList()); 
		
		registrations.add(CustomOAuth2Provider.KAKAO.getBuilder("kakao") 
				.clientId(kakaoClientId) 
				.clientSecret(kakaoClientSecret) 
				.jwkSetUri("temp") 
				.build()); 
		
		registrations.add(CustomOAuth2Provider.NAVER.getBuilder("naver") 
				.clientId(naverClientId) 
				.clientSecret(naverClientSecret) 
				.jwkSetUri("temp") 
				.build()); 
		
		return new InMemoryClientRegistrationRepository(registrations); 
		
	} 
	
	private ClientRegistration getRegistration(OAuth2ClientProperties clientProperties, String client) { 
		if("google".equals(client)) { 
			OAuth2ClientProperties.Registration registration = clientProperties.getRegistration().get("google"); 
			return CommonOAuth2Provider.GOOGLE.getBuilder(client) 
					.clientId(registration.getClientId()) 
					.clientSecret(registration.getClientSecret()) 
					.scope("email", "profile") 
					.build(); 
			} 
		if("facebook".equals(client)) { 
			OAuth2ClientProperties.Registration registration = clientProperties.getRegistration().get("facebook"); 
			return CommonOAuth2Provider.FACEBOOK.getBuilder(client) 
					.clientId(registration.getClientId()) 
					.clientSecret(registration.getClientSecret()) 
					.userInfoUri("https://graph.facebook.com/me?fields=id,name,email,link") 
					.scope("email") 
					.build(); 
			} return null; 
			
	} 

	
	
	
}
