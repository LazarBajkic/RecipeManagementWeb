package com.bajkic.RecipeManagement.security;


import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.bajkic.RecipeManagement.Repo.UserRepo;
import com.bajkic.RecipeManagement.exceptions.CustomAccessDeniedHandler;
import com.bajkic.RecipeManagement.exceptions.CustomAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
    private UserRepo userRepository;

	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
	
	  
	
	@Bean
	public UserDetailsService userDetailsService() {
		  return new UserDetailsService() {
	            @Override
	            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
	                com.bajkic.RecipeManagement.model.User user = userRepository.findByUsername(username);
	                if (user == null) {
	                    throw new UsernameNotFoundException("User not found with username: " + username);

	                }
	                
	                return User.withUsername(user.getUsername())
	                        .password(passwordEncoder().encode(user.getPassword()))
	                        .build();
	            }
	        };
}
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http
	        .authorizeHttpRequests((requests) -> requests
	            .requestMatchers("/","/recipeInfo","/login", "/register").permitAll()
	            .anyRequest().authenticated()
	        )
	        .formLogin(login -> login
	            .loginPage("/login")
	            .loginProcessingUrl("/login")
	            .defaultSuccessUrl("/", true)
	            .failureUrl("/login?error=true")
	            .permitAll()
	        )
	        .logout(logout -> logout
	            .logoutUrl("/logout")
	            .logoutSuccessUrl("/login?logout=true")
	            .invalidateHttpSession(true)
	            .deleteCookies("JSESSIONID")
	            .permitAll()
	        )
	        .exceptionHandling(exceptionHandling -> exceptionHandling
	            .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
	            .accessDeniedHandler(new CustomAccessDeniedHandler())
	        );

	    return http.build();
	}

}