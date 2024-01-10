package org.digit.exchange.security;

import org.digit.exchange.constant.Role;
import org.digit.exchange.service.IndividualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig{

    private final AuthenticationConfiguration authenticationConfiguration;
    private final IndividualService individualService;
    private final JwtUtil jwtUtil;

    public SecurityConfig(@Lazy IndividualService individualService, AuthenticationConfiguration authenticationConfiguration, JwtUtil jwtUtil) {
        this.individualService = individualService;
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/exchange/v1/public/**").permitAll()
                .requestMatchers("/exchange/v1/admin/**").hasRole(Role.ADMIN.toString())
                // .requestMatchers("/line/**").permitAll()
                // .requestMatchers("/finance/**").permitAll()
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilter(new CustomJwtAuthenticationFilter(authenticationManager)) 
            .addFilterBefore(new JwtRequestFilter(jwtUtil), CustomJwtAuthenticationFilter.class);
            // .addFilterBefore(new ApiKeyAuthFilter(), JwtRequestFilter.class);


        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder) throws Exception {
        auth.userDetailsService(individualService).passwordEncoder(passwordEncoder);
    }
}
