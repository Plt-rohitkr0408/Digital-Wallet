package com.example.DigitalWallet.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityService {

    public final JWTFilter jwtfilter;
    public final UserDetailsService userDetailsService;

    public SecurityService(JWTFilter jwtfilter, UserDetailsService userDetailsService) {
        this.jwtfilter = jwtfilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration){
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
       http.csrf(csrf-> csrf.disable())
               .authorizeHttpRequests(auth->
                       auth.requestMatchers("/swagger-ui/index.html", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                               .requestMatchers("/test/**").permitAll()
                               .requestMatchers("/api/users/register", "/api/users/login").permitAll()

                               .anyRequest().authenticated()
                       )
               .authenticationProvider(authenticationProvider())
               .addFilterBefore(jwtfilter, UsernamePasswordAuthenticationFilter.class)
               .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
               .httpBasic(h-> {});
      return http.build();
    }
}
