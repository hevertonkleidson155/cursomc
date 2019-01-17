package com.heverton.cursomc.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private Environment env;
	
	// Criando um vetor de "endpoints" que eu quero que seja liberado total
	private static final String[] PUBLIC_MATCHERS = {
		"/h2-console/**",
	};
	// Criando um vetor de "endpoints" que eu quero que seja liberado só via GET
	private static final String[] PUBLIC_MATCHERS_GET = {
			"/produtos/**",
			"/categorias/**",
		};
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		
		if(Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();//Liberando o h2 que dá um bugzinho
		}
			
		
		http.cors().and().csrf().disable(); //.and().csrf().disable é questão de segurança 
		http.authorizeRequests()
		.antMatchers(HttpMethod.GET,PUBLIC_MATCHERS_GET).permitAll() //Liberando apenas para trabalhar via GET
		.antMatchers(PUBLIC_MATCHERS).permitAll() // Pega o vetor que eu fiz antes e libera para todo mundo
		.anyRequest().authenticated(); // Qualquer request autenticado. No caso, o anterior liberou o que precisava.
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //Não criar sessão de usuário
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); 
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues()); //Pode chegar nos endpois por qualquer fonte pegando a configuração padrão
		return source;
		
	}
}
