package com.example.Bookstore.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF protection (adjust based on your app's needs)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/styles.css","/api/auth/**","/api/books","/register","/","/js/**", "/css/**", "/images/**").permitAll()
                .requestMatchers("/login","/books/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login") // URL to submit the login form
                .defaultSuccessUrl("/", true) // Redirect to home on successful login
                .permitAll()
            )
            .logout(logout -> logout.permitAll());
        

        return http.build();
    }

 
}
