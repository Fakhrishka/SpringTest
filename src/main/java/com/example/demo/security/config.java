package com.example.demo.security;

import com.example.demo.security.jwt.JwtFilter;
import jakarta.servlet.http.HttpServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class config{

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    UserDetailsService userDetailsServiceImpl;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(daoAuthenticationProvider());  // Provide the DAO provider
        return authenticationManagerBuilder.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider()
    {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(userDetailsServiceImpl);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(
                        httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(request -> {
                            var cors = new org.springframework.web.cors.CorsConfiguration();
                            cors.setAllowedOrigins(java.util.List.of("*"));
                            cors.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
                            cors.setAllowedHeaders(java.util.List.of("*"));
                            return cors;
                        })
                )
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

                http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/login", "/register").permitAll()
                        .anyRequest().authenticated()// Allow all requests by default
                );

                http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter before the default authentication filter

                return http.build();  // Build the SecurityFilterChain
    }


//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()  // Disable CSRF protection if not needed
//                .authorizeRequests()
//                .anyRequest().permitAll();  // Allow all requests without authentication
//
//        return http.build();  // Return the built security filter chain
//    }
}
