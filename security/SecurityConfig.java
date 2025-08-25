package com.ms.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final BCryptPasswordEncoder passwordEncoder;
 
    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/product/allProducts").permitAll()
                    .requestMatchers("/api/auth/deleteUser/**").hasAnyRole("ADMIN", "STAFF")
                    .requestMatchers("/api/product/newProduct").hasAnyRole("ADMIN", "STAFF")
                    .requestMatchers("/api/product/lowStock").hasAnyRole("ADMIN", "STAFF")
                    .requestMatchers("/api/product/deleteProduct/**").hasAnyRole("ADMIN")
                    .requestMatchers("/api/product/updateProduct/**").hasAnyRole("ADMIN", "STAFF")
                    .requestMatchers("/api/product/all").hasAnyRole("ADMIN")
                    .anyRequest().authenticated()
                )
                .build();
    }
    
    @Bean
    public UserDetailsService userDetailsService() {
    	UserDetails admin = User.withUsername("admin")
    			.password(passwordEncoder.encode("adminpass"))
    			.roles("ADMIN")
    			.build();
    	
    	UserDetails staff1 = User.withUsername("staff1")
    			.password(passwordEncoder.encode("staff1pass"))
    			.roles("STAFF")
    			.build();
    	
    	UserDetails staff2 = User.withUsername("staff2")
    			.password(passwordEncoder.encode("staff2pass"))
    			.roles("STAFF")
    			.build();
    	
    	UserDetails staff3 = User.withUsername("staff3")
    			.password(passwordEncoder.encode("staff3pass"))
    			.roles("STAFF")
    			.build();
    	
    	return new InMemoryUserDetailsManager(admin,staff1,staff2,staff3);
    }
    
    
    
    
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//    	return new BCryptPasswordEncoder();
//    }
    
    

}

