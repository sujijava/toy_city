package ca.sheridancollege.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	
    @Autowired
    private LoginAccessDeniedHandler accessDeniedHandler;

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable(); //Remove before deployment
		http.headers().frameOptions().disable(); //Remove before deployment
		http.authorizeRequests()
		//Restricting HTML pages is wrong
		.antMatchers("/worker/email/**").hasAnyRole("BOSS", "WORKER")
		.antMatchers("/worker/search/**").hasAnyRole("BOSS", "WORKER")
		.antMatchers("/worker/boss/**").hasRole("BOSS")
		.antMatchers(HttpMethod.POST, "/register").permitAll()
		.antMatchers("/", "/css/**", "/images/**", 
				"/js/**", "/**").permitAll() //Login not required
		.antMatchers("/h2-console/**").permitAll() //Remove before deployment
		.anyRequest().authenticated()
		.and()
			.formLogin()
			.loginPage("/login")
			.permitAll()
		.and()
			.logout()
			.invalidateHttpSession(true)
			.clearAuthentication(true)
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/login?logout")
			.permitAll()
		.and()
			.exceptionHandling()
			.accessDeniedHandler(accessDeniedHandler);
		
		// any url starting with /user or /user/ will be restricted to user's home
	}
	
	@Autowired
	private UserDetailedServiceImpl userDatailsService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		 return new BCryptPasswordEncoder(); 
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(userDatailsService)
		.passwordEncoder(passwordEncoder());	
		
	}
}
