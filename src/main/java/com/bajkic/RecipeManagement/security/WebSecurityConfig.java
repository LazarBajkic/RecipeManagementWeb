package com.bajkic.RecipeManagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bajkic.RecipeManagement.Repo.UserRepo;

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
}
