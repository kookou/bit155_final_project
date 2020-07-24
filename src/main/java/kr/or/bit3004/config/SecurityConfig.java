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
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import kr.or.bit3004.oauth2.CustomOAuth2Provider;
import kr.or.bit3004.oauth2.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	
	
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

	@Autowired
	private BCryptPasswordEncoder bCrypPasswordEncoder;

	
	@Autowired
	private AuthenticationSuccessHandler loginSuccessHandler; 
	
	@Autowired
	private AuthenticationFailureHandler loginFailureHandler;
	
	private final CustomOAuth2UserService customOAuth2UserService;
	private final DataSource dataSource;

	
	
	
	@Value("${spring.queries.users-query}")
	private String usersQuery;
	
	@Value("${spring.queries.roles-query}")
	private String rolesQuery;
	
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", 
								   "/images/**", "/css/**", "/img/**", "/js/**", 
								   "/console/**", "/favicon.ico/**", "/assets/**", 
								   "/dist/**", "/error**");
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
						.antMatchers("/user/**", "/resetpassword") 
								.hasAnyRole("ADMIN", "USER")
						.antMatchers("/", "/login/**", "/signin/**", "/signup/**", "/oauth2/**")
								.permitAll()
						.anyRequest().authenticated();
			
			http.csrf().disable();
			
			http.rememberMe()
						.key("tika")
						.tokenRepository(tokenRepository()) // DB연동
						.rememberMeCookieName("TIKA_KNOWS") // 쿠키 이름 커스터마이징
						.rememberMeParameter("remember-me") 
						.authenticationSuccessHandler(loginSuccessHandler);
						
//						이 방식은 쿠키를 쓰는 방식(제일 간단함. 작동됨)
//						.key("uniqueAndSecret")
//						.tokenValiditySeconds(60*60*24*7)// 쿠키 일주일 유지
//						.authenticationSuccessHandler(loginSuccessHandler); // 자동로그인 후에도 Handler를 태워줘야한다
			
			http.formLogin()
							.loginPage("/signin")
							.defaultSuccessUrl("/")
							.failureUrl("/signin?error=true")
							.usernameParameter("id")
							.passwordParameter("pwd")
							
							.successHandler(loginSuccessHandler)
							.failureHandler(loginFailureHandler);
			
			http.logout()
							.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
							.logoutSuccessUrl("/")
							.invalidateHttpSession(true)
							.deleteCookies("JSESSIONID");
			
			http.oauth2Login()
				.userInfoEndpoint()
				.userService(customOAuth2UserService) // 네이버 USER INFO의 응답을 처리하기 위한 설정 
				.and() 
				.defaultSuccessUrl("/") // 이걸로 구분해 줄 수 도 있었을 것 같네... 근데 이걸로 타고가면 token값을 쓸 수 있나?
				.failureUrl("/signin?error=true") 
				.and() 
				.exceptionHandling() 
				.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/signin")); 
			
			
		} catch (Exception e) {
			e.getMessage();
		}
	}
	
	
	@Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

	
	
	@Bean 
	public ClientRegistrationRepository clientRegistrationRepository( 
			OAuth2ClientProperties oAuth2ClientProperties, 
			@Value("${custom.oauth2.naver.client-id}") 
			String naverClientId, 
			@Value("${custom.oauth2.naver.client-secret}") 
			String naverClientSecret) { 
		List<ClientRegistration> registrations = oAuth2ClientProperties 
				.getRegistration().keySet().stream() 
				.map(client -> getRegistration(oAuth2ClientProperties, client)) 
				.filter(Objects::nonNull) .collect(Collectors.toList()); 
		
		
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
					.redirectUriTemplate("{baseUrl}/login/oauth2/code/{registrationId}")
					.scope("email", "profile") 
					.build(); 
			} 
		
		if("facebook".equals(client)) { 
			OAuth2ClientProperties.Registration registration = clientProperties.getRegistration().get("facebook"); 
			return CommonOAuth2Provider.FACEBOOK.getBuilder(client) 
					.clientId(registration.getClientId()) 
					.clientSecret(registration.getClientSecret()) 
					.userInfoUri("https://graph.facebook.com/me?fields=id,name,email,link,picture") 
					.scope("email") 
					.build(); 
			} return null; 
			
	}
	


	
	
	
}
